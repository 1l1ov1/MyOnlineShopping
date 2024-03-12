package com.wan.server;

import com.wan.dto.GoodsPageQueryDTO;
import com.wan.entity.Goods;
import com.wan.result.PageResult;
import com.wan.vo.GoodsPageQueryVO;

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
    void addGoods(GoodsPageQueryDTO goodsPageQueryDTO);

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
}
