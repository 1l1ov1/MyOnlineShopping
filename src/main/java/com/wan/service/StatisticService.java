package com.wan.service;

import com.wan.vo.*;

import javax.servlet.http.HttpServletResponse;

public interface StatisticService {

    /**
     * 商家查询自己的营业额
     * @param day
     * @return
     */
    StoreSalesVO queryStoreSalesInOneDay(Integer day);

    /**
     * 查询用户订单数量
     * @param day
     * @return
     */
    OrdersUserCountVO queryOrdersUserCount(Integer day);

    /**
     * 查询订单数量和订单完成率
     * @param day
     * @return
     */
    OrdersCountVO queryOrdersCount(Integer day);

    /**
     * 查询用户数量
     * @param day
     * @return
     */
    UserCountVO queryUserCount(Integer day);

    /**
     * 查询销量前10的商品
     * @param day
     * @return
     */
    SalesTop10VO querySalesTop10(Integer day);

    /**
     * 数据导出
     * @param response
     */
    void exportData(HttpServletResponse response);
}
