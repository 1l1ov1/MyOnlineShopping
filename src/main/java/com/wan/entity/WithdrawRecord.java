package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRecord implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 买家id
     */
    private Long userId;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 商店id
     */
    private Long storeId;
    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品数量
     */
    private Integer number;

    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 原先的钱
     */
    private BigDecimal originMoney;
    /**
     * 提现金额
     */
    private BigDecimal withdrawMoney;

    /**
     * 支付方式
     */
    private Integer pay;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
