package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO extends PageDTO{
    // id
    private Long id;
    // 名称
    private String categoryName;
    // 分类状态
    private Integer categoryStatus;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;

    // private Integer page;
    // private Integer pageSize;
    //
    // private Integer sort;
}
