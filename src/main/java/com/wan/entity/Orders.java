package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    private Long id;
    private String goodsName;
    private Long userId;
    private Long goodsId;
    private Long storeId;
    private Integer number;
    private BigDecimal totalPrice;
    private Integer pay;
    private Integer status;
    private Address address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
