package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageQueryDTO {
    private Long id; // 编号
    private String username; // 用户名
    private String phone; // 电话
    private String address; // 地址
    private Integer status; // 用户身份
    private Integer accountStatus; // 账号状态
    private Integer isOnline; // 账号在线状态
    private LocalDateTime createTime; // 账号创建时间
    private Integer page; // 页码
    private Integer pageSize;// 每页显示数
}
