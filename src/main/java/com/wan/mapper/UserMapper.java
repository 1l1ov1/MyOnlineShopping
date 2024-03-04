package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.dto.UserLoginDTO;
import com.wan.entity.User;
import com.wan.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 按照用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    /**
     * 插入用户
     * @param user
     */
    @Insert("insert into user(username, password, phone, create_time, update_time, status, account_status, is_online) " +
            "VALUES (#{username},#{password}, #{phone}, #{createTime},#{updateTime},#{status},#{accountStatus},#{isOnline})")
    @AutoFill(OperationType.INSERT)
    void insert(User user);

    /**
     * 修改用户
     * @param user
     */
    @AutoFill(OperationType.UPDATE)
    void update(User user);

    /**
     * 根据id查询
     * @param userId
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    /**
     * 删除用户
     * @param ids
     */
    void deleteByIds(List<Long> ids);
}
