package com.wan.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.*;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.*;
import com.wan.entity.*;
import com.wan.exception.*;
import com.wan.mapper.*;
import com.wan.result.PageResult;
import com.wan.service.UserService;

import com.wan.utils.CheckObjectFieldUtils;
import com.wan.utils.CheckPasswordUtils;
import com.wan.utils.SnowFlakeUtil;
import com.wan.vo.UserOrdersVO;
import com.wan.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private StoreSalesMapper storeSalesMapper;
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        User user = isValid(userLoginDTO);
        // 修改用户的登录状态
        user.setIsOnline(UserConstant.IS_ONLINE);
        userMapper.update(user);
        return user;
    }

    /**
     * 用户注册
     *
     * @param userLoginDTO
     */
    @Override
    public void addUser(UserLoginDTO userLoginDTO) {
        // 先按照用户名查询用户
        User user = userMapper.getByUsername(userLoginDTO.getUsername());
        // 如果该用户存在
        if (user != null) {
            throw new AccountExistException(MessageConstant.ACCOUNT_EXIST);
        }
        // 如果用户不存在
        user = new User();
        // 将DTO属性的值拷贝到user中
        BeanUtils.copyProperties(userLoginDTO, user);
        // 设置状态为启用
        user.setAccountStatus(UserConstant.ENABLE);
        // 设置用户为普通用户
        user.setStatus(UserConstant.COMMON_USER);
        // 设置用户的在线状态
        user.setIsOnline(UserConstant.IS_NOT_ONLINE);
        // 将密码修改为MD5后保存
        String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5);
        // 插入用户
        userMapper.insert(user);
    }

    /**
     * 根据id得到用户
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserById(Long userId) {
        return userMapper.getById(userId);
    }

    /**
     * 修改用户
     *
     * @param user
     */
    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    @Override
    public User getDetail(Long id) {
        User user = userMapper.getById(id);
        return user;
    }

    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Integer type = updatePasswordDTO.getType();
        if (type == null) {
            throw new IllegalArgumentException("非法类型");
        }
        User user = null;
        if (type.equals(PasswordConstant.FORGET)) {
            user = handleForgetPassword(updatePasswordDTO);
        } else if (type.equals(PasswordConstant.UPDATE)) {
            user = handleUpdatePassword(updatePasswordDTO);
        }

        userMapper.update(user);
    }

    private User handleForgetPassword(UpdatePasswordDTO updatePasswordDTO) {
        String username = updatePasswordDTO.getUsername();
        if (username == null || "".equals(username)) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.USER_IS_NOT_EXIST);
        }

        if (CheckPasswordUtils.checkPassword(updatePasswordDTO.getNewPwd(),
                updatePasswordDTO.getRePwd())) {
            if (user.getPassword().equals(DigestUtils.md5DigestAsHex(updatePasswordDTO.getNewPwd().getBytes()))) {
                throw new PasswordErrorException(PasswordConstant.NEW_PASSWORD_EQUALS_OLD_PASSWORD);
            }
            user.setPassword(DigestUtils.md5DigestAsHex(updatePasswordDTO.getNewPwd().getBytes()));
        }

        return user;
    }

    private User handleUpdatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Long userId = ThreadBaseContext.getCurrentId();
        ThreadBaseContext.removeCurrentId();
        if (userId == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        User user = userMapper.getById(userId);
        if (user == null) {
            throw new AccountLockedException(MessageConstant.USER_IS_NOT_EXIST);
        }

        if (CheckPasswordUtils.checkPassword(updatePasswordDTO.getOldPwd(),
                updatePasswordDTO.getNewPwd(), updatePasswordDTO.getRePwd())) {
            user.setPassword(DigestUtils.md5DigestAsHex(updatePasswordDTO.getNewPwd().getBytes()));
        }

        return user;
    }

    @Override
    @Transactional
    public void createStore(UserCreateStoreDTO userCreateStoreDTO) {
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username(userCreateStoreDTO.getUsername())
                .password(userCreateStoreDTO.getPassword()).build();

        User user = isValid(userLoginDTO);

        Store store = userCreateStoreDTO.getStore();
        // 如果没有这个名字的商店，就找这个用户是否有商店
        Store userStore = storeMapper.findStoreByUserId(user.getId());
        // 如果有
        if (userStore != null) {
            throw new StoreException(MessageConstant.ONLY_HAS_ONE_STORE);
        }
        // 得到商店
        Store storeByStoreName = storeMapper.findStoreByStoreName(store.getStoreName());
        if (storeByStoreName != null) {
            throw new StoreException(MessageConstant.STORE_EXIST);
        }
        // 如果没有
        store.setUserId(user.getId());
        store.setStatus(StoreConstant.OPEN);
        // 添加商店
        storeMapper.insertStore(store);
        // 得到地址
        Address address = userCreateStoreDTO.getAddress();
        if (address == null) {
            throw new StoreException(MessageConstant.STORE_ADDRESS_IS_NOT_ALLOWED_TO_BE_EMPTY);
        }
        // 成为店家
        user.setStatus(UserConstant.BUSINESSMAN);
        userMapper.update(user);
        // 添加地址
        address.setStoreId(store.getId());
        address.setIsDefault(AddressConstant.IS_DEFAULT);
        addressMapper.insertStoreAddress(address);
    }


    /**
     * 购买商品
     *
     * @param goodsPurchaseDTO
     */
    @Override
    @Transactional
    public void buy(GoodsPurchaseDTO goodsPurchaseDTO) {
        // 获取商品信息
        Goods goods = goodsMapper.findGoodsById(goodsPurchaseDTO.getGoodsId());
        if (goods == null) {
            throw new GoodsException(MessageConstant.GOODS_IS_NOT_EXIST);
        }

        // 检查库存
        if (goods.getTotal() < goodsPurchaseDTO.getNumber()) {
            throw new GoodsException(MessageConstant.GOODS_TOTAL_IS_OUT_OF_VALID_RANGE);
        }

        // 获取当前用户ID
        Long userId = ThreadBaseContext.getCurrentId();

        // 更新商品库存
        goods.decreaseStock(goodsPurchaseDTO.getNumber());
        goodsMapper.update(goods);

        // 创建订单
        Orders orders = createOrders(goodsPurchaseDTO);
        orders.setUserId(userId);
        ordersMapper.insertOrders(orders);

        // 扣除用户余额
        User user = userMapper.getById(userId);
        BigDecimal userMoney = user.getMoney();
        if (userMoney.compareTo(goodsPurchaseDTO.getTotalPrice()) < 0) {
            throw new OrdersException(MessageConstant.THE_AMOUNT_IS_INSUFFICIENT);
        }
        user.decreaseBalance(goodsPurchaseDTO.getTotalPrice());
        userMapper.update(user);

        // 当用户购买后，就通知商家
        Map<String, Object> map = new HashMap<>();
        map.put("type", WebSocketConstant.REMIND_ORDER); // 1表示来单提醒 2表示客户催单
        map.put("message", MessageConstant.USER_HAS_ORDERED);
        map.put("content", "订单号：" + orders.getOrdersNumber());
        webSocketServer.sendToSpecificStore(orders.getStoreId(), JSON.toJSONString(map));
    }


    /**
     * 查询某种类型的订单
     *
     * @param userId
     * @param target
     * @return
     */
    @Override
    public PageResult queryOneTypeOrders(Long userId, Integer target, Integer currentPage, Integer pageSize) {
        // 查询该用户是否存在
        User user = userMapper.getById(userId);
        // 如果不存在
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 如果存在
        // 就根据类型去查询
        // 如果订单类型错误
        if (target < OrdersConstant.ALL_ORDERS || target > OrdersConstant.SUCCESSFUL_ORDER) {
            throw new OrdersException(MessageConstant.ORDERS_STATUS_IS_WRONG);
        }

        PageHelper.startPage(currentPage, pageSize);
        Page<UserOrdersVO> page = userMapper.queryOneTypeOrders(userId, target);
        return PageResult.builder()
                .data(page.getResult())
                .total(page.getTotal())
                .build();
    }

    /**
     * 用户申请退款
     *
     * @param id
     */
    @Transactional
    @Override
    public void applyRefund(Long id) {
        Orders orders = ordersMapper.findOrders(id);
        if (orders == null) {
            throw new OrdersException(MessageConstant.ORDERS_NUMBER_IS_NOT_EXIST);
        }
        Integer status = orders.getStatus();
        // 如果订单状态是未发货
        if (OrdersConstant.UNSHIPPED_ORDER.equals(status)) {
            orders.setStatus(OrdersConstant.REFUNDED_ORDER);
        } else if (OrdersConstant.USER_RECEIVE_PRODUCT.equals(status)) {
            // 如果是已签收的7天内 不包含
            if (LocalDateTime.now()
                    .isBefore(orders.getCreateTime()
                            .plus(Duration.ofDays(7)))) {
                orders.setStatus(OrdersConstant.REFUNDED_ORDER);
            } else {
                throw new OrdersException(MessageConstant.THE_AMOUNT_IS_INSUFFICIENT);
            }
        } else {
            throw new OrdersException(MessageConstant.ORDERS_STATUS_IS_WRONG);
        }
        // 修改订单
        ordersMapper.update(orders);
        // 退款
        Long userId = orders.getUserId();
        User refund = userMapper.getById(userId);
        if (refund == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        refund.setMoney(refund.getMoney().add(orders.getTotalPrice()));
        userMapper.update(refund);
    }

    /**
     * 添加地址
     *
     * @param address
     */

    @Override
    public void addAddress(Address address) {
        Long userId = address.getUserId();
        User user = userMapper.getById(userId);
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 如果用户不空
        try {
            // 检查字段为空吗
            if (CheckObjectFieldUtils.areAllNonExcludedFieldsNotNull(address, "id", "storeId")) {
                // 如果不空
                // 就去检查该用户是否已经拥有了相同的地址
                // 得到该用户的所有地址
                List<Address> addressList = addressMapper.getAddressByUserId(userId, null);
                // 如果说还没到达最大的地址数
                if (addressList.size() < AddressConstant.MAX_ADDRESS_NUMBER) {
                    // 就允许添加
                    List<Address> collect = addressList.stream()
                            // 过滤，查找相同的地址
                            .filter(item -> item.getProvinceName().equals(address.getProvinceName()))
                            .filter(item -> item.getCityName().equals(address.getCityName()))
                            .filter(item -> item.getDistrictName().equals(address.getDistrictName()))
                            .collect(Collectors.toList());

                    // 如果过滤集合为空， 即没有相同的地址
                    if (collect.isEmpty()) {
                        addressMapper.insertAddress(address);
                    } else {
                        throw new AddressException(MessageConstant.ADDRESS_IS_EXIST);
                    }
                } else {
                    // 如果到了
                    throw new AddressException(MessageConstant.OUT_OF_MAX_ADDRESS_NUMBER);
                }


            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 删除地址
     *
     * @param id
     */
    @Override
    public void deleteAddress(Long id) {
        Address address = addressMapper.findAddressById(id);
        if (address == null) {
            throw new AddressException(MessageConstant.ADDRESS_IS_NOT_EXIST);
        }

        addressMapper.deleteAddress(id);
    }

    private Orders createOrders(GoodsPurchaseDTO goodsPurchaseDTO) {
        return Orders.builder()
                .goodsId(goodsPurchaseDTO.getGoodsId())
                .ordersNumber(SnowFlakeUtil.nextId())
                .goodsName(goodsPurchaseDTO.getGoodsName())
                .number(goodsPurchaseDTO.getNumber())
                .pay(PayConstant.WALLET_PAYMENTS)
                .storeId(goodsPurchaseDTO.getStoreId())
                .totalPrice(goodsPurchaseDTO.getTotalPrice())
                .status(OrdersConstant.UNSHIPPED_ORDER)
                .address(goodsPurchaseDTO.getAddress())
                .build();
    }

    @Override
    public void reminder(ReminderDTO reminderDTO) {
        // 数据校验
        if (reminderDTO == null || reminderDTO.getStoreId() == null || reminderDTO.getOrdersNumber() == null) {
            throw new IllegalArgumentException("输入参数不能为空");
        }
        Long storeId = reminderDTO.getStoreId();
        Long ordersNumber = reminderDTO.getOrdersNumber();
        // 发送消息
        Map<String, Object> map = new HashMap<>();
        map.put("type", WebSocketConstant.USER_URGE_ORDER);
        map.put("message", MessageConstant.USER_URGE_ORDER);
        map.put("content", "订单号：" + ordersNumber);

        webSocketServer.sendToSpecificStore(storeId, JSON.toJSONString(map));

    }

    /**
     * 判断账号是否合法，如果合法就将用户返回
     *
     * @param userLoginDTO
     * @return
     */
    private User isValid(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        // 得到MD5加密后的密码
        String password = DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes());
        // 从数据库中按用户名查找
        User user = userMapper.getByUsername(username);
        // 如果账号不存在
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 如果账号存在，看看密码是否一致
        if (!password.equals(user.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // 如果账号密码输对，看看是否被禁用
        // 如果被禁用
        if (user.getAccountStatus() == UserConstant.DISABLE) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }


        return user;
    }
}
