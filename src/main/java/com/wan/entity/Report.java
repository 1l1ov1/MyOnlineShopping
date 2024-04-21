package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 举报信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {
    /**
     * 举报id
     */
    private Long id;


    /**
     * 举报人id
     */
    private Long userId;

    /**
     * 举报的评论id
     */
    private Long commentId;
    /**
     * 举报原因
     */
    private String reason;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
