package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String address; // 地址
    private Integer isOnline; // 是否在线 1在线 0不在线
    private Integer accountStatus; // 账户状态 0禁用 1启用
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
