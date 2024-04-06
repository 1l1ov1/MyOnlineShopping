package com.wan.controller;

import com.wan.result.Result;
import com.wan.service.StatisticService;
import com.wan.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/statistic")
@Slf4j
@Api("数据统计相关接口")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;


    @GetMapping("/queryStoreSales/{day}")
    @ApiOperation("查询规定天数的营业额")
    public Result<StoreSalesVO> queryStoreSales(@PathVariable Integer day) {
        log.info("查询规定天数的营业额 {}", day);
        StoreSalesVO storeSalesVO = statisticService.queryStoreSalesInOneDay(day);
        return Result.success(storeSalesVO);
    }

    @GetMapping("/queryOrdersUserCount/{day}")
    @ApiOperation("订单用户数量")
    public Result<OrdersUserCountVO> queryOrdersUserCount(@PathVariable Integer day) {
        log.info("查询用户数量 {}", day);
        OrdersUserCountVO ordersUserCountVO = statisticService.queryOrdersUserCount(day);
        return Result.success(ordersUserCountVO);
    }

    @GetMapping("/queryOrdersCount/{day}")
    @ApiOperation("查询商家订单数量")
    public Result<OrdersCountVO> queryOrdersCount(@PathVariable Integer day) {
        log.info("查询用户数量 {}", day);
        OrdersCountVO ordersCountVO = statisticService.queryOrdersCount(day);
        return Result.success(ordersCountVO);
    }


    @GetMapping("/queryUserCount/{day}")
    @ApiOperation("查询用户数量和在线用户数量")
    public Result<UserCountVO> queryUserCount(@PathVariable Integer day) {
        log.info("查询用户数量 {}", day);
        UserCountVO userCountVO = statisticService.queryUserCount(day);
        return Result.success(userCountVO);
    }

    @GetMapping("/top10/{day}")
    @ApiOperation("销量排名top10")
    public Result<SalesTop10VO> top10(@PathVariable Integer day) {
        log.info("销量排名top10:{} ", day);
        SalesTop10VO salesTop10VO = statisticService.querySalesTop10(day);
        return Result.success(salesTop10VO);
    }

    @GetMapping("/export")
    @ApiOperation("数据导出")
    public void export(HttpServletResponse response) {
        log.info("数据导出");
        statisticService.exportData(response);
    }
}
