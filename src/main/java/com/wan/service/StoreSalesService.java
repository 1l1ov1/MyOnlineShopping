package com.wan.service;

import com.wan.entity.StoreSales;

/**
* @author 123
* @description 针对表【store_sales(营业额数据表)】的数据库操作Service
* @createDate 2024-03-30 22:09:11
*/
public interface StoreSalesService {

    /**
     * 插入营业额
     * @param storeSales
     */
    void insertStoreSales(StoreSales storeSales);

}
