package com.wan.server;

import com.wan.annotation.AutoFill;
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
     * @param user
     */

    void addUser(User user);

    /**
     * 修改用户
     * @param userPageQueryDTO
     */
    void updateUser(UserPageQueryDTO userPageQueryDTO);

    /**
     * 商店分页
     * @param storePageQueryDTO
     * @return
     */
    PageResult pageQuery(StorePageQueryDTO storePageQueryDTO);

    /**
     * 管理员修改商店
     * @param storePageQueryDTO
     */
    void updateStore(StorePageQueryDTO storePageQueryDTO);

    /**
     * 获取某一个商店的详情
     * @param id
     * @return
     */
    StorePageQueryVO getStoreDetail(Long id);

    /**
     * 添加商店
     * @param storePageQueryDTO
     */
    void addStore(StorePageQueryDTO storePageQueryDTO);

    /**
     * 批量删除商店
     * @param ids
     */
    void deleteBatchStore(List<Long> ids);

    /**
     * 开店或关店
     * @param status
     * @param id
     */
    void openOrClose(Integer status, Long id);
}
