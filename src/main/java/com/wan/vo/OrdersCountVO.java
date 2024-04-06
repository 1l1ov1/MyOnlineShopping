package com.wan.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 订单数量
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersCountVO {
    /**
     * 所有订单的数量集合
     */
    private List<Integer> totalOrdersCountList;

    /**
     * 成功的订单数量集合
     */
    private List<Integer> successfulOrdersCountList;
    /**
     * 订单总量
     */
    private Integer totalOrdersCount;
    /**
     * 成功订单数量
     */
    private Integer successfulOrdersCount;
    /**
     * 订单完成率
     */
    private Double ordersCompletionRate;
    /**
     * 开始时间
     */
    private LocalDate start;
    /**
     * 结束时间
     */
    private LocalDate end;
}
