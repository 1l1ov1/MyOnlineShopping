package com.wan.interceptor;

import com.wan.constant.JwtClaimConstant;
import com.wan.constant.UserConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.entity.User;
import com.wan.mapper.UserMapper;
import com.wan.properties.JwtProperties;
import com.wan.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;
    @Value("${HSK.domain}")
    private String HSK_DOMAIN;

    /**
     * 在执行方法前，先进行拦截校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            // 如果是其他资源，直接放行
            return true;
        }

        // 如果是方法，就看看是否携带token
        // 如果前端请求头传递了token

        String token = request.getHeader(jwtProperties.getUserTokenName());
        // 校验
        try {
            // 对token进行解密
            Claims claims = JwtUtils.parseJWT(jwtProperties.getUserSecretKey(), token);
            // 从token中得到用户的id
            Long userId = Long.valueOf(claims.get(JwtClaimConstant.USER_ID).toString());
            // 将该id保存到本地线程变量中
            ThreadBaseContext.setCurrentId(userId);

            return true;
        } catch (Exception ex) {
            // 不通过，返回响应码
            response.setStatus(401);
            return false;
        }
    }
}
