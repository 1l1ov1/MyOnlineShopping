package com.wan.server;

import com.wan.entity.Store;

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
}
