package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.ApplyDTO;
import com.wan.entity.Address;
import com.wan.entity.Apply;
import com.wan.enumeration.OperationType;
import com.wan.vo.ApplyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplyMapper {
    @AutoFill(OperationType.INSERT)
    void insertApply(ApplyDTO applyDTO);

    @AutoFill(OperationType.INSERT)
    void insertApplyAddress(@Param("address") Address address,@Param("id") Long id);

    /**
     * 根据用户名查询待审核或者已通过的申请
     * @param username
     * @return
     */
    Apply findApprovedOrUnderApply(String username);

    @Select("select * from apply_for_create_store where username = #{username}")
    List<Apply> findUserAllApplies(String username);
    /**
     * 分页查询
     * @param applyDTO
     * @return
     */
    Page<ApplyVO> pageQuery(ApplyDTO applyDTO);

    /**
     * 修改申请
     * @param applyDTO
     */
    @AutoFill(OperationType.INSERT)
    void updateApply(ApplyDTO applyDTO);

    /**
     * 批量查询申请
     * @param ids
     * @return
     */
    List<Apply> findAppliesById(List<Long> ids);

    /**
     * 批量删除申请
     * @param ids
     */
    void batchDeleteApplies(List<Long> ids);

}
