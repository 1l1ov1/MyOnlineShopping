package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.entity.Goods;
import com.wan.enumeration.OperationType;
import com.wan.vo.GoodsPageQueryVO;
import com.wan.vo.GoodsSearchVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper {

    /**
     * 商品分页查询
     *
     * @param goodsPageQueryDTO
     * @return
     */
    Page<GoodsPageQueryVO> pageQuery(GoodsPageQueryDTO goodsPageQueryDTO);

    /**
     * 添加商品
     *
     * @param goods
     */
    @AutoFill(OperationType.INSERT)
    void insertGoods(Goods goods);

    /**
     * 批量删除
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据id查找商品
     *
     * @param id
     * @return
     */
    @Select("select * from goods where id = #{id}")
    Goods findGoodsById(Long id);

    /**
     * 根据id集合查询商品
     *
     * @param ids
     * @return
     */
    List<Goods> findGoodsByIds(List<Long> ids);

    /**
     * 修改商品信息
     *
     * @param goods
     */
    @AutoFill(OperationType.UPDATE)
    void update(Goods goods);

    /**
     * 搜索上架商品
     *
     * @param storeId
     * @return
     */
    List<Goods> findShelvesGoodsByStoreId(Long storeId);

    /**
     * 搜索上架商品
     *
     * @param goodsName
     * @return
     */
    List<Goods> searchGoods(String goodsName);

    /**
     * 根据分类id查询上架商品
     *
     * @param categoryId
     * @return
     */
    @Select("select * from goods where category_id = #{categoryId} and status = 1")
    List<Goods> findGoodsByCategoryIdAndShelves(Long categoryId);
}
