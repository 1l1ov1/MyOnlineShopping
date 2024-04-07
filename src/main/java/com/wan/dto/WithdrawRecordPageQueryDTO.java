package com.wan.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 前端提现记录参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRecordPageQueryDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 用户之前的金额
     */
    private BigDecimal money;
    /**
     * 提现金额
     */
    private BigDecimal withdrawMoney;
    /**
     * 用户的id
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 商店id
     */
    private Long storeId;
    /**
     * 商店名称
     */
    private String storeName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 付款方式
     */
    private Integer pay;

    /**
     * 排序方式
     */
    private Integer sort;

    private Integer page;
    private Integer pageSize;
}
