package com.wan.controller;

import com.wan.dto.FavoriteDTO;
import com.wan.result.Result;
import com.wan.service.FavoriteService;
import com.wan.vo.FavoriteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api("收藏夹接口")
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add/{target}")
    @ApiOperation("添加收藏夹")
    public Result<String> addFavorite(@RequestBody List<FavoriteDTO> favoriteDTOList, @PathVariable String target) {
        log.info("添加收藏夹 {}, {}", favoriteDTOList, target);
        favoriteService.batchSave(favoriteDTOList, target);
        return Result.success("添加成功");
    }

    @GetMapping("/query/{userId}")
    @ApiOperation("根据用户id查询对应的收藏夹")
    public Result<FavoriteVO> queryFavorite(@PathVariable Long userId) {
        log.info("查询用户的收藏夹 {}", userId);
        FavoriteVO favoriteVO = favoriteService.queryFavoriteByUserId(userId);
        return Result.success(favoriteVO);
    }

    @DeleteMapping("/delete/{target}")
    @ApiOperation("批量删除")
    public Result<String> batchDeleteFavorites(@RequestParam List<Long> ids, @PathVariable String target) {
        log.info("批量删除 {} {}", ids, target);
        favoriteService.batchDelete(ids, target);
        return Result.success("删除成功");
    }

    @GetMapping("/search/{id}")
    @ApiOperation("查询用户某种收藏")
    public Result<FavoriteVO> searchFavorite(@PathVariable Long id, @RequestParam String target) {
        log.info("查询用户某种收藏 {}， {}", id, target);
        FavoriteVO favoriteVO = favoriteService.queryFavorite(id, target);
        return Result.success(favoriteVO);
    }
}
