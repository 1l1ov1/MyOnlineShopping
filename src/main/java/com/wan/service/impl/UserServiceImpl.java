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
import com.wan.vo.CommentPageQueryVO;
import com.wan.vo.UserOrdersVO;
import com.wan.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private WebSocketServer webSocketServer;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ReportMapper reportMapper;

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
        // 修改用户
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
        // 设置封禁开始时间和结束时间
        user.setBanStartTime(null);
        user.setBanEndTime(null);
        // 设置用户为普通用户
        user.setStatus(UserConstant.COMMON_USER);
        // 设置用户的在线状态
        user.setIsOnline(UserConstant.IS_NOT_ONLINE);
        // 设置不禁言
        user.setForbiddenWord(UserConstant.NOT_FORBIDDEN_WORD);
        // 设置禁言开始时间和结束时间
        user.setForbiddenStartTime(null);
        user.setForbiddenEndTime(null);
        // 将密码修改为MD5后保存
        String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5);
        // 设置用户的默认头像
        user.setAvatar(UserConstant.DEFAULT_AVATAR);
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
        return userMapper.getById(id);
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

    /**
     * 忘记用户密码
     *
     * @param updatePasswordDTO
     * @return
     */
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

    /**
     * 修改用户密码
     *
     * @param updatePasswordDTO
     * @return
     */
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

    /**
     * 用户创建商店
     *
     * @param userCreateStoreDTO
     */
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
                if (addressList.isEmpty()) {
                    // 如果说没有地址，说明此次添加是默认地址
                    address.setIsDefault(AddressConstant.IS_DEFAULT);
                    // 那么就直接添加
                    addressMapper.insertAddress(address);
                } else if (addressList.size() < AddressConstant.MAX_ADDRESS_NUMBER) {
                    // 如果说还没到达最大的地址数
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
     * 查询评论
     *
     * @param commentPageQueryDTO
     * @return
     */
    @Override
    public CommentPageQueryVO queryComments(CommentPageQueryDTO commentPageQueryDTO) {
        try {
            // 检查所有属性
            if (CheckObjectFieldUtils.allFieldNotNUll(commentPageQueryDTO)) {
                // 如果全不空
                Goods goods = goodsMapper.findGoodsById(commentPageQueryDTO.getGoodsId());
                if (goods == null || !goods.getStoreId().equals(commentPageQueryDTO.getStoreId())) {
                    // 如果商品不存在或商品存在但是商品不属于这个店铺
                    throw new GoodsException(MessageConstant.GOODS_IS_NOT_EXIST);
                }
                // 如果相等
                return commentMapper.queryComments(commentPageQueryDTO) == null ? new CommentPageQueryVO() : commentMapper.queryComments(commentPageQueryDTO);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 添加评论
     *
     * @param comment
     */
    @Override
    @Transactional
    public void addComment(Comment comment) {
        try {
            if (CheckObjectFieldUtils.areAllNonExcludedFieldsNotNull(comment,
                    "id", "commentStatus", "reportCount", "parentCommentId",
                    "replyCount", "likeCount", "dislikeCount")) {
                // 如果其他的字段不空，就看内容是不是全部为空格
                validateCommentContent(comment.getContent());
                // 如果合法就查询用户
                User user = userMapper.getById(comment.getUserId());
                // 如果用户为空
                if (user == null) {
                    throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
                }

                // 如果用户不空，就先去判断有没有被封禁和禁言
                checkUserBanOrForbidden(user);

                // 如果没禁言，也没封禁
                // 就查询商品
                validateGoods(comment);
                // 如果一致，就校验评分
                if (comment.getStar() > 5 || comment.getStar() < 0) {
                    throw new CommentException(MessageConstant.COMMENT_STAR_OUT_OF_RANGE);
                }
                // 如果评分合法
                comment.setCommentStatus(CommentConstant.NORMAL);
                comment.setLikeCount(0);
                comment.setReplyCount(0);
                comment.setReportCount(0);
                comment.setDislikeCount(0);
                // 添加评论
                commentMapper.addComment(comment);
                // 添加评论行为
                commentMapper.addCommentAction(CommentAction.builder()
                        .commentId(comment.getId())
                        .userId(comment.getUserId())
                        .goodsId(comment.getGoodsId())
                        .storeId(comment.getStoreId())
                        .action(CommentConstant.COMMENT_ACTION_NONE)
                        .build());
            } else {
                throw new FieldException(MessageConstant.FIELD_IS_EMPTY);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证评论内容的合法性。
     *
     * @param content 评论的内容，需要进行验证。
     * @throws CommentException 如果评论内容为空白、长度超出允许范围，则抛出此异常。
     */
    private void validateCommentContent(String content) {
        // 检查内容是否全部为空格
        if (content.trim().length() == 0) {
            // 如果内容为空白，则抛出评论内容为空的异常
            throw new CommentException(MessageConstant.COMMENT_CONTENT_IS_EMPTY);
        }
        // 检查内容长度是否合法
        if (content.length() > CommentConstant.MAX_COMMENT_CONTENT_LENGTH || content.length() < CommentConstant.MIN_COMMENT_CONTENT_LENGTH) {
            // 如果内容长度超出允许范围，则抛出评论内容长度异常
            throw new CommentException(MessageConstant.COMMENT_CONTENT_LENGTH_OUT_OF_RANGE);
        }
    }


    /**
     * 验证商品信息的有效性。
     *
     * @param comment 包含商品ID和店铺ID的评论对象。
     *                通过商品ID查找商品信息，并验证该商品是否存在于对应的店铺中。
     * @throws GoodsException 如果商品不存在或商品不属于指定店铺，则抛出商品异常。
     */
    private void validateGoods(Comment comment) {
        // 根据评论中的商品ID查找对应的商品信息
        Goods goods = goodsMapper.findGoodsById(comment.getGoodsId());
        // 如果查找不到对应的商品信息，则抛出商品不存在异常
        if (goods == null) {
            throw new GoodsException(MessageConstant.GOODS_IS_NOT_EXIST);
        }
        // 检查商品所属店铺ID是否与评论中的店铺ID一致，如果不一致则抛出商品不存在异常
        if (!goods.getStoreId().equals(comment.getStoreId())) {
            throw new GoodsException(MessageConstant.GOODS_IS_NOT_EXIST);
        }
    }


    /**
     * 检查用户是否被禁用或禁止发言。
     *
     * @param user 用户对象，包含用户的状态和禁止词信息。
     * @throws ForbiddenOrBanException 如果用户被禁用或禁止发言，则抛出此异常。
     */
    private void checkUserBanOrForbidden(User user) throws ForbiddenOrBanException {
        Integer accountStatus = user.getAccountStatus();
        // 检查用户是否被禁用
        if (accountStatus == UserConstant.DISABLE) {
            processBanStatus(user);
        }
        // 检查用户是否使用了禁止词
        if (accountStatus == UserConstant.ENABLE && user.getForbiddenWord() == UserConstant.FORBIDDEN_WORD) {
            processForbiddenStatus(user);
        }
    }

    private void processForbiddenStatus(User user) {
        // 如果被禁言，就不允许发评论
        // 如果被封禁
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime forbiddenEndTime = user.getForbiddenEndTime();
        if (forbiddenEndTime == null) {
            // 如果是永久禁言
            throw new ForbiddenOrBanException(MessageConstant.ACCOUNT_IS_FOREVER_FORBIDDEN);
        }
        // 如果不空，就看看是否到时间
        if (forbiddenEndTime.isBefore(now)) {
            // 如果结束时间在当前时间之前，说明应该要解除禁言
            user.setForbiddenStartTime(null);
            user.setForbiddenEndTime(null);
            user.setForbiddenWord(UserConstant.NOT_FORBIDDEN_WORD);
            userMapper.update(user);
        } else {
            // 否则就是还没到时间
            throw new ForbiddenOrBanException(MessageConstant.USER_HAS_FORBIDDEN_COMMENT + forbiddenEndTime);
        }
    }

    private void processBanStatus(User user) {
        // 如果被封禁
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime banEndTime = user.getBanEndTime();
        if (banEndTime == null) {
            // 说明是永久封禁
            throw new ForbiddenOrBanException(MessageConstant.ACCOUNT_IS_FOREVER_BAN);
        }
        // 如果不空，就看看用户是否到了解封时间
        if (banEndTime.isBefore(now)) {
            // 如果到了
            user.setBanStartTime(null);
            user.setBanEndTime(null);
            user.setAccountStatus(UserConstant.ENABLE);
            userMapper.update(user);
        } else {
            throw new ForbiddenOrBanException(MessageConstant.USER_HAS_BANNED + banEndTime);
        }
    }

    /**
     * 查询评论行为
     *
     * @param commentActionDTO
     * @return
     */
    @Override
    public List<CommentAction> queryCommentsAction(CommentActionDTO commentActionDTO) {
        try {
            if (CheckObjectFieldUtils.allFieldNotNUll(commentActionDTO)) {
                // 如果全部字段不空
                return commentMapper.findCommentAction(commentActionDTO);
            } else {
                throw new FieldException(MessageConstant.FIELD_IS_EMPTY);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改评论行为
     *
     * @param commentAction
     */
    @Override
    public void updateCommentAction(CommentAction commentAction) {
        try {
            // 验证commentAction对象的必要字段非空
            if (CheckObjectFieldUtils.areAllNonExcludedFieldsNotNull(commentAction, "id", "userId", "commentId")) {
                // 根据用户ID和评论ID查询评论行为
                CommentAction findCommentAction = commentMapper.findCommentActionByUserIdAndCommentId(commentAction.getUserId(), commentAction.getCommentId());
                if (ObjectUtils.isEmpty(findCommentAction)) {
                    // 不存在则添加评论行为
                    commentMapper.addCommentAction(commentAction);
                } else {
                    // 存在则更新评论行为
                    commentAction.setId(findCommentAction.getId());
                    commentMapper.updateCommentAction(commentAction);
                    // 更新点赞或点踩数
                    updateLikeOrDislikeCount(commentAction, findCommentAction);
                }
            }
        } catch (Exception e) {
            // 捕获更通用的异常，并进行处理或日志记录
            // 此处的处理应根据实际情况，可能包括记录日志、抛出自定义异常等
            throw new RuntimeException("更新评论行为失败", e);
        }
    }


    /**
     * 举报
     * 同一条评论仅能举报一次。
     * 用户在举报时应谨慎选择举报类型。
     * 每天可以举报评价的总次数为30次。
     * 周和月无举报限制。
     * 举报成功后，举报数量会相应减少一次
     *
     * @param report
     */
    @Override
    @Transactional
    public Comment addReport(Report report) {
        try {
            if (CheckObjectFieldUtils.areAllNonExcludedFieldsNotNull(report, "id")) {
                // 如果字段都不空
                // 先根据时间查询举报次数
                Integer reportCount = reportMapper.findReportCount(report.getUserId(), LocalDate.now());
                // 判断举报次数是否超过限制
                if (reportCount >= ReportConstant.MAX_REPORT_COUNT_PER_DAY) {
                    // 如果超过了或到达
                    throw new ReportException(MessageConstant.REPORT_COUNT_OUT_OF_RANGE);
                }
                // 就看举报长度是否合法
                int reasonLength = report.getReason().trim().length();
                if (reasonLength < ReportConstant.MIN_REPORT_CONTENT_LENGTH
                        || reasonLength > ReportConstant.MAX_REPORT_CONTENT_LENGTH) {
                    // 如果非法
                    throw new ReportException(MessageConstant.REPORT_CONTENT_LENGTH_ERROR);
                }
                // 如果没有超出限制
                User user = userMapper.getById(report.getUserId());
                // 判断用户是否为空
                if (ObjectUtils.isEmpty(user)) {
                    throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
                }

                // 判断评论是否存在
                Comment comment = commentMapper.findCommentById(report.getCommentId());
                if (ObjectUtils.isEmpty(comment)) {
                    throw new CommentException(MessageConstant.COMMENT_CONTENT_IS_EMPTY);
                }
                // 如果都存在，就去查询有没有举报过该评论
                Report findReport = reportMapper.findReportByUserIdAndCommentId(report.getUserId(), report.getCommentId());
                if (!ObjectUtils.isEmpty(findReport)) {
                    // 如果不为空
                    throw new ReportException(MessageConstant.ALREADY_REPORTED);
                }
                // 举报为空，就添加
                reportMapper.addReport(report);
                // 如果将对应的评论添加举报次数
                comment.setReportCount(comment.getReportCount() + 1);
                commentMapper.updateComment(comment);

                return comment;
            } else {
                throw new FieldException(MessageConstant.FIELD_IS_EMPTY);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新点赞或点踩数
     *
     * @param commentAction     当前操作的评论行为
     * @param findCommentAction 查询到的原有评论行为
     */
    private void updateLikeOrDislikeCount(CommentAction commentAction, CommentAction findCommentAction) {
        Comment comment = commentMapper.findCommentById(commentAction.getCommentId());
        if (ObjectUtils.isEmpty(comment)) {
            throw new CommentException(MessageConstant.COMMENT_CONTENT_IS_EMPTY);
        }

        // 判断行为类型，并更新计数
        Integer action = commentAction.getAction();
        Integer findAction = findCommentAction.getAction();

        switch (action) {
            case CommentConstant.COMMENT_ACTION_NONE:
                handleActionNone(comment, findAction);
                break;
            case CommentConstant.COMMENT_ACTION_LIKE:
                handleLike(comment, findAction);
                break;
            case CommentConstant.COMMENT_ACTION_DISLIKE:
                handleDislike(comment, findAction);
                break;
        }

        // 保存更新后的评论
        commentMapper.updateComment(comment);

    }

    /**
     * 处理行为为"无"的情况
     */
    private void handleActionNone(Comment comment, Integer findAction) {
        if (findAction == CommentConstant.COMMENT_ACTION_LIKE) {
            comment.setLikeCount(Math.max(0, comment.getLikeCount() - 1));
        } else if (findAction == CommentConstant.COMMENT_ACTION_DISLIKE) {
            comment.setDislikeCount(Math.max(0, comment.getDislikeCount() - 1));
        }
    }

    /**
     * 处理点赞情况
     */
    private void handleLike(Comment comment, Integer findAction) {
        if (findAction == CommentConstant.COMMENT_ACTION_NONE) {
            comment.setLikeCount(comment.getLikeCount() + 1);
        } else if (findAction == CommentConstant.COMMENT_ACTION_DISLIKE) {
            comment.setDislikeCount(comment.getDislikeCount() - 1);
            comment.setLikeCount(comment.getLikeCount() + 1);
        }
    }

    /**
     * 处理点踩情况
     */
    private void handleDislike(Comment comment, Integer findAction) {
        if (findAction == CommentConstant.COMMENT_ACTION_NONE) {
            comment.setDislikeCount(comment.getDislikeCount() + 1);
        } else if (findAction == CommentConstant.COMMENT_ACTION_LIKE) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            comment.setDislikeCount(comment.getDislikeCount() + 1);
        }
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
        if (isAccountBanned(user)) {
            LocalDateTime banEndTime = user.getBanEndTime();
            if (banEndTime == null) {
                // 如果是永久封禁，就说被永久封禁
                throw new ForbiddenOrBanException(MessageConstant.ACCOUNT_IS_FOREVER_BAN);
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 如果不是，就显示结束时间
            throw new ForbiddenOrBanException(MessageConstant.USER_HAS_BANNED + user.getBanEndTime().format(dtf));
        }

        return user;
    }

    /**
     * 判断账号是否被封禁
     *
     * @param user
     * @return
     */
    private boolean isAccountBanned(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime banEndTime = user.getBanEndTime();
        if (banEndTime != null) {
            // 如果封禁结束时间在当前时间之前，解封账号
            if (banEndTime.isBefore(now)) {
                user.setBanEndTime(null);
                user.setBanStartTime(null);
                user.setAccountStatus(UserConstant.ENABLE);
                return false; // 账号已解封
            }
            return true; // 账号在封禁期内
        }

        LocalDateTime banStartTime = user.getBanStartTime();
        // 封禁期内
        return banStartTime != null;// 账号未被封禁
    }
}
