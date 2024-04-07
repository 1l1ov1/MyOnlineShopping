package com.wan.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 提现记录分页查询返回值
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRecordPageQueryVO {

    /**
     * id
     */
    private Long id;
    /**
     * 原先的钱
     */
    private BigDecimal originMoney;
    /**
     * 提现金额
     */
    private BigDecimal withdrawMoney;
    /**
     * 现在的钱
     */
    private BigDecimal nowMoney;
    /**
     * 购买数量
     */
    private Integer number;
    /**
     * 商品总价
     */
    private BigDecimal totalPrice;
    /**
     * 卖家的用户id
     */
    private Long sellerId;
    /**
     * 卖家用户名
     */
    private String sellerName;
    /**
     * 买家用户的id
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 买家电话
     */
    private String phone;
    /**
     * 商店id
     */
    private Long storeId;
    /**
     * 商店名称
     */
    private String storeName;
    /**
     * 商品名
     */
    private String goodsName;
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
}
