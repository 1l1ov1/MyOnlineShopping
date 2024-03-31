package com.wan.entity;

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
public class User {
    private Long id; // 编号
    private String username; // 账号
    private String password; // 密码
    private Integer status; // 用户身份
    private String phone; // 电话号码
    // private String address; // 地址
    private String avatar; // 头像路径
    private Integer isOnline; // 是否在线 1在线 0不在线
    private Integer accountStatus; // 账户状态 0禁用 1启用
    private BigDecimal money; // 用户钱包
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间

    /**
     * 减少余额
     * 该方法用于减少当前账户的余额。它会将当前余额减去指定的商品总价。
     * @param totalPrice 要从余额中扣除的总金额。类型为BigDecimal，以确保精确的货币计算。
     */
    public void decreaseBalance(BigDecimal totalPrice) {
        // 设置新的余额为当前余额减去总价
        setMoney(getMoney().subtract(totalPrice));
    }
}
