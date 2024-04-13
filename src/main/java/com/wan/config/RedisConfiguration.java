package com.wan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.json.JacksonObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDateTime;

/**
 * Redis配置类
 */
@Configuration
public class RedisConfiguration {


    /**
     * 创建并配置RedisTemplate，用于操作Redis数据库。
     *
     * @param connectionFactory Redis连接工厂，用于创建与Redis服务器的连接。
     * @return 配置好的RedisTemplate实例，可以用于执行Redis操作。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    // 使用自定义的JacksonObjectMapper，其中已配置了JavaTimeModule
    ObjectMapper objectMapper = new JacksonObjectMapper();
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
            new Jackson2JsonRedisSerializer<>(Object.class);
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

    // 设置默认序列化方式为Jackson2JsonRedisSerializer，覆盖所有类型（包括LocalDateTime）
    template.setDefaultSerializer(jackson2JsonRedisSerializer);

    return template;
}


}
