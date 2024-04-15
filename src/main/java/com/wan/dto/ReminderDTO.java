package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户催单DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderDTO {
    /**
     * 商店id
     */
    private Long storeId;
    /**
     * 订单号
     */
    private Long ordersNumber;
}
