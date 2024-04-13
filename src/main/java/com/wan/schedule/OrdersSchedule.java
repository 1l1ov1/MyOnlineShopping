package com.wan.schedule;

import com.wan.constant.*;
import com.wan.entity.*;
import com.wan.mapper.*;
import com.wan.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单定时器
 */
@Component
@Slf4j
public class OrdersSchedule {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private StoreSalesMapper storeSalesMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // @Scheduled(cron = "0 0 0 * * ?") // 每天午夜执行，用于处理已发货3天后转为已签收的订单
    @Scheduled(cron = "0/60 * * * * ?") // 每天午夜执行，用于处理已发货3天后转为已签收的订单
    public void processShippedOrders() {
        log.info("定时任务 已发货转为已签收 已启动");
        List<Orders> shippedOrders = ordersMapper.queryOneTypeOrders(OrdersConstant.SHIPPED_ORDER);
        processOrders(shippedOrders, OrdersConstant.USER_RECEIVE_PRODUCT, 3);
    }

    // @Scheduled(cron = "0 0 0 * * ?") // 每天午夜点执行，用于处理已签收7天后转为交易完成的订单
    @Scheduled(cron = "0/60 * * * * ?") // 每天午夜点执行，用于处理已签收7天后转为交易完成的订单
    public void processReceivedOrders() {
        log.info("定时任务 签收转为交易完成 已启动");
        List<Orders> receivedOrders = ordersMapper.queryOneTypeOrders(OrdersConstant.USER_RECEIVE_PRODUCT);
        processOrders(receivedOrders, OrdersConstant.SUCCESSFUL_ORDER, 7);
    }


    /**
     * 处理订单状态的更新。
     *
     * @param orders 订单列表，类型为List<Orders>。
     * @param status 需要更新到的状态值，类型为int。
     * @param days   订单创建时间与当前时间相隔的天数，类型为int。
     *               说明：此方法将根据提供的天数，寻找对应时间创建的订单，并将其状态更新为指定的状态值。
     */
    private void processOrders(List<Orders> orders, int status, int days) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 筛选出创建时间与当前时间相差指定天数的订单
        List<Orders> ordersToUpdate = orders.stream()
                .filter(item -> {
                    LocalDateTime createTime = item.getCreateTime();
                    // 如果订单的创建时间+指定天数还在今天之前或相等
                    return createTime.plusDays(days).isBefore(now) || createTime.plusDays(days).isEqual(now);
                })
                .collect(Collectors.toList());
        // 如果存在需要更新的订单
        if (!ordersToUpdate.isEmpty()) {
            // 遍历并更新订单状态
            ordersToUpdate.forEach(item -> item.setStatus(status));
            // 批量更新订单状态到数据库
            ordersMapper.batchUpdate(ordersToUpdate);

            // 订单更新完，就清除订单缓存
            // RedisUtils.clearRedisCache(redisTemplate, RedisConstant.ADMINISTRATOR_CLEAR_ORDERS_PATTERN);
            // 如果已经是交易完成了，就将营业额添加到指定的商店
            if (status == OrdersConstant.SUCCESSFUL_ORDER) {
                updateTurnoverForSuccessfulOrders(ordersToUpdate);
            }
        }
    }

    /**
     * 为成功交易的订单更新营业额。
     *
     * @param ordersToUpdate 需要更新状态的订单列表
     */
    private void updateTurnoverForSuccessfulOrders(List<Orders> ordersToUpdate) {
        ordersToUpdate.forEach(item -> {
            Long goodsId = item.getGoodsId();
            try {
                Goods goods = goodsMapper.findGoodsById(goodsId);
                addTurnover(item, goods.getCategoryId());
            } catch (Exception e) {
                // 对于查询商品和添加营业额过程中的异常进行捕获处理
                e.printStackTrace();
            }
        });
    }

    /**
     * 为特定类别添加销售量和总价格到店铺销售记录中。
     *
     * @param orders     包含销售信息的订单对象，需要包括店铺ID、销售数量和总价格。
     * @param categoryId 商品类别ID，用于分类销售数据。
     */
    private void addTurnover(Orders orders, Long categoryId) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 根据店铺ID、当前日期和类别ID获取或创建店铺销售对象
        StoreSales storeSales = getOrCreateStoreSales(orders.getStoreId(), currentDate, categoryId);
        // 添加销售数量和总价格到店铺销售对象中
        storeSales.addSales(orders.getNumber(), orders.getTotalPrice());
        // 更新店铺销售对象到数据库
        storeSalesMapper.update(storeSales);
        // 然后将提现金额放到店铺提现记录中
        insertWithdrawRecord(orders);
        // 然后将提现的金额放到钱包中
    }

    /**
     * 插入一条提现记录。
     *
     * @param orders 订单信息，包含店铺ID、用户ID、商品数量、总价、支付方式和商品ID。
     *               通过这些信息计算出提现金额，并为该订单生成一条提现记录。
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void insertWithdrawRecord(Orders orders) {
        // 提取订单中的关键信息
        Long storeId = orders.getStoreId();
        Long userId = orders.getUserId();
        Integer number = orders.getNumber();
        BigDecimal totalPrice = orders.getTotalPrice();
        Integer pay = orders.getPay();

        // 查询商品名称
        String goodsName = goodsMapper.findGoodsById(orders.getGoodsId()).getGoodsName();

        // 计算提现金额（总价乘以提现费率）
        BigDecimal feeMultiplier = new BigDecimal(WithdrawRecordConstant.fee);
        BigDecimal fee = totalPrice.multiply(feeMultiplier);
        BigDecimal roundedFee = fee.setScale(2, RoundingMode.HALF_UP);

        BigDecimal withdrawMoney = totalPrice.subtract(roundedFee);


        // 查询店铺对应的卖家ID
        Long sellerId = storeMapper.findStoreById(storeId).getUserId();
        // 查询该店家原先的钱
        BigDecimal originMoney = userMapper.getById(userId).getMoney();
        // 插入提现记录
        withdrawRecordMapper.insertWithdrawRecord(WithdrawRecord.builder()
                .storeId(storeId)
                .userId(userId)
                .number(number)
                .totalPrice(totalPrice)
                .pay(pay)
                .goodsName(goodsName)
                .withdrawMoney(withdrawMoney)
                .originMoney(originMoney)
                .sellerId(sellerId)
                .build());

        // 更新商家钱包
        userMapper.update(User.builder()
                .id(sellerId)
                .money(originMoney.add(withdrawMoney))
                .build());

        // 使用抽取的方法来更新管理员钱包并插入手续费记录
        updateAndInsertAdminWallet(fee, sellerId, totalPrice, goodsName, number, pay, storeId);
    }

    /**
     * 修改管理员的钱包
     *
     * @param fee
     * @param sellerId
     * @param totalPrice
     * @param goodsName
     * @param number
     * @param pay
     * @param storeId
     */
    private void updateAndInsertAdminWallet(BigDecimal fee, Long sellerId, BigDecimal totalPrice,
                                            String goodsName, Integer number, Integer pay, Long storeId) {
        User administrator = getAdministrator();

        // 插入提现记录（手续费）
        withdrawRecordMapper.insertWithdrawRecord(WithdrawRecord.builder()
                .storeId(storeId)
                .userId(sellerId) // 给予的userId 该为卖家的id
                .withdrawMoney(fee)
                .originMoney(administrator.getMoney())
                .sellerId(administrator.getId())
                .totalPrice(totalPrice)
                .goodsName(goodsName)
                .number(number)
                .pay(pay)
                .build());

        // 更新管理员钱包
        administrator.setMoney(administrator.getMoney().add(fee));
        userMapper.update(administrator);
        // 清除提现缓存和用户的缓存
        // RedisUtils.clearRedisCache(redisTemplate,
        //         RedisConstant.ADMINISTRATOR_WITHDRAW_RECORD_PAGE,
        //         RedisConstant.ADMINISTRATOR_USER_PAGE);
    }

    /**
     * 得到管理员
     *
     * @return
     */
    private User getAdministrator() {
        return userMapper.getAdministrator(UserConstant.SUPER_ADMINISTRATOR);
    }

    /**
     * 获取或创建店铺指定日期和类别的销售信息。
     *
     * @param storeId    店铺ID，用于标识特定店铺。
     * @param date       指定的日期，格式为LocalDate。
     * @param categoryId 类别ID，用于标识销售商品的特定类别。
     * @return StoreSales 对象，包含指定店铺在指定日期和类别的销售详情。
     */
    private StoreSales getOrCreateStoreSales(Long storeId, LocalDate date, Long categoryId) {
        // 尝试根据给定的店铺ID、日期和类别ID查找已存在的销售记录
        StoreSales storeSales = storeSalesMapper.findSalesByCategoryInOneDay(storeId, date, categoryId);
        if (storeSales == null) {
            // 如果不存在，则创建新的销售记录，并初始化各项销售指标
            storeSales = StoreSales.builder()
                    .storeId(storeId)
                    .date(date)
                    .categoryId(categoryId)
                    .dailySales(BigDecimal.ZERO)
                    .orderCount(0)
                    .avgOrderAmount(BigDecimal.ZERO)
                    .isWithdraw(StoreSalesConstant.NOT_WITHDRAWN)
                    .userCount(0)
                    .build();
            // 将新创建的销售记录插入数据库
            storeSalesMapper.insertStoreSales(storeSales);
        }
        return storeSales;
    }
}


