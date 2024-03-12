package com.wan.server;

import com.wan.dto.StorePageQueryDTO;
import com.wan.entity.Store;
import com.wan.result.PageResult;
import com.wan.vo.StorePageQueryVO;

import java.util.List;

public interface StoreService {
    /**
     * 添加商店
     * @param store
     */
    void addStore(Store store);

    /**
     * 根据id查询商店
     * @param id
     * @return
     */
    Store findStoreById(Long id);

    /**
     * 根据用户id查询商店
     * @param userId
     * @return
     */
    Store findStoreByUserId(Long userId);

    /**
     * 商店分页查询
     * @param storePageQueryDTO
     * @return
     */
    PageResult pageQuery(StorePageQueryDTO storePageQueryDTO);

    /**
     * 修改商店信息
     * @param storePageQueryDTO
     */
    void updateStore(StorePageQueryDTO storePageQueryDTO);

    /**
     * 开店或关店
     * @param status
     * @param id
     */
    void openOrClose(Integer status, Long id);

    /**
     * 批量删除商店
     * @param ids
     */
    void deleteBatchStore(List<Long> ids);

    /**
     * 得到商店详情
     * @param id
     * @return
     */
    StorePageQueryVO getStoreDetail(Long id);

    /**
     * 添加商店
     * @param storePageQueryDTO
     */
    void addStore(StorePageQueryDTO storePageQueryDTO);
}
