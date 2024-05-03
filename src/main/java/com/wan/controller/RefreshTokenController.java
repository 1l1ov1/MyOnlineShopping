package com.wan.controller;

import com.wan.properties.JwtProperties;
import com.wan.result.Result;
import com.wan.utils.JwtUtils;
import com.wan.vo.RefreshVO;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/refresh")
@Api("刷新令牌")
@Slf4j
public class RefreshTokenController {

    @Autowired
    private JwtProperties jwtProperties;


    @GetMapping("/refreshToken")
    public Result<RefreshVO> refreshToken(HttpServletRequest request) {
        log.info("刷新令牌");
        // 得到刷新令牌
        String refreshToken = request.getHeader(jwtProperties.getUserRefreshTokenName());
        if (refreshToken == null) {
            // 如果说为空，证明用户已经很久没有上线了
            return Result.success("用户很久没有上线");
        }
        // 否则
        Claims claims = JwtUtils.parseJWT(jwtProperties.getUserRefreshTokenSecretKey(), refreshToken);
        // 创建新的refreshToken
        String newRefreshToken = JwtUtils.createJWT(jwtProperties.getUserRefreshTokenSecretKey(),
                jwtProperties.getUserRefreshTokenTtl(),
                claims);


        // 创建新的token
        String newToken = JwtUtils.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        return Result.success(RefreshVO.builder()
                .token(newToken)
                .refreshToken(newRefreshToken)
                .build());
    }
}
