package com.wan.server;

import com.wan.dto.GoodsPageQueryDTO;
import com.wan.entity.Goods;
import com.wan.result.PageResult;
import com.wan.vo.GoodsPageQueryVO;
import com.wan.vo.GoodsSearchVO;

import java.util.List;

public interface GoodsService {

    /**
     * 分页查询
     * @param goodsPageQueryDTO
     * @return
     */
    PageResult pageQuery(GoodsPageQueryDTO goodsPageQueryDTO);

    /**
     * 商品添加
     * @param goodsPageQueryDTO
     */
    Long addGoods(GoodsPageQueryDTO goodsPageQueryDTO);

    /**
     * 商品的批量删除
     * @param ids
     */
    void batchDelete(List<Long> ids);

    /**
     * 获取商品信息
     * @param id
     * @return
     */
    GoodsPageQueryVO getGoodsInfo(Long id);

    /**
     * 修改商品信息
     * @param goodsPageQueryDTO
     */
    void updateGoods(GoodsPageQueryDTO goodsPageQueryDTO);

    /**
     * 查询所有上架商品
     * @return
     */
    List<Goods> queryAll();

    /**
     * 根据商品名搜索商品
     * @param goodsName
     * @return
     */
    GoodsSearchVO searchGoods(String goodsName);

    /**
     * 根据分类id查询商品
     * @param id
     * @return
     */
    GoodsSearchVO findGoods(Long id);
}
