package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsPurchaseDTO {
    private Long goodsId;
    private Long storeId;
    private String goodsName;
    private Integer number;
    private Double price;
    private Double discount;
    private BigDecimal totalPrice;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
