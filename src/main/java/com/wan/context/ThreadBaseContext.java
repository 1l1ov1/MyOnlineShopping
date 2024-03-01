package com.wan.context;

// 存放本地线程变量
/*
ThreadLocal 中填充的的是当前线程的变量，该变量对其他线程而言是封闭且隔离的，
ThreadLocal 为变量在每个线程中创建了一个副本，这样每个线程都可以访问自己内部的副本变量。
 */
public class ThreadBaseContext {
    // 创建一个本地线程变量
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }
}
