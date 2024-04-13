package com.wan.vo;

import com.wan.entity.StoreSales;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * 门店销售量价汇总类
 * 用于存储门店销售的总金额和各个门店的销售详情列表
 */
public class StoreSalesVO implements Serializable {
    private BigDecimal totalRevenue; // 总销售额
    private List<StoreSalesWithStoreName> storeSalesList; // 门店销售详情列表
    private LocalDate start;
    private LocalDate end;
}
