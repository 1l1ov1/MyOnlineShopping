package com.wan.controller;

import com.wan.constant.RedisConstant;
import com.wan.dto.ReportPageQueryDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.ReportService;
import com.wan.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@Api("举报")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/page")
    @ApiOperation("举报分页")
    public Result<PageResult> pageQueryReport(@RequestBody ReportPageQueryDTO reportPageQueryDTO) {
        log.info("举报分页查询 {}", reportPageQueryDTO);
        PageResult pageResult = reportService.pageQueryReport(reportPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量删除举报")
    public Result deleteReport(@RequestParam List<Long> ids) {
        log.info("批量删除举报 {}", ids);
        reportService.batchDeleteReport(ids);
        RedisUtils.clearRedisCacheByPattern(redisTemplate,
                RedisConstant.STORE_COMMENT + "*");
        return Result.success("删除成功");
    }
}
