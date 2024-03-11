package com.wan.server;

import com.wan.dto.GoodsPageQueryDTO;
import com.wan.result.PageResult;
import com.wan.vo.GoodsPageQueryVO;

public interface GoodsService {

    /**
     * 分页查询
     * @param goodsPageQueryDTO
     * @return
     */
    PageResult pageQuery(GoodsPageQueryDTO goodsPageQueryDTO);
}
