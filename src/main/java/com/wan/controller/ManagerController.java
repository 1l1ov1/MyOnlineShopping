package com.wan.controller;

import com.wan.dto.StorePageQueryDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.User;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.server.ManagerService;
import com.wan.vo.StorePageQueryVO;
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
    public Result<String> deleteUsers(@RequestParam  List<Long> ids) {
        log.info("删除用户：{}", ids);
        managerService.deleteBatch(ids);
        return Result.success("删除成功");
    }
    @PostMapping("/add")
    @ApiOperation("添加用户")
    public Result<String> addUser(@RequestBody User user) {
        log.info("添加用户：{}", user);
        managerService.addUser(user);
        return Result.success("添加成功");
    }

    @PutMapping("/updateUser")
    @ApiOperation("修改用户信息")
    public Result<String> updateUser(@RequestBody UserPageQueryDTO userPageQueryDTO) {
        log.info("修改用户：{}", userPageQueryDTO);
        managerService.updateUser(userPageQueryDTO);
        return Result.success("修改成功");
    }
    @GetMapping("/storePageQuery")
    @ApiOperation("商店分页查询")
    public Result<PageResult> pageQuery(StorePageQueryDTO storePageQueryDTO) {
        log.info("商店分页查询，参数为：{}", storePageQueryDTO);
        // 分页查询
        PageResult pageResult = managerService.pageQuery(storePageQueryDTO);

        return Result.success(pageResult);
    }

    @PutMapping("/updateStore")
    @ApiOperation("修改商店信息")
    public Result<String> updateStore(@RequestBody StorePageQueryDTO storePageQueryDTO) {
        log.info("修改用户：{}", storePageQueryDTO);
        managerService.updateStore(storePageQueryDTO);
        return Result.success("修改成功");
    }

    @GetMapping("/getStoreDetail/{id}")
    @ApiOperation("获取商店信息")
    public Result<StorePageQueryVO> getDetail(@PathVariable Long id) {
        log.info("获取商店信息 {}", id);
        StorePageQueryVO storePageQueryVO = managerService.getStoreDetail(id);
        return Result.success(storePageQueryVO);
    }
    @PostMapping("/addStore")
    @ApiOperation("添加商店信息")
    public Result<String> addStore(@RequestBody StorePageQueryDTO storePageQueryDTO) {
        log.info("添加商店信息 {}", storePageQueryDTO);
        managerService.addStore(storePageQueryDTO);
        return Result.success("添加成功");
    }

    @DeleteMapping("/deleteStore")
    @ApiOperation("批量删除商店")
    public Result<String> deleteStore(@RequestParam List<Long> ids) {
        log.info("删除商店：{}", ids);
        managerService.deleteBatchStore(ids);
        return Result.success("删除成功");
    }

    @PostMapping("/openOrClose/{status}")
    @ApiOperation("开店和关店")
    public Result<String> openOrClose(@PathVariable Integer status, Long id) {
            log.info("开店和关店{}, {}", status, id);
            managerService.openOrClose(status, id);
            return Result.success("修改成功");
    }
}
