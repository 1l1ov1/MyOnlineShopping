package com.wan.controller;

import com.wan.dto.OrdersPageQueryDTO;
import com.wan.entity.Orders;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api("订单相关接口")
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/page")
    @ApiOperation("订单分页查询")
    public Result<PageResult> pageQuery(@RequestBody OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单分页查询, {}", ordersPageQueryDTO);
        PageResult pageResult = ordersService.pageQuery(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量删除")
    public Result<String> batchDeleteOrders(@RequestParam List<Long> ids) {
        log.info("批量删除 {}", ids);
        ordersService.batchDeleteOrders(ids);
        return Result.success("删除成功");
    }

    @GetMapping("/update/{status}")
    @ApiOperation("修改订单状态")
    public Result<String> updateStatus(@PathVariable Integer status, Long id) {
        log.info("修改订单状态, {}, {}", status, id);
        Orders orders = Orders.builder()
                .id(id)
                .status(status).build();
        ordersService.updateOrders(orders);
        return Result.success("修改成功");
    }


}
