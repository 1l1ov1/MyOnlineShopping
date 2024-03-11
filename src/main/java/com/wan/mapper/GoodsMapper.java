package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.vo.GoodsPageQueryVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {

    Page<GoodsPageQueryVO> pageQuery(GoodsPageQueryDTO goodsPageQueryDTO);
}
