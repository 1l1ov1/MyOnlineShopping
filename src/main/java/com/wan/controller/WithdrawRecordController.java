package com.wan.controller;

import com.wan.dto.WithdrawRecordPageQueryDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.WithdrawRecordService;
import com.wan.vo.WithdrawRecordPageQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/withdrawRecord")
@Api("提现记录")
@Slf4j
public class WithdrawRecordController {
    @Autowired
    private WithdrawRecordService withdrawRecordService;

    @PostMapping("/pageQuery")
    @ApiOperation("查询提现记录")
    public Result<PageResult> queryWithdrawRecord(@RequestBody WithdrawRecordPageQueryDTO withdrawRecordPageQueryDTO) {
        log.info("查询提现记录 {}", withdrawRecordPageQueryDTO);
        PageResult pageResult = withdrawRecordService.queryWithdrawRecord(withdrawRecordPageQueryDTO);
        return Result.success(pageResult);
    }
}
