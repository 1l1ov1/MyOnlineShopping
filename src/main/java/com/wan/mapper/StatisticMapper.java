package com.wan.mapper;

import com.wan.dto.GoodsSalesDTO;
import com.wan.vo.StoreSalesAndCategoryName;
import com.wan.vo.StoreSalesWithStoreName;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StatisticMapper {

    /**
     * 得到某个时间范围的用户数量 如果只传递end 就是总的 都传递就是注册的数量
     * @param start
     * @param end
     * @return
     */
    Integer queryUserCountByDay(LocalDateTime start, LocalDateTime end);


    /**
     * 查询某个商店的某个范围的营业额
     *
     * @param start
     * @param end
     * @param storeId
     * @return
     */
    List<StoreSalesWithStoreName> queryStoreSalesInOneDay(LocalDate start, LocalDate end, Long storeId);

    /**
     * 得到某个商店在某个范围的营业额
     *
     * @param start
     * @param end
     * @return
     */
    BigDecimal getTotalRevenue(LocalDate start, LocalDate end, Long storeId);


    /**
     * 得到总订单人数
     * @param start
     * @param end
     * @param storeId
     * @return
     */
    Integer getTotalOrdersCount(LocalDate start, LocalDate end, Long storeId);

    /**
     * 得到某个商店在某个范围的营业额
     * @param start
     * @param end
     * @param storeId
     * @return
     */
    List<StoreSalesAndCategoryName> getStoreSales(LocalDate start, LocalDate end, Long storeId);


    /**
     * 查询指定状态的订单数
     * @param beginTime
     * @param endTime
     * @param status
     * @param storeId
     * @return
     */
    Integer queryOrdersCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status, Long storeId);

    /**
     * 查询某个状态的销量前10
     * @param start
     * @param end
     * @param storeId
     * @param status
     * @return
     */

    List<GoodsSalesDTO> getSalesTop10(LocalDate start, LocalDate end, Long storeId, Integer status);
}
