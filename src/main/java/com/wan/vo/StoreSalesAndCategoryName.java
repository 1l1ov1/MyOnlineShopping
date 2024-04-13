package com.wan.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreSalesAndCategoryName implements Serializable {
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
     * 分类名称
     */
    private String categoryName;
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
}
