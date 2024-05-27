package com.wan;

import com.wan.websocket.NoticeUserWebSocketServer;
import com.wan.websocket.UserChatWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
// @EnableWebMvc
// @EnableAspectJAutoProxy
@EnableScheduling // 开启定时任务
public class MyOnlineShoppingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MyOnlineShoppingApplication.class, args);
        NoticeUserWebSocketServer.setApplicationContext(context);
        UserChatWebSocket.setApplicationContext(context);
    }

}
