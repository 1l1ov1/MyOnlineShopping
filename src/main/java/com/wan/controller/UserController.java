package com.wan.controller;

import com.wan.constant.AddressConstant;
import com.wan.constant.JwtClaimConstant;
import com.wan.constant.MessageConstant;
import com.wan.constant.UserConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.UserLoginDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.Address;
import com.wan.entity.Store;
import com.wan.entity.User;
import com.wan.entity.VerificationCode;
import com.wan.properties.JwtProperties;
import com.wan.result.Result;
import com.wan.server.AddressService;
import com.wan.server.StoreService;
import com.wan.server.UserService;
import com.wan.utils.JwtUtils;
import com.wan.vo.UserLoginVO;
import com.wan.vo.UserPageQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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
        String code = (String) request.getSession().getAttribute("verify_code");
        // 忽略大小写
        if (!code.equalsIgnoreCase(userLoginDTO.getVerifyCode())) {
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

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .token(token)
                .build();
        return Result.success(userLoginVO, "登录成功");
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
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        System.out.println("验证码为：" + text);
        VerificationCode.output(image, resp.getOutputStream());
        return Result.success();
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("获取个人信息")
    public Result<UserPageQueryVO> getUserInfo() {
        Long userId = ThreadBaseContext.getCurrentId();
        // 使用完后就删除
        ThreadBaseContext.removeCurrentId();
        User user = userService.getUserById(userId);
        UserPageQueryVO userPageQueryVO = new UserPageQueryVO();
        BeanUtils.copyProperties(user, userPageQueryVO);
        List<Address> addressList = addressService.getAllAddressByUserId(userId);
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
    public Result<String> updatePwd(@RequestBody Map<String, String> pwdData) {
        log.info("用户重置密码{}", pwdData);
        Long id = ThreadBaseContext.getCurrentId();
        userService.updatePassword(pwdData, id);
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

    @PostMapping("/createStore/{storeName}")
    @ApiOperation("用户开店")
    public Result<String> createStore(@RequestBody UserLoginDTO userLoginDTO, @PathVariable String storeName) {
        log.info("用户开店... {}, {}",userLoginDTO, storeName);
        userService.createStore(userLoginDTO, storeName);
        return Result.success("开店成功");
    }
}
