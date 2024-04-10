package com.wan.dto;

import com.wan.entity.Address;
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
    private Address address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
