package com.wan.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportVO {
    /**
     * 举报id
     */
    private Long id;

    /**
     * 举报人id
     */
    private Long userId;

    /**
     * 举报人是否被奖励 0没有 1有
     */
    private Integer isAward;

    /**
     * 举报人用户名
     */
    private String username;
    /**
     * 举报的评论id
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private LocalDateTime commentCreateTime;

    /**
     * 评论状态
     */
    private Integer commentStatus;
    /**
     * 举报原因
     */
    private String reason;

    /**
     * 举报状态
     */
    private Integer status;
    /**
     * 被举报人id
     */
    private Long reportedUserId;

    /**
     * 被举报人的用户名
     */
    private String reportedUsername;

    /**
     * 举报次数
     */
    private Long reportCount;

    /**
     * 商店名
     */
    private String storeName;

    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
