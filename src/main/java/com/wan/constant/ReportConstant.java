package com.wan.constant;

public class ReportConstant {

    /**
     * 每天最大举报次数
     */
    public static final Integer MAX_REPORT_COUNT_PER_DAY = 30;

    /**
     * 最大举报内容长度
     */
    public static final Integer MAX_REPORT_CONTENT_LENGTH = 300;

    /**
     * 最小举报内容长度
     */
    public static final Integer MIN_REPORT_CONTENT_LENGTH = 1;


     // 举报状态
    /**
     * 待处理
     */
    public static final Integer REPORT_STATUS_PENDING = 1;

    /**
     * 已处理
     */
    public static final Integer REPORT_STATUS_PROCESSED = 2;


    /**
     * 已奖励
     */
    public static final Integer REPORT_STATUS_REWARDED = 1;

    /**
     * 未奖励
     */
    public static final Integer REPORT_STATUS_UNREWARDED = 0;
}
