package com.wan.websocket;

import com.wan.constant.MessageConstant;
import com.wan.exception.StoreException;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.*;

@Component
@ServerEndpoint(value = "/ws/{storeId}")
public class WebSocketServer {

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

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    // 最大批处理数
    private static final int MAX_BATCH_SIZE = 100;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("storeId") Long storeId) {
        System.out.println("客户端：" + storeId + " 建立连接");
        // 将连接的会话对象存入map
        sessionMap.put(storeId, session);


        // 如果商家连接了，就取消息队列里查看是否有消息
        // 得到消息队列
        Queue<String> messageQueue = WebSocketServer.messageQueue.get(storeId);
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
                executor.execute(() -> {
                    for (String message : batchMessage) {
                        sendToSpecificStore(storeId, message);
                    }
                });

            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("storeId") Long storeId) {
        System.out.println("收到来自客户端：" + storeId + "的信息:" + message);
    }

    /**
     * 连接关闭调用的方法
     *
     * @param storeId
     */
    @OnClose
    public void onClose(@PathParam("storeId") Long storeId) {
        System.out.println("连接断开:" + storeId);
        sessionMap.remove(storeId);
    }

    /**
     * 群发
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                // 服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将消息发送到指定的商店。
     *
     * @param storeId 商店的唯一标识符。
     * @param message 要发送的消息内容。
     *                该方法首先从sessionMap中根据storeId获取对应的Session对象。
     *                如果找到对应的Session，则尝试发送消息。如果发送过程中出现异常，则打印异常栈信息。
     *                如果没有找到对应的Session，则根据具体需求处理无法找到商店会话的情况，例如将消息暂存至消息队列中，等待商家登录后再发送。
     */
    public void sendToSpecificStore(Long storeId, String message) {
        Session targetSession = sessionMap.get(storeId); // 根据storeId获取对应的Session
        if (targetSession != null) {
            try {
                targetSession.getBasicRemote().sendText(message); // 发送消息
            } catch (Exception e) {
                e.printStackTrace(); // 处理发送消息异常
            }
        } else {
            // 商家未登录时，将消息存入消息队列
            Queue<String> messageQueue = WebSocketServer.messageQueue
                    .computeIfAbsent(storeId, id -> new ConcurrentLinkedQueue<>());
            messageQueue.offer(message);
        }
    }

    /**
     * 发送队列中的消息
     *
     * @param storeId 商店ID
     */
    private void sendQueueMessage(Long storeId) {

    }

}
