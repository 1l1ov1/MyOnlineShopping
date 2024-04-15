package com.wan.controller;

import com.wan.constant.OrdersConstant;
import com.wan.constant.RedisConstant;
import com.wan.dto.OrdersPageQueryDTO;
import com.wan.entity.Orders;
import com.wan.result.PageResult;
import com.wan.result.Result;
import com.wan.service.OrdersService;
import com.wan.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@Api("订单相关接口")
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
        Long userId = ordersService.updateOrders(orders).getUserId();
        RedisUtils.clearRedisCache(redisTemplate,
                RedisConstant.USER_ORDERS + OrdersConstant.ALL_ORDERS + "-" + userId);
        return Result.success("修改成功");
    }


    /**
     * 根据订单状态获取对应的缓存键值。
     *
     * @param ordersStatus 订单状态，不同状态对应不同的缓存键。
     * @return 返回对应订单状态的缓存键字符串。
     * @throws IllegalArgumentException 当传入未知的订单状态时抛出。
     */
    private String getCacheKey(Integer ordersStatus) {
        if (ordersStatus == null) {
            return RedisConstant.ADMINISTRATOR_ALL_ORDERS_PAGE;
        }

        switch (ordersStatus) {
            case 1:
                // 获取未发货订单的缓存键
                return RedisConstant.ADMINISTRATOR_UNSHIPPED_ORDERS_PAGE;
            case 2:
                // 获取已发货订单的缓存键
                return RedisConstant.ADMINISTRATOR_SHIPPED_ORDERS_PAGE;
            case 3:
                // 获取退款订单的缓存键
                return RedisConstant.ADMINISTRATOR_REFUNDED_ORDERS_PAGE;
            case 4:
                // 获取用户签收订单的缓存键
                return RedisConstant.ADMINISTRATOR_USER_RECEIVE_PRODUCT_ORDERS_PAGE;
            case 5:
                // 获取成功订单的缓存键
                return RedisConstant.ADMINISTRATOR_SUCCESSFUL_ORDERS_PAGE;
            default:
                // 对于未知的订单状态抛出异常
                throw new IllegalArgumentException("未知的订单类型: " + ordersStatus);
        }
    }

}
