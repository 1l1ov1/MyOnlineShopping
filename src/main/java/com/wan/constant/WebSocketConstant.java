package com.wan.constant;

/**
 * WebSocket消息类型常量类。
 * 提供了用于WebSocket通信的消息类型标识。
 */
public class WebSocketConstant {

    /**
     * 提醒接单的消息类型。
     */
    public static final Integer REMIND_ORDER = 1;

    /**
     * 用户催单的消息类型。
     */
    public static final Integer USER_URGE_ORDER = 2;


    /**
     * 用户退出的消息类型
     */
    public static final Integer USER_EXIT = 3;

    /**
     * 用户启用的消息类型
     */
    public static final Integer USER_START = 4;
    /**
     * 奖励用户
     */
    public static final Integer USER_AWARD = 5;

    /**
     * 用户聊天
     */
    public static final Integer USER_CHAT = 6;
}
