package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.UserLoginDTO;
import com.wan.entity.User;
import com.wan.enumeration.OperationType;
import com.wan.vo.UserOrdersVO;
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
     * 根据ids得到用户列表
     * @param ids
     * @return
     */
    List<User> findUserByIds(List<Long> ids);
    /**
     * 删除用户
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 批量修改用户
     * @param userList
     */
    @AutoFill(OperationType.UPDATE)
    void batchUpdateUsers(List<User> userList);


    /**
     * 查询某种类型
     * @param userId
     * @param target
     * @return
     */
    Page<UserOrdersVO> queryOneTypeOrders(Long userId, Integer target);

    /**
     * 得到管理员
     * @return
     */
    @Select("select * from user where status = #{status}")
    User getAdministrator(Integer status);
}
