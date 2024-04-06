package com.wan.vo;

import com.wan.entity.StoreSales;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 订单用户数量
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersUserCountVO {
    /**
     * 总用户数量
     */
    private Integer totalUserCount;
    /**
     * 营业额
     */
    private List<StoreSalesAndCategoryName> storeSalesAndCategoryNameList;

    /**
     * 开始时间
     */
    private LocalDate start;
    /**
     * 结束时间
     */
    private LocalDate end;
}
