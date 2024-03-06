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
public class Address {
    // id
    private Long id;
    // 用户id
    private Long userId;
    // 省级区划编号
    private String provinceCode;
    // 省级名称
    private String provinceName;
    // 市级区划编号
    private String cityCode;
    // 市级名称
    private String cityName;
    // 区级区划编号
    private String districtCode;
    // 区级名称
    private String districtName;
    // 详细地址
    private String detail;
    // 是否默认 0 否 1是
    private Integer isDefault;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
}
