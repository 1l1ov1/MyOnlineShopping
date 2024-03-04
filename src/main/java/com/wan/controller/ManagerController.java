package com.wan.controller;

import com.wan.dto.UserPageQueryDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.server.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/manager")
@Api("管理员相关接口")
@Slf4j
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @GetMapping("/page")
    @ApiOperation("用户分页查询")
    public Result<PageResult> pageQuery(UserPageQueryDTO userPageQueryDTO) {
        log.info("用户分页查询，参数为：{}", userPageQueryDTO);
        // 分页查询
        PageResult pageResult = managerService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{accountStatus}")
    @ApiOperation("启用和禁用用户账号")
    public Result<String> startOrStop(@PathVariable Integer accountStatus, Long id) {
        log.info("启用禁用员工账号：{},{}", accountStatus, id);
        managerService.startOrStop(accountStatus, id);
        return Result.success("修改成功");
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量删除用户")
    public Result<String> patchDeleteUsers(@RequestParam  List<Long> ids) {
        log.info("删除用户：{}", ids);
        managerService.patchDelete(ids);
        return Result.success("删除成功");
    }
}