package com.wan.controller;

import com.wan.constant.RedisConstant;
import com.wan.dto.ApplyDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.ApplyService;
import com.wan.utils.RedisUtils;
import com.wan.vo.ApplyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api("申请的接口")
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQuery(@RequestBody ApplyDTO applyDTO) {
        log.info("申请的分页查询 {}", applyDTO);

        PageResult pageResult = applyService.pageQuery(applyDTO);

        return Result.success(pageResult);
    }

    @GetMapping("/update/{status}")
    @ApiOperation("修改申请状态")
    public Result<String> updateApplyStatus(@PathVariable Integer status, Long id) {
        log.info("修改申请状态 {} {}", status, id);

        ApplyDTO applyDTO = ApplyDTO.builder()
                .id(id)
                .status(status)
                .build();
        applyService.update(applyDTO);

        return Result.success("修改成功");
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量删除")
    public Result<String> batchDeleteApply(@RequestParam List<Long> ids) {
        log.info("批量删除 {}", ids);
        applyService.batchDeleteApply(ids);

        return Result.success("删除成功");
    }

    @GetMapping("/getApplyDetail/{id}")
    @ApiOperation("回显申请信息")
    public Result<ApplyVO> getApplyDetail(@PathVariable Long id) {
        log.info("回显申请信息 {}", id);
        ApplyVO applyVO = applyService.getDetail(id);
        return Result.success(applyVO);
    }

    @PutMapping("/update")
    @ApiOperation("修改申请")
    public Result<String> updateApply(@RequestBody ApplyDTO applyDTO) {
        log.info("修改申请， {}", applyDTO);

        applyService.update(applyDTO);

        return Result.success();
    }

    private String getCacheKey(Integer applyStatus) {
        if (applyStatus == null) {
            return RedisConstant.ADMINISTRATOR_APPLY_PAGE;
        }

        switch (applyStatus) {
            case 0:
                return RedisConstant.ADMINISTRATOR_UNDER_REVIEW_APPLY_PAGE;
            case 1:
                return RedisConstant.ADMINISTRATOR_APPROVED_APPLY_PAGE;
            case 2:
                return RedisConstant.ADMINISTRATOR_REVIEW_REJECTION_APPLY_PAGE;
            default:
                // 对于未知的申请状态抛出异常
                throw new IllegalArgumentException("未知的申请类型: " + applyStatus);
        }
    }
}
