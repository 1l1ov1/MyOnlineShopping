package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.User;
import com.wan.enumeration.OperationType;
import com.wan.vo.UserPageQueryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerMapper {
    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    // Page<UserPageQueryVO> pageQuery(UserPageQueryDTO userPageQueryDTO);
    List<UserPageQueryVO> pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 修改用户
     * @param userPageQueryDTO
     */
    @AutoFill(OperationType.UPDATE)
    void update(UserPageQueryDTO userPageQueryDTO);


}
