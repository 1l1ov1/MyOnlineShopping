package com.wan.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@Component
@ServerEndpoint(value = "/ws/user/{userId}")
public class NoticeUserWebSocketServer {
    // 存放会话对象
    private static final ConcurrentHashMap<Long, Session> sessionMap = new ConcurrentHashMap();

    // 创建消息队列
    private static final ConcurrentHashMap<Long, Queue<String>> messageQueue = new ConcurrentHashMap();

    // 任务队列未达到队列容量时，最大可以同时运行的线程数量。
    private static final int CORE_POOL_SIZE = 5;
    // 任务队列中存放的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数。
    private static final int MAX_POOL_SIZE = 10;
    // 线程池中的线程数量大于 corePoolSize 的时候，如果这时没有新的任务提交，核心线程外的线程不会立即销毁，
    // 而是会等待，直到等待的时间超过了 keepAliveTime才会被回收销毁。
    private static final long KEEP_ALIVE_TIME = 1L;
    // 队列容量
    private static final int QUEUE_CAPACITY = 100;

    private static final ExecutorService executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.DiscardOldestPolicy());

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


                // 提交批处理任务到线程池
                executor.submit(() -> {
                    for (String message : batchMessage) {
                        sendToSpecificUser(userId, message);
                    }
                });

            }
        }
    }

    /**
     * 给指定用户发送消息
     * @param userId
     * @param message
     */
    public static void sendToSpecificUser(Long userId, String message) {
        Session targetSession = sessionMap.get(userId); // 根据storeId获取对应的Session
        if (targetSession != null) {
            try {
                targetSession.getBasicRemote().sendText(message); // 发送消息
            } catch (Exception e) {
                e.printStackTrace(); // 处理发送消息异常
            }
        } else {
            // 用户未登录时，将消息存入消息队列
            Queue<String> messageQueue = NoticeUserWebSocketServer.messageQueue
                    .computeIfAbsent(userId, id -> new ConcurrentLinkedQueue<>());
            messageQueue.offer(message);
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
        sessionMap.remove(userId);
    }

}
