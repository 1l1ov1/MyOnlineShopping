package com.wan.controller;

import com.wan.dto.ReportPageQueryDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@Api("举报")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/page")
    @ApiOperation("举报分页")
    public Result<PageResult> pageQueryReport(@RequestBody ReportPageQueryDTO reportPageQueryDTO) {
        log.info("举报分页查询 {}", reportPageQueryDTO);
        PageResult pageResult = reportService.pageQueryReport(reportPageQueryDTO);
        return Result.success(pageResult);
    }
}
