package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerMapper {
    Page<User> pageQuery(UserPageQueryDTO userPageQueryDTO);
}
