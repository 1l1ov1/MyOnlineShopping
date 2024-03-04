package com.wan.controller;

import com.wan.constant.JwtClaimConstant;
import com.wan.constant.MessageConstant;
import com.wan.constant.UserConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.UserLoginDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.User;
import com.wan.entity.VerificationCode;
import com.wan.properties.JwtProperties;
import com.wan.result.Result;
import com.wan.server.UserService;
import com.wan.utils.JwtUtils;
import com.wan.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api("用户公共相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

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
    public Result<User> getUserInfo() {
        Long userId = ThreadBaseContext.getCurrentId();
        // 使用完后就删除
        ThreadBaseContext.removeCurrentId();
        User user = userService.getUserById(userId);

        return Result.success(user,"获取个人信息成功");
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

}
