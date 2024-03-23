package com.wan.dto;

import com.wan.entity.Cart;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartSubmitDTO {
    // 购买者id
    private Long userId;
    // 购物车列表
    private List<Cart> cartList;
    // 总金额
    private BigDecimal totalAmount;
}
