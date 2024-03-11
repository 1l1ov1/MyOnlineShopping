package com.wan.server.impl;

import com.wan.entity.Store;
import com.wan.mapper.StoreMapper;
import com.wan.server.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    /**
     * 添加商店
     * @param store
     */
    @Override
    public void addStore(Store store) {
        // 添加商店
        storeMapper.insertStore(store);
    }

    /**
     * 根据id查询商店
     * @param id
     * @return
     */
    @Override
    public Store findStoreById(Long id) {
        return storeMapper.findStoreById(id);
    }

    @Override
    public Store findStoreByUserId(Long userId) {
        return storeMapper.findStoreByUserId(userId);
    }
}
