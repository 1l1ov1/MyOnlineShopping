package com.wan.service;

import com.wan.dto.UserPageQueryDTO;
import com.wan.result.PageResult;
import com.wan.vo.StoreSalesVO;
import com.wan.vo.UserCountVO;

import java.util.List;

public interface ManagerService {
    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 启用和禁用用户账号
     * @param accountStatus
     * @param id
     */
    void startOrStop(Integer accountStatus, Long id);

    /**
     * 批量删除用户
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 管理员添加用户
     * @param userPageQueryDTO
     */

    void addUser(UserPageQueryDTO userPageQueryDTO);

    /**
     * 修改用户
     * @param userPageQueryDTO
     */
    void updateUser(UserPageQueryDTO userPageQueryDTO);

    /**
     * 获取用户总数
     * @return
     */
    Integer userCount();

    /**
     * 查询某天的营业额
     * @param day
     * @return
     */
    StoreSalesVO queryStoreSalesInOneDay(Integer day);

    /**
     * 查询用户数量和注册人数在规定天数内
     * @param day
     * @return
     */
    UserCountVO queryUserCount(Integer day);
}
