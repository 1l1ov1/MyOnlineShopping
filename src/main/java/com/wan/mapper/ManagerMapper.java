package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.dto.StorePageQueryDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.StoreSales;
import com.wan.enumeration.OperationType;
import com.wan.enumeration.StoreSalesRangeType;
import com.wan.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ManagerMapper {
    /**
     * 分页查询
     *
     * @param userPageQueryDTO
     * @return
     */
    List<UserPageQueryVO> pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 修改用户
     *
     * @param userPageQueryDTO
     */
    @AutoFill(OperationType.UPDATE)
    void update(UserPageQueryDTO userPageQueryDTO);

    /**
     * 查询某范围的营业额
     *
     * @param start
     * @param end
     * @return
     */
    List<StoreSalesWithStoreName> queryStoreSalesInOneDay(LocalDate start, LocalDate end);

    /**
     * 得到某个区间内的总营业额
     * @param start
     * @param end
     * @return
     */
    BigDecimal getTotalRevenue(LocalDate start , LocalDate end);

    /**
     * 得到用户数量
     * @return
     */
    Integer queryAllUserCountInOneDay();

    /**
     * 某区间的注册用户数量
     * @param start
     * @param end
     * @return
     */
    List<Integer> queryRegisterUserCountInOneDay(LocalDate start, LocalDate end);
}
