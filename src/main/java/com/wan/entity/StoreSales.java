package com.wan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 营业额数据表
 *
 * @TableName store_sales
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreSales implements Serializable {
    /**
     * 营业额id
     */
    private Long id;

    /**
     * 今日日期
     */
    private LocalDate date;

    /**
     * 商店ID
     */
    private Long storeId;

    /**
     * 当日营业额
     */
    private BigDecimal dailySales;

    /**
     * 订单数
     */
    private Integer orderCount;

    /**
     * 平均订单金额
     */
    private BigDecimal avgOrderAmount;

    /**
     * 产品类别ID
     */
    private Long categoryId;

    /**
     * 用户数量，来表示该店铺的火热和商品的热销程度
     */
    private Integer userCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 添加营业额
     * 该方法用于增加订单数量和累计日销售额。
     *
     * @param number     订单数量，整型。表示要增加的订单数量。
     * @param totalPrice 订单总价格，BigDecimal类型。表示要累加的订单总价格。
     */
    public void addSales(Integer number, BigDecimal totalPrice) {
        // 增加订单数量
        setOrderCount(getOrderCount() + number);
        // 累加日销售额
        setDailySales(getDailySales().add(totalPrice));
        // 该类商品的购买人数增加1
        setUserCount(getUserCount() + 1);
        // 计算平均订单金额
        setAvgOrderAmount(getDailySales()
                .divide(new BigDecimal(getOrderCount()), 2, RoundingMode.HALF_UP));
    }

}