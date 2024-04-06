package com.wan.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 导出数据
 */
@Data
@Builder
public class ExportDataVO {
    private BigDecimal turnover;//营业额

    private Integer successfulOrdersCount;//有效订单数

    private Double orderCompletionRate;//订单完成率

    private BigDecimal unitPrice;//平均客单价

    private Integer newUsers;//新增用户数
}
