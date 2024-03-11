package com.wan.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.mapper.GoodsMapper;
import com.wan.result.PageResult;
import com.wan.server.GoodsService;
import com.wan.vo.GoodsPageQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public PageResult pageQuery(GoodsPageQueryDTO goodsPageQueryDTO) {
        // 开启分页
        PageHelper.startPage(goodsPageQueryDTO.getPage(), goodsPageQueryDTO.getPageSize());
        Page<GoodsPageQueryVO> pages = goodsMapper.pageQuery(goodsPageQueryDTO);

        return PageResult.builder()
                .total(pages.getTotal())
                .data(pages.getResult())
                .build();
    }
}
