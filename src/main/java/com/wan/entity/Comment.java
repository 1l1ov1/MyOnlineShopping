package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 评论者名字
     */
    private String username;

    /**
     * 评论者头像
     */
    private String avatar;
    /**
     * 商店id
     */
    private Long storeId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 商品评分 0.5-5分
     */
    private Double star;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 回复数
     */
    private Integer replyCount;

    /**
     * 不喜欢数
     */
    private Integer dislikeCount;
    /**
     * 父评论ID
     */
    private Long parentCommentId;

    /**
     * 举报次数
     */
    private Integer reportCount;

    /**
     * 评论状态
     */
    private Integer commentStatus;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
