package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.entity.StoreSales;
import com.wan.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author 123
 * @description 针对表【store_sales(营业额数据表)】的数据库操作Mapper
 * @createDate 2024-03-30 22:09:11
 * @Entity com.wan.entity.StoreSales
 */
@Mapper
public interface StoreSalesMapper {

    /**
     * 插入营业额
     *
     * @param storeSales
     */
    @AutoFill(OperationType.INSERT)
    void insertStoreSales(StoreSales storeSales);

    /**
     * 根据商店ID、日期和分类ID查询指定分类在某一天的营业额详情
     *
     * @param storeId    商店ID
     * @param date       日期
     * @param categoryId 分类ID
     * @return 对应分类在指定日期的营业额记录
     */
    @Select("select * from store_sales where store_id = #{storeId} " +
            "and date = #{date} and category_id = #{categoryId}")
    StoreSales findSalesByCategoryInOneDay(Long storeId, LocalDate date, Long categoryId);

    /**
     * 根据商店ID和日期查询商店在某一天的所有分类总营业额
     *
     * @param storeId 商店ID
     * @param date    日期
     * @return 商店在指定日期所有分类的营业额记录列表
     */
    @Select("select * from store_sales where store_id = #{storeId} " +
            "and date = #{date}")
    List<StoreSales> findAllSalesInOneDay(Long storeId, LocalDate date);

    /**
     * 修改营业额
     *
     * @param storeSales
     */
    @AutoFill(OperationType.UPDATE)
    void update(StoreSales storeSales);

}
