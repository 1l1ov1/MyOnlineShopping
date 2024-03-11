package com.wan.controller;

import com.wan.dto.GoodsPageQueryDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.server.GoodsService;
import com.wan.vo.GoodsPageQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api("商品接口")
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/page")
    @ApiOperation("商品分页查询")
    public Result<PageResult> pageQuery(GoodsPageQueryDTO goodsPageQueryDTO) {
        log.info("商品分页查询{}", goodsPageQueryDTO);
        PageResult pageResult = goodsService.pageQuery(goodsPageQueryDTO);

        return Result.success(pageResult);
    }

}
