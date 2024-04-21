package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封禁或禁言DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForbiddenOrBanDTO {
    private Long reportedUserId;

    private Double forbiddenWordTime;

    private Double banTime;

    private String reportedUsername;

    private String type;
}
