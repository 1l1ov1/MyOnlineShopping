package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.entity.Store;
import com.wan.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {
    /**
     * 添加商店
     * @param store
     */
    @AutoFill(OperationType.INSERT)
    void insertStore(Store store);

    /**
     * 根据商店买查询商店
     * @param storeName
     * @return
     */
    @Select("select * from store where store_name = #{storeName}")
    Store findStoreByStoreName(String storeName);

    /**
     * 根据id查询商店
     * @param id
     * @return
     */
    @Select("select * from store where id = #{id}")
    Store findStoreById(Long id);

    /**
     * 根据用户id查询商店
     * @param userId
     * @return
     */
    @Select("select * from store where user_id = #{userId}")
    Store findStoreByUserId(Long userId);

    /**
     * 批量删除商店
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 修改商店信息
     * @param store
     */
    @AutoFill(OperationType.UPDATE)
    void update(Store store);
}
