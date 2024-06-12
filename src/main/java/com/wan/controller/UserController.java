package com.wan.controller;

import com.wan.constant.*;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.*;
import com.wan.entity.*;

import com.wan.exception.AccountNotFountException;
import com.wan.exception.FieldException;
import com.wan.properties.JwtProperties;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.*;
import com.wan.utils.JwtUtils;

import com.wan.utils.RedisUtils;
import com.wan.vo.CommentPageQueryVO;
import com.wan.vo.UserLoginVO;
import com.wan.vo.UserPageQueryVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Api("用户公共相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private AddressService addressService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${HSK.domain}")
    private String HSK_DOMAIN;

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        log.info("用户登录：{}", userLoginDTO);
        // 先检查请求中是否有Cookie
        Cookie[] cookies = request.getCookies();
        // 如果没有
        if (cookies == null || cookies.length == 0) {
            // 如果Cookie不存在，即验证码过期，则返回错误信息
            throw new FieldException(MessageConstant.VERIFY_CODE_EXPIRE);
        }
        // 如果有，就去看看是否有验证码
        String key = Arrays.stream(request.getCookies())
                .filter(cookie -> RedisConstant.VERIFY_CODE.equals(cookie.getName()))
                .findFirst() // 只取第一个匹配的Cookie，如果有多个同名Cookie，只取第一个
                .map(Cookie::getValue) // 从找到的Cookie中提取值
                .orElse(null); // 如果没有找到匹配的Cookie，返回null
        if (key == null) {
            // 如果Cookie不存在，即验证码过期，则返回错误信息
            throw new FieldException(MessageConstant.VERIFY_CODE_EXPIRE);
        }
        // 得到缓存中的键后，在得到值
        String code = (String) redisTemplate.opsForValue().get(key);
        // 如果得到的验证码为空或者不匹配
        if (code == null || !code.equalsIgnoreCase(userLoginDTO.getVerifyCode())) {
            return Result.error(MessageConstant.VERIFY_CODE_ERROR);
        }

        // 开始登录
        User user = userService.login(userLoginDTO);
        // 创建Map集合
        Map<String, Object> claims = new HashMap<>();
        // 存放用户id
        claims.put(JwtClaimConstant.USER_ID, user.getId());
        // 创建token
        String token = JwtUtils.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims
        );

        String refreshToken = JwtUtils.createJWT(jwtProperties.getUserRefreshTokenSecretKey(),
                jwtProperties.getUserRefreshTokenTtl(),
                claims);

        return Result.success(UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .token(token)
                .refreshToken(refreshToken)
                .build(), "登录成功");
    }

    /**
     * 用户注册
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<String> register(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("新增用户：{}", userLoginDTO);
        userService.addUser(userLoginDTO);
        return Result.success();
    }

    @GetMapping("/verify")
    @ApiOperation("生成验证码")
    public Result<String> generateVerifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode(130, 30);
        BufferedImage image = code.getImage(5, 3);
        String text = code.getText();

        // 随机键名
        String KEY = UUID.randomUUID().toString();
        // 存放到cookie中
        Cookie cookie = new Cookie(RedisConstant.VERIFY_CODE, KEY);
        cookie.setMaxAge(120); // 2分钟
        // 由于内外网穿透，这是前端的域名，这里要将cookie保存到对应域名下
        cookie.setDomain(HSK_DOMAIN);
        // 设置为登录页面路径
        cookie.setPath("/");
        resp.addCookie(cookie);
        // 保存到缓存中
        redisTemplate.opsForValue().set(KEY, text);
        // 设置过期时间 2分钟
        redisTemplate.expire(KEY, 120 * 1000, TimeUnit.MILLISECONDS);
        System.out.println("验证码为：" + text);
        VerificationCode.output(image, resp.getOutputStream());
        return Result.success();
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("获取个人信息")
    public Result<UserPageQueryVO> getUserInfo() {
        System.out.println("获取用户信息...");
        Long userId = ThreadBaseContext.getCurrentId();
        // 使用完后就删除
        ThreadBaseContext.removeCurrentId();
        // 得到该用户
        User user = userService.getUserById(userId);
        // 如果用户不存在
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 如果用户存在
        UserPageQueryVO userPageQueryVO = new UserPageQueryVO();
        // 拷贝
        BeanUtils.copyProperties(user, userPageQueryVO);
        // 得到用户的所有收货地址
        List<Address> addressList = addressService.getAllAddressByUserId(userId);
        // 查询其店铺
        Store store = storeService.findStoreByUserId(userId);
        userPageQueryVO.setAddressList(addressList);
        userPageQueryVO.setStore(store);
        return Result.success(userPageQueryVO, "获取个人信息成功");
    }

    @GetMapping("/userLogout")
    @ApiOperation("用户退出")
    public Result<String> logout() {
        log.info("用户退出...");
        // 得到用户id
        Long userId = ThreadBaseContext.getCurrentId();
        ThreadBaseContext.removeCurrentId();
        //  创建用户
        User user = User.builder()
                .id(userId)
                .isOnline(UserConstant.IS_NOT_ONLINE)
                .build();
        // 修改用户信息
        userService.update(user);
        return Result.success("退出成功");
    }

    @GetMapping("/getDetail")
    @ApiOperation("得到用户信息")
    public Result<UserPageQueryVO> getDetail(Long id, Integer isDefault) {
        log.info("得到用户信息{}, {}", id, isDefault);

        // 否则去数据库查询
        User user = userService.getDetail(id);
        // 查询其对应的默认地址
        Address address = addressService.getAddressByUserId(id, isDefault);
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        // 创建视图对象
        UserPageQueryVO userPageQueryVO = new UserPageQueryVO();
        // 拷贝到视图对象中
        BeanUtils.copyProperties(user, userPageQueryVO);

        userPageQueryVO.setAddressList(addressList);
        return Result.success(userPageQueryVO, "查询成功");
    }

    @PatchMapping("/updatePwd")
    @ApiOperation("重置密码")
    public Result<String> updatePwd(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        log.info("用户重置密码{}", updatePasswordDTO);
        userService.updatePassword(updatePasswordDTO);
        return Result.success("密码修改成功");
    }

    @PatchMapping("/forgetPwd")
    @ApiOperation("忘记密码")
    public Result<String> forgetPwd(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        log.info("用户重置密码{}", updatePasswordDTO);
        userService.updatePassword(updatePasswordDTO);
        return Result.success("密码修改成功");
    }


    @PutMapping("/updateUser")
    @ApiOperation("修改用户自己的信息")
    public Result<String> update(@RequestBody UserPageQueryDTO userPageQueryDTO) {
        log.info("用户修改自己的信息{}", userPageQueryDTO);
        // 得到用户和对应的地址
        User user = new User();
        List<Address> addressList = userPageQueryDTO.getAddressList();
        // 拷贝属性到user中
        BeanUtils.copyProperties(userPageQueryDTO, user);
        // 修改用户
        userService.update(user);
        // 修改地址
        addressService.update(addressList);
        return Result.success("修改成功");
    }

    @PostMapping("/createStore")
    @ApiOperation("用户开店")
    public Result<String> createStore(@RequestBody UserCreateStoreDTO userCreateStoreDTO) {
        log.info("用户开店... {}", userCreateStoreDTO);
        userService.createStore(userCreateStoreDTO);
        return Result.success("开店成功");
    }

    @GetMapping("/recharge/{value}")
    @ApiOperation("用户充值")
    public Result<String> recharge(@PathVariable Double value) {
        log.info("用户充值 {}", value);
        Long userId = ThreadBaseContext.getCurrentId();
        // 用完就清空
        ThreadBaseContext.removeCurrentId();
        // 根据id查询用户id
        User user = userService.getUserById(userId);
        // 设置用户金额
        user.setMoney(user.getMoney().add(BigDecimal.valueOf(value)));
        userService.update(user);
        return Result.success("充值成功");
    }

    @PostMapping("/addressDefault")
    @ApiOperation("设为默认地址")
    public Result<String> updateAddressDefault(@RequestBody Address address) {
        log.info("设为默认地址 {}", address);
        addressService.update(address);
        return Result.success("修改成功");
    }

    @PostMapping("/send")
    @ApiOperation("发送开店申请")
    public Result<String> sendApplyForCreateStore(@RequestBody ApplyDTO applyDTO) {
        log.info("发送开店申请, {}", applyDTO);
        applyService.addApply(applyDTO);
        return Result.success("发送成功");
    }

    @PostMapping("/check")
    @ApiOperation("/检查申请格式")
    public Result<Boolean> checkApply(@RequestBody ApplyDTO applyDTO) {
        log.info("检查开店申请, {}", applyDTO);
        applyService.checkApply(applyDTO);
        return Result.success("格式正确");
    }

    @GetMapping("/findApply/{username}")
    @ApiOperation("查询用户是否已经发送过申请并审核通过")
    public Result<Apply> findApplyExist(@PathVariable String username) {
        log.info("查询用户是否发送过申请并审核通过 {}", username);
        Apply apply = applyService.findApply(username);
        return Result.success(apply);
    }


    @PostMapping("/buy")
    @ApiOperation("立即购买")
    public Result<String> buyGoods(@RequestBody GoodsPurchaseDTO goodsPurchaseDTO) {
        log.info("立即购买 {}", goodsPurchaseDTO);
        userService.buy(goodsPurchaseDTO);
        // 清除该用户未发货订单和全部订单的缓存
        Long userId = ThreadBaseContext.getCurrentId();
        ThreadBaseContext.removeCurrentId();
        RedisUtils.clearRedisCache(redisTemplate,
                RedisConstant.USER_ORDERS + OrdersConstant.UNSHIPPED_ORDER + '-' + userId,
                RedisConstant.USER_ORDERS + OrdersConstant.ALL_ORDERS + '-' + userId);
        return Result.success("购买成功");
    }

    @GetMapping("/queryOrders/{userId}")
    @ApiOperation("用户查询某种类型的订单")
    public Result<PageResult> userQueryOrders(@PathVariable Long userId, @RequestParam Integer target, @RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        log.info("用户查询某种类型的订单 {}, {}, {}, {}", userId, target, currentPage, pageSize);
        PageResult pageResult = RedisUtils.redisGetHashValues(redisTemplate,
                RedisConstant.USER_ORDERS + target + '-' + userId + "-" + currentPage + "-" + pageSize, PageResult.class);
        if (pageResult != null) {
            return Result.success(pageResult);
        }
        pageResult = userService.queryOneTypeOrders(userId, target, currentPage, pageSize);
        RedisUtils.redisHashPut(redisTemplate,
                RedisConstant.USER_ORDERS + target + '-' + userId + "-" + currentPage + "-" + pageSize, pageResult,
                1L, TimeUnit.HOURS);
        return Result.success(pageResult);
    }


    @GetMapping("/applyRefund/{id}")
    @ApiOperation("申请退款")
    public Result<String> applyRefund(@PathVariable Long id) {
        log.info("申请退款, {}", id);
        userService.applyRefund(id);
        return Result.success("申请退款成功");
    }

    @PostMapping("/addAddress")
    @ApiOperation("添加收货地址")
    public Result<String> addAddress(@RequestBody Address address) {
        log.info("添加收货地址 {}", address);
        userService.addAddress(address);
        return Result.success("添加成功");
    }

    @DeleteMapping("/deleteAddress")
    @ApiOperation("删除非默认地址")
    public Result<String> addAddress(@RequestParam Long id) {
        log.info("删除收货地址 {}", id);
        userService.deleteAddress(id);
        return Result.success("删除成功");
    }

    @PostMapping("/reminder")
    @ApiOperation("用户催单")
    public Result<String> reminder(@RequestBody ReminderDTO reminderDTO) {
        log.info("用户催单 {}", reminderDTO);
        userService.reminder(reminderDTO);
        return Result.success("发送成功");
    }

    @PostMapping("/queryComment")
    @ApiOperation("查询某商品的评论")
    public Result<CommentPageQueryVO> queryComment(@RequestBody CommentPageQueryDTO commentPageQueryDTO) {
        log.info("查询某商品的评论 {}", commentPageQueryDTO);
        // 从缓存中找
        String key = RedisConstant.STORE_COMMENT + checkCommentType(commentPageQueryDTO.getType()) + commentPageQueryDTO.getStoreId() + "-" + commentPageQueryDTO.getGoodsId();
        CommentPageQueryVO commentPageQueryVO = RedisUtils.redisGetHashValues(redisTemplate,
                key,
                CommentPageQueryVO.class);
        if (!ObjectUtils.isEmpty(commentPageQueryVO)) {
            return Result.success(commentPageQueryVO);
        }
        // 没有就去数据库查
        commentPageQueryVO = userService.queryComments(commentPageQueryDTO);
        RedisUtils.redisHashPut(redisTemplate,
                key,
                commentPageQueryVO,
                1L, TimeUnit.HOURS);
        return Result.success(commentPageQueryVO);
    }

    @PostMapping("/queryCommentAction")
    @ApiOperation("查询评论的action")
    public Result<List<CommentAction>> queryCommentAction(@RequestBody CommentActionDTO commentActionDTO) {
        log.info("查询评论的action {}", commentActionDTO);
        List<CommentAction> commentActionList = RedisUtils.redisStringGet(redisTemplate,
                RedisConstant.STORE_COMMENT_ACTION + commentActionDTO.getStoreId()
                        + "-" + commentActionDTO.getGoodsId() + "-" + commentActionDTO.getUserId(), List.class);
        if (!ObjectUtils.isEmpty(commentActionList)) {
            return Result.success(commentActionList);
        }
        commentActionList = userService.queryCommentsAction(commentActionDTO);
        RedisUtils.redisStringSet(redisTemplate,
                RedisConstant.STORE_COMMENT_ACTION + commentActionDTO.getStoreId()
                        + "-" + commentActionDTO.getGoodsId() + "-" + commentActionDTO.getUserId(),
                commentActionList,
                1L, TimeUnit.HOURS);
        return Result.success(commentActionList);
    }

    @PostMapping("/addComment")
    @ApiOperation("添加评论")
    public Result<String> addComment(@RequestBody Comment comment) {
        log.info("添加评论 {}", comment);
        userService.addComment(comment);
        // 删除缓存
        Long storeId = comment.getStoreId();
        Long goodsId = comment.getGoodsId();
        RedisUtils.clearRedisCache(redisTemplate,
                RedisConstant.STORE_COMMENT_ACTION + storeId + "-" + goodsId);
        RedisUtils.clearRedisCacheByPattern(redisTemplate,
                RedisConstant.STORE_COMMENT + "*" + storeId + "-" + goodsId);
        return Result.success("添加成功");
    }

    @PutMapping("/updateCommentAction")
    @ApiOperation("修改评论的点赞数")
    public Result<String> updateCommentAction(@RequestBody CommentAction commentAction) {
        log.info("修改评论状态 {}", commentAction);
        userService.updateCommentAction(commentAction);
        // 删除缓存
        Long storeId = commentAction.getStoreId();
        Long goodsId = commentAction.getGoodsId();
        RedisUtils.clearRedisCacheByPattern(redisTemplate,
                RedisConstant.STORE_COMMENT_ACTION + storeId + "-" + goodsId + "*");
        RedisUtils.clearRedisCacheByPattern(redisTemplate,
                RedisConstant.STORE_COMMENT + "*" + storeId + "-" + goodsId);
        return Result.success("修改成功");
    }

    @PostMapping("/addReport")
    @ApiOperation("添加举报")
    public Result<String> addReport(@RequestBody Report report) {
        log.info("添加举报 {}", report);
        Comment comment = userService.addReport(report);
        // 删除缓存
        RedisUtils.clearRedisCacheByPattern(redisTemplate,
                RedisConstant.STORE_COMMENT + "*-" + comment.getStoreId() + "-" + comment.getGoodsId());
        return Result.success("举报成功");
    }


    /**
     * 检查评论类型。
     *
     * @param type 评论类型的整数值。1 表示好评，2 表示差评，0 或其他值表示全部评论。
     * @return 返回对应的评论类型字符串。"good" 表示好评，"bad" 表示差评，"all" 表示全部评论。
     */
    private String checkCommentType(Integer type) {
        if (type == null) {
            return "all-";
        }
        switch (type) {
            case 1:
                return "good-"; // 好评
            case 2:
                return "bad-"; // 差评
            case 0:
            default:
                return "all-"; // 全部评论
        }
    }


}
