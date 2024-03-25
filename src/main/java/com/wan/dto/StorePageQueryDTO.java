package com.wan.dto;

import com.wan.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorePageQueryDTO {
    // id
    private Long id;
    // 用户id
    private Long userId;
    // 店名
    private String storeName;
    // 店的状态
    private Integer status;
    // 用户名称
    private String username;
    // 商店地址
    private Address address;
    // 商店logo
    private String logo;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;

    private Integer page; // 页码
    private Integer pageSize;// 每页显示数

    private Integer sort;
}
