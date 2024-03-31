package com.wan.vo;

import com.wan.entity.Goods;
import com.wan.entity.Store;
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
public class UserOrdersVO {
    private Long id;// 订单id
    private Goods goods; // 订单对应的商品
    private Store store; // 订单对应的商店
    private Integer pay; // 支付方式
    private Integer status; // 订单状态
    private Integer number; // 购买数量
    private BigDecimal totalPrice;// 总价
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
