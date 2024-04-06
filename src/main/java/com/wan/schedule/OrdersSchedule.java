package com.wan.schedule;

import com.wan.constant.OrdersConstant;
import com.wan.entity.Goods;
import com.wan.entity.Orders;
import com.wan.entity.StoreSales;
import com.wan.mapper.CategoryMapper;
import com.wan.mapper.GoodsMapper;
import com.wan.mapper.OrdersMapper;
import com.wan.mapper.StoreSalesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    @Scheduled(cron = "0 0 0 * * ?") // 每天午夜执行，用于处理已发货3天后转为已签收的订单
    public void processShippedOrders() {
        log.info("定时任务 已发货转为已签收 已启动");
        List<Orders> shippedOrders = ordersMapper.queryOneTypeOrders(OrdersConstant.SHIPPED_ORDER);
        processOrders(shippedOrders, OrdersConstant.USER_RECEIVE_PRODUCT, 3);
    }

    @Scheduled(cron = "0 0 0 * * ?") // 每天午夜点执行，用于处理已签收7天后转为交易完成的订单
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
                    .userCount(0)
                    .build();
            // 将新创建的销售记录插入数据库
            storeSalesMapper.insertStoreSales(storeSales);
        }
        return storeSales;
    }
}


