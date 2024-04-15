package com.wan.vo;

import com.wan.entity.Address;
import com.wan.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersPageQueryVO implements Serializable {
    // 订单id
    private Long id;
    // 订单号
    private Long ordersNumber;
    // 商品名
    private String goodsName;
    // 商品价钱
    private BigDecimal totalPrice;
    // 商品数量
    private Integer number;
    // 商品封面
    private String coverPic;
    // 订单状态
    private Integer status;
    // 店铺
    private Store store;
    // 购买者
    private String username;
    // 购买者电话
    private String phone;
    // 购买者地址
    private Address address;
    // 订单创建时间
    private LocalDateTime createTime;
    // 订单修改时间
    private LocalDateTime updateTime;
}
