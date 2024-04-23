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
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPageQueryVO implements Serializable {
    private Long id; // 编号
    private String username; // 账号
    private String password; // 密码
    private Integer status; // 用户身份
    private String phone; // 电话号码
    // private String address; // 地址
    // private Address address; // 默认地址
    private List<Address> addressList; // 所有地址
    private Store store; // 用户商店
    private String avatar; // 头像路径
    private Integer isOnline; // 是否在线 1在线 0不在线
    private Integer accountStatus; // 账户状态 0禁用 1启用
    private Integer forbiddenWord; // 禁言 0禁言 1未禁言
    private BigDecimal money; // 钱包
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
