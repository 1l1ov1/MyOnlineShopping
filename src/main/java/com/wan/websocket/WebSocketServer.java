package com.wan.websocket;

import com.wan.constant.MessageConstant;
import com.wan.exception.StoreException;
import com.wan.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
@ServerEndpoint(value = "/ws/store/{storeId}")
public class WebSocketServer {

    // 存放会话对象
    private static final ConcurrentHashMap<Long, Session> sessionMap = new ConcurrentHashMap();

    // 创建消息队列
    private static final ConcurrentHashMap<Long, Queue<String>> messageQueue = new ConcurrentHashMap();

    private ThreadPoolExecutor threadPool;

    // 最大批处理数
    private static final int MAX_BATCH_SIZE = 100;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("storeId") Long storeId) {
        System.out.println("商店客户端：" + storeId + " 建立连接");
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

                if (threadPool == null) {
                    threadPool = SpringContextUtils.getBean("threadPool", ThreadPoolExecutor.class);
                }

                // 提交批处理任务到线程池
                threadPool.submit(() -> {
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
        System.out.println("收到来自商店客户端：" + storeId + "的信息:" + message);
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


}
