package com.wan.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wan.constant.UserConstant;
import com.wan.constant.WebSocketConstant;
import com.wan.entity.Message;
import com.wan.entity.User;
import com.wan.service.MessageService;
import com.wan.service.UserService;
import com.wan.utils.ObjectUtils;
import com.wan.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.*;

@Component
@ServerEndpoint(value = "/ws/user/{userId}")
public class NoticeUserWebSocketServer {
    // 存放会话对象
    private static final ConcurrentHashMap<Long, Session> sessionMap = new ConcurrentHashMap();

    // 创建消息队列
    private static final ConcurrentHashMap<Long, Queue<String>> messageQueue = new ConcurrentHashMap();

    private ThreadPoolExecutor threadPool;
    // 最大批处理数
    private static final int MAX_BATCH_SIZE = 100;


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("userId") Long userId) {
        System.out.println("收到来自用户客户端：" + userId + "的信息:" + message);
    }

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        System.out.println("用户客户端：" + userId + " 建立连接");

        // 将连接的会话对象存入map
        sessionMap.put(userId, session);


        // 如果用户连接了，就取消息队列里查看是否有消息
        // 得到消息队列
        Queue<String> messageQueue = NoticeUserWebSocketServer.messageQueue.get(userId);
        // 如果消息队列不为空
        if (messageQueue != null) {
            // 遍历消息队列，如果消息不为空，就发送消息
            while (!messageQueue.isEmpty()) {
                List<String> batchMessage = new ArrayList<>();
                for (int i = 0; i < MAX_BATCH_SIZE && !messageQueue.isEmpty(); i++) {
                    // 将消息添加到批量消息中
                    batchMessage.add(messageQueue.poll());
                }

                if (threadPool == null) {
                    threadPool = SpringContextUtils.getBean("threadPool", ThreadPoolExecutor.class);
                }

                // 提交批处理任务到线程池
                threadPool.submit(() -> {
                    for (String message : batchMessage) {
                        sendToSpecificUser(userId, message);
                    }
                });

            }
        }
        UserService userService = SpringContextUtils.getBean(UserService.class);
        // 将用户状态改为在线
        userService.update(User.builder()
                .id(userId)
                .isOnline(UserConstant.IS_ONLINE).build());
    }

    /**
     * 给指定用户发送消息
     *
     * @param userId  用户ID，用于标识消息接收者
     * @param message 要发送的消息内容（是把Map转成JSON字符串）
     */
    public static void sendToSpecificUser(Long userId, String message) {
        // 尝试根据用户ID从sessionMap中获取对应的会话Session
        Session targetSession = sessionMap.get(userId);
        if (targetSession != null) {
            // 如果找到对应的Session，则直接发送消息
            try {
                targetSession.getBasicRemote().sendText(message);
            } catch (Exception e) {
                // 处理发送消息时可能出现的异常
                e.printStackTrace();
            }
        } else {
            // 如果未找到对应的Session，即用户未登录，则将消息存入消息队列
            // 检查消息队列中是否存在用户退出的消息，如果存在且随后收到用户启动的消息，则移除之前的退出消息
            Queue<String> messageQueue = NoticeUserWebSocketServer.messageQueue
                    .computeIfAbsent(userId, id -> new ConcurrentLinkedQueue<>());
            Map<String, Object> messageMap = JSONObject.parseObject(message);
            Integer type = (Integer) messageMap.get("type");
            // 如果添加的是解封的消息
            if (Objects.equals(type, WebSocketConstant.USER_START)) {

                // 得到迭代器
                Iterator<String> iterator = messageQueue.iterator();
                // 遍历
                while (iterator.hasNext()) {
                    // 得到迭代器中的元素
                    String s = iterator.next();
                    // 转成map
                    Map<String, Object> map = JSONObject.parseObject(s);
                    // 取出类型
                    Integer ban = (Integer) map.get("type");
                    // 如果还是被封禁的状态
                    if (Objects.equals(ban, WebSocketConstant.USER_EXIT)) {
                        iterator.remove(); // 移除之前记录的用户退出消息
                        break;
                    }
                }
            }
            messageQueue.offer(message); // 将消息加入到队列中等待用户登录后发送
        }
    }

    /**
     * 连接关闭调用的方法
     *
     * @param userId
     */
    @OnClose
    public void onClose(@PathParam("userId") Long userId) {
        System.out.println("用户连接断开:" + userId);
        // 将用户下线
        UserService userService = SpringContextUtils.getBean(UserService.class);
        userService.update(User.builder()
                .id(userId)
                .isOnline(UserConstant.IS_NOT_ONLINE).build());
        sessionMap.remove(userId);
    }

}
