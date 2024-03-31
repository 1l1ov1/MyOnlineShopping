package com.wan.controller;

import com.wan.dto.UserPageQueryDTO;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@Api("管理员相关接口")
@Slf4j
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    // 用户
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
    public Result<String> deleteUsers(@RequestParam List<Long> ids) {
        log.info("删除用户：{}", ids);
        managerService.deleteBatch(ids);
        return Result.success("删除成功");
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    public Result<String> addUser(@RequestBody UserPageQueryDTO userPageQueryDTO) {
        log.info("添加用户：{}", userPageQueryDTO);
        managerService.addUser(userPageQueryDTO);
        return Result.success("添加成功");
    }

    @PutMapping("/updateUser")
    @ApiOperation("修改用户信息")
    public Result<String> updateUser(@RequestBody UserPageQueryDTO userPageQueryDTO) {
        log.info("修改用户：{}", userPageQueryDTO);
        managerService.updateUser(userPageQueryDTO);
        return Result.success("修改成功");
    }

    @GetMapping("/userCount")
    @ApiOperation("查询用户人数")
    public Result<Integer> userCount() {
        log.info("查询用户人数");
        Integer count = managerService.userCount();
        return Result.success(count);
    }
}
