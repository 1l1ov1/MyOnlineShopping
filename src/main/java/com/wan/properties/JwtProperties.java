package com.wan.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "online-shopping.jwt")
@Data
public class JwtProperties {
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
    private String userRefreshTokenName;
    private long userRefreshTokenTtl;
    private String userRefreshTokenSecretKey;
    private long userTokenExpireTime;
}
