package com.wan.utils;

/**
 * 采用雪花算法计算订单号
 */
public class SnowFlakeUtil {

    /**
     * 初始化时间戳
     */
    private static final long timeStamp = 1660291200000L;

    /**
     * 表明机器Id（工作节点ID）
     */
    private static final long WORKER_ID = 1L;

    /**
     * 数据中心
     */
    private static final long DATA_CENTER = 1L;
    /**
     * 序列号
     */
    private static long sequence;

    /**
     * 上一次的时间戳
     */
    private static long lastTimestamp;

    /**
     * 序列号最大长度位数
     */
    private static final long SEQUENCE_BIT_LENGTH = 12L;


    /**
     * 数据中心左移动位数
     */
    private static final long DATE_CENTER_LEFT_SHIFT_BIT = SEQUENCE_BIT_LENGTH;

    /**
     * 数据中心占最大位数
     */
    private static final long DATA_CENTER_BIT_LENGTH = 5L;
    /**
     * 机器Id左移位数
     */
    private static final long WORKER_ID_LEFT_SHIFT_BIT = DATA_CENTER_BIT_LENGTH + SEQUENCE_BIT_LENGTH;

    /**
     * 时间戳左移动位数
     */
    private static final long TIMESTAMP_LEFT_SHIFT_BIT = WORKER_ID_LEFT_SHIFT_BIT + DATA_CENTER_BIT_LENGTH;

    static {
        // 初始化序列号
        sequence = 0L;
        // 上一次时间戳
        lastTimestamp = -1L;
    }

    /**
     * 生成下一个唯一标识Id
     * 该方法是静态同步方法，保证了在多线程环境下的安全性和唯一性。
     * 使用了时间戳、工作机器ID和序列号来生成Id。
     *
     * @return 生成的唯一标识Id
     * @throws RuntimeException 如果系统时钟回拨，为了保证Id的顺序性，会抛出异常。
     */
    public static synchronized long nextId() {
        // 获取当前时间戳
        long currentTimestamp = System.currentTimeMillis();

        // 检查系统时钟是否有回拨现象
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨，拒绝生成订单号");
        }

        // 处理在同一毫秒内生成Id的情况
        if (currentTimestamp == lastTimestamp) {
            // 序列号自增，防止溢出
            sequence = (sequence + 1) & 0xFFF;
            // 如果序列号溢出，则阻塞线程，等待下一毫秒
            if (sequence == 0) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                currentTimestamp = System.currentTimeMillis();
            }
        } else {
            // 如果是新的毫秒，则序列号重置
            sequence = 0L;
        }

        // 更新上一次的时间戳
        lastTimestamp = currentTimestamp;

        // 生成并返回唯一的Id
        return (currentTimestamp - timeStamp) << TIMESTAMP_LEFT_SHIFT_BIT |
                // 数据中心向左移动12位
                DATA_CENTER << DATE_CENTER_LEFT_SHIFT_BIT |
                // 机器Id向左移动5位
                WORKER_ID << WORKER_ID_LEFT_SHIFT_BIT |
                sequence;
    }
}
