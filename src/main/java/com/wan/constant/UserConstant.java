package com.wan.constant;

public class UserConstant {
    private UserConstant() {

    }
    // 用户身份
    public static final int SUPER_ADMINISTRATOR = 0; // 超级管理员
    public static final int MANAGER = 1; // 管理员
    public static final int COMMON_USER = 2; // 普通用户
    public static final int BUSINESSMAN = 3; // 商家
    // 用户在线状态
    public static final int IS_ONLINE = 1; // 在线
    public static final int IS_NOT_ONLINE = 0; // 不在线
    // 用户账号状态
    public static final int ENABLE = 1;// 启用
    public static final int DISABLE = 0; // 禁用

    public static final String DEFAULT_PASSWORD = "123456";
}
