package com.wan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfiguration {
    // 核心线程数
    private static final int CORE_THREAD_SIZE = 10;
    // 最大线程数
    private static final int MAX_THREAD_SIZE = 100;

    // 时间
    private static final int KEEP_ALIVE_TIME = 10;

    // 单位
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    // 阻塞队列
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>(100);
    // 创建线程池

    @Bean("threadPool")
    public ThreadPoolExecutor createThreadPool() {
        return new ThreadPoolExecutor(
                CORE_THREAD_SIZE,
                MAX_THREAD_SIZE,
                KEEP_ALIVE_TIME, TIME_UNIT,
                WORK_QUEUE,
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }
}
