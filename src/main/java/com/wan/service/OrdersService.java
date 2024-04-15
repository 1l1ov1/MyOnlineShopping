package com.wan.service;

import com.wan.dto.OrdersPageQueryDTO;
import com.wan.entity.Orders;
import com.wan.result.PageResult;

import java.util.List;

public interface OrdersService {


    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 批量删除订单
     * @param ids
     */
    void batchDeleteOrders(List<Long> ids);

    /**
     * 修改订单
     * @param orders
     */
    Orders updateOrders(Orders orders);


}
