package com.wan.controller;

import com.wan.constant.RedisConstant;
import com.wan.dto.CategoryDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.CategoryService;
import com.wan.utils.RedisUtils;
import com.wan.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api("分类接口")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQuery(@RequestBody CategoryDTO categoryDTO) {
        log.info("分页查询 {}", categoryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/update")
    @ApiOperation("修改")
    public Result<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类 {}", categoryDTO);
        categoryService.updateCategory(categoryDTO);

        return Result.success("修改成功");
    }

    @PutMapping("/updateStatus")
    public Result<String> updateCategoryStatus(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类 {}", categoryDTO);
        categoryService.updateCategoryStatus(categoryDTO);

        return Result.success("修改成功");
    }


    @DeleteMapping("/delete")
    @ApiOperation("批量删除")
    public Result<String> batchDeleteCategory(@RequestParam List<Long> ids) {
        log.info("分类批量删除 {}", ids);
        categoryService.batchDelete(ids);

        return Result.success("删除成功");
    }

    @PostMapping("/add")
    @ApiOperation("添加分类")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("添加分类 {}", categoryDTO);
        categoryService.addCategory(categoryDTO);

        return Result.success("添加成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("得到分类详情")
    public Result<CategoryVO> getDetail(@PathVariable Long id) {
        log.info("得到分类详情 {}", id);
        CategoryVO categoryVO = categoryService.getDetail(id);
        return Result.success(categoryVO);
    }
}
