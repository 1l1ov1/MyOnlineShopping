package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封禁或禁言DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForbiddenOrBanDTO {

    /**
     * 举报id
     */
    private List<Long> reportId;

    /**
     * 被举报人
     */
    private Long reportedUserId;

    /**
     * 禁言时间
     */
    private Double forbiddenWordTime;
    /**
     * 封禁时间
     */
    private Double banTime;

    /**
     * 被举报人用户名
     */
    private String reportedUsername;

    /**
     * 类型
     */
    private String type;
}
