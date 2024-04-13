package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store implements Serializable {
    // id
    private Long id;
    // 用户id
    private Long userId;
    // 店名
    private String storeName;
    // 店的地址
    private Address address;
    // 店的状态
    private Integer status;
    // 商店logo
    private String logo;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;
}
