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



}
