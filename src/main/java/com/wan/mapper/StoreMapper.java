package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.StorePageQueryDTO;
import com.wan.entity.Store;
import com.wan.enumeration.OperationType;
import com.wan.vo.GoodsPageQueryVO;
import com.wan.vo.StoreAllGoodsVO;
import com.wan.vo.StorePageQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {
    /**
     * 添加商店
     *
     * @param store
     */
    @AutoFill(OperationType.INSERT)
    void insertStore(Store store);

    /**
     * 根据商店买查询商店
     *
     * @param storeName
     * @return
     */
    @Select("select * from store where store_name = #{storeName}")
    Store findStoreByStoreName(String storeName);

    /**
     * 根据id查询商店
     *
     * @param id
     * @return
     */
    /*@Select("select * from store where id = #{id}")*/
    Store findStoreById(Long id);

    /**
     * 批量查询商店
     * @param ids
     * @return
     */
    List<Store> findStoreByIds(List<Long> ids);
    /**
     * 根据用户id查询商店
     *
     * @param userId
     * @return
     */
    @Select("select * from store where user_id = #{userId}")
    Store findStoreByUserId(Long userId);

    /**
     * 批量删除商店
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 修改商店信息
     *
     * @param store
     */
    @AutoFill(OperationType.UPDATE)
    void update(Store store);

    /**
     * 商品分页查询
     *
     * @param storePageQueryDTO
     * @return
     */
    Page<StorePageQueryVO> pageQuery(StorePageQueryDTO storePageQueryDTO);

    /**
     * 得到商店详情
     *
     * @param id
     * @return
     */
    StorePageQueryVO getStoreDetail(Long id);

    /**
     * 得到商店的所有商品
     *
     * @param id
     * @return
     */
    StoreAllGoodsVO getAllGoods(Long id);

    @Select("select * from store where status = 1 and store_name like concat('%', #{storeName}, '%')")
    List<Store> queryStores(String storeName);
}
