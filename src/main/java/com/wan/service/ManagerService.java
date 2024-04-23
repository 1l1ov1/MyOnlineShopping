package com.wan.service;

import com.wan.dto.AwardUserDTO;
import com.wan.dto.ForbiddenOrBanDTO;
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
     * 禁言或封禁
     * @param forbiddenOrBanDTO
     */
    void forbidOrBan(ForbiddenOrBanDTO forbiddenOrBanDTO);

    /**
     * 管理员禁言或解除禁言
     * @param forbiddenOrBanDTO
     */
    void updateForbiddenWord(ForbiddenOrBanDTO forbiddenOrBanDTO);

    /**
     * 奖励用户
     * @param awardUserDTO
     */
    void awardUser(AwardUserDTO awardUserDTO);
}
