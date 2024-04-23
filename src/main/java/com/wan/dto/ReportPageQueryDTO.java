package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportPageQueryDTO {

    private Long storeId;

    private String username;

    private String reportedUsername;

    private String storeName;

    private String goodsName;

    private Integer page;

    private Integer pageSize;

    private Integer sort;
}
