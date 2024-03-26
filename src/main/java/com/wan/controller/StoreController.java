package com.wan.controller;

import com.wan.dto.GoodsPageQueryDTO;
import com.wan.dto.StorePageQueryDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.server.StoreService;
import com.wan.vo.StoreAllGoodsVO;
import com.wan.vo.StorePageQueryVO;
import com.wan.vo.StoreSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("商店相关接口")
@Slf4j
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/page")
    @ApiOperation("商店分页查询")
    public Result<PageResult> pageQuery(StorePageQueryDTO storePageQueryDTO) {
        log.info("商店分页查询{}", storePageQueryDTO);
        PageResult pageResult = storeService.pageQuery(storePageQueryDTO);

        return Result.success(pageResult);
    }

    @PutMapping("/updateStore")
    @ApiOperation("修改商店信息")
    public Result<String> updateStore(@RequestBody StorePageQueryDTO storePageQueryDTO) {
        log.info("修改用户：{}", storePageQueryDTO);
        storeService.updateStore(storePageQueryDTO);
        return Result.success("修改成功");
    }


    @GetMapping("/getStoreDetail/{id}")
    @ApiOperation("获取商店信息")
    public Result<StorePageQueryVO> getDetail(@PathVariable Long id) {
        log.info("获取商店信息 {}", id);
        StorePageQueryVO storePageQueryVO = storeService.getStoreDetail(id);
        return Result.success(storePageQueryVO);
    }

    @PostMapping("/addStore")
    @ApiOperation("添加商店信息")
    public Result<String> addStore(@RequestBody StorePageQueryDTO storePageQueryDTO) {
        log.info("添加商店信息 {}", storePageQueryDTO);
        storeService.addStore(storePageQueryDTO);
        return Result.success("添加成功");
    }

    @DeleteMapping("/deleteStore")
    @ApiOperation("批量删除商店")
    public Result<String> deleteStore(@RequestParam List<Long> ids) {
        log.info("删除商店：{}", ids);
        storeService.deleteBatchStore(ids);
        return Result.success("删除成功");
    }

    @PostMapping("/openOrClose/{status}")
    @ApiOperation("开店和关店")
    public Result<String> openOrClose(@PathVariable Integer status, Long id) {
        log.info("开店和关店{}, {}", status, id);
        storeService.openOrClose(status, id);
        return Result.success("修改成功");
    }

    @GetMapping("/getGoods/{id}")
    @ApiOperation("得到商店的商品")
    public Result<StoreAllGoodsVO> getStoreAllGoods(@PathVariable Long id) {
        log.info("得到商店的商品 {}", id);
        StoreAllGoodsVO storeAllGoodsVO = storeService.getStoreAllGoods(id);
        return Result.success(storeAllGoodsVO);
    }

    @GetMapping("/query/{storeName}")
    @ApiOperation("搜索商店")
    public Result<StoreSearchVO> queryStores(@PathVariable String storeName) {
        log.info("搜索商店 {}", storeName);
        StoreSearchVO storeSearchVO = storeService.searchStores(storeName);
        return Result.success(storeSearchVO);
    }
}
