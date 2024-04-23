package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 奖励用户DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardUserDTO {
    private Long userId;

    private Integer award;

    /**
     * 举报id
     */
    private Long reportId;
}
