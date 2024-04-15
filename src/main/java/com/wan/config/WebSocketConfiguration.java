package com.wan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfiguration {

    /**
     * 创建并返回一个ServerEndpointExporter实例。
     * ServerEndpointExporter是Spring Boot中用于注册和暴露WebSocket端点的组件。
     * 它通过扫描包路径下所有的注解，将WebSocket端点注册到服务器中。
     *
     * @return ServerEndpointExporter 用于暴露WebSocket端点的组件实例
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
