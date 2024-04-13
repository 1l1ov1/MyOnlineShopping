package com.wan.controller;

import com.wan.constant.RedisConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.entity.Goods;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.GoodsService;
import com.wan.service.StoreService;
import com.wan.utils.RedisUtils;
import com.wan.vo.GoodsPageQueryVO;
import com.wan.vo.GoodsSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Api("商品接口")
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/page")
    @ApiOperation("商品分页查询")
    public Result<PageResult> pageQuery(@RequestBody GoodsPageQueryDTO goodsPageQueryDTO) {
        log.info("商品分页查询{}", goodsPageQueryDTO);
        PageResult pageResult = goodsService.pageQuery(goodsPageQueryDTO);
        return Result.success(pageResult);
    }


    @GetMapping("/query")
    @ApiOperation("获取所有商品信息")
    public Result<List<Goods>> queryAll() {
        log.info("查询所有商品");
        List<Goods> goodsList = RedisUtils.redisStringGet(redisTemplate, RedisConstant.USER_ALL_GOODS_PAGE, List.class);
        if (goodsList != null) {
            return Result.success(goodsList);
        }

        goodsList = goodsService.queryAll();
        RedisUtils.redisStringSet(redisTemplate, RedisConstant.USER_ALL_GOODS_PAGE, goodsList);
        return Result.success(goodsList);
    }

    @PostMapping("/add")
    @ApiOperation("商品添加")
    public Result<Long> addGoods(@RequestBody GoodsPageQueryDTO goodsPageQueryDTO) {
        log.info("商品添加 {}", goodsPageQueryDTO);
        Long goodsId = goodsService.addGoods(goodsPageQueryDTO);
        RedisUtils.clearRedisCache(redisTemplate, RedisConstant.USER_ALL_GOODS_PAGE,
                RedisConstant.STORE_GOODS_PAGE + goodsPageQueryDTO.getStoreId());
        return Result.success(goodsId, "添加成功");
    }

    @DeleteMapping("/delete")
    @ApiOperation("商品的批量删除")
    public Result<String> deleteGoods(@RequestParam List<Long> ids) {
        log.info("商品的批量删除 {}", ids);
        if (ids == null || ids.isEmpty()) {
            return Result.error("请选择要删除的商品");
        }
        Long userId = ThreadBaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("用户未登录，无法执行删除操作");
        }

        Long storeId = storeService.findStoreByUserId(userId).getId();
        goodsService.batchDelete(ids);
        RedisUtils.clearRedisCache(redisTemplate, RedisConstant.USER_ALL_GOODS_PAGE,
                RedisConstant.STORE_GOODS_PAGE + storeId);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取商品信息")
    public Result<GoodsPageQueryVO> getGoodsDetail(@PathVariable Long id) {
        log.info("获取商品的信息 {}", id);
        GoodsPageQueryVO goodsPageQueryVO = goodsService.getGoodsInfo(id);
        return Result.success(goodsPageQueryVO);
    }

    @PutMapping("/update")
    @ApiOperation("修改商品")
    public Result<String> updateGoods(@RequestBody GoodsPageQueryDTO goodsPageQueryDTO) {
        log.info("修改商品的信息 {}", goodsPageQueryDTO);
        goodsService.updateGoods(goodsPageQueryDTO);
        RedisUtils.clearRedisCache(redisTemplate, RedisConstant.USER_ALL_GOODS_PAGE,
                RedisConstant.STORE_GOODS_PAGE + goodsPageQueryDTO.getStoreId());
        return Result.success("修改成功");
    }

    @GetMapping("/query/{goodsName}")
    @ApiOperation("搜索商品")
    public Result<GoodsSearchVO> searchGoods(@PathVariable String goodsName) {
        log.info("搜索商品 {}", goodsName);
        GoodsSearchVO goodsSearchVO = goodsService.searchGoods(goodsName);
        return Result.success(goodsSearchVO);
    }

    @GetMapping("/category/{id}")
    @ApiOperation("根据分类id查询上架商品")
    public Result<GoodsSearchVO> queryGoods(@PathVariable Long id) {
        log.info("根据分类id查询 {}", id);
        GoodsSearchVO goodsSearchVO = goodsService.findGoods(id);
        return Result.success(goodsSearchVO);
    }


}
