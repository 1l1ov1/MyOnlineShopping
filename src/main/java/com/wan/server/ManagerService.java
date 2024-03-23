package com.wan.server;

import com.wan.annotation.AutoFill;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.dto.StorePageQueryDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.User;
import com.wan.enumeration.OperationType;
import com.wan.result.PageResult;
import com.wan.vo.StorePageQueryVO;

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

}
