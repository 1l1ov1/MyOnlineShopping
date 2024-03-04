package com.wan.server;

import com.wan.dto.UserPageQueryDTO;
import com.wan.result.PageResult;

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
    void patchDelete(List<Long> ids);
}
