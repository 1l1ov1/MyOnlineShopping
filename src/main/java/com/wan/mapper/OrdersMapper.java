package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.OrdersPageQueryDTO;
import com.wan.entity.Orders;
import com.wan.enumeration.OperationType;
import com.wan.vo.OrdersPageQueryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper {

    /**
     * 批量添加订单
     * @param ordersList
     */
    @AutoFill(OperationType.INSERT)
    void batchInsertOrder(List<Orders> ordersList);

    /**
     * 分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    Page<OrdersPageQueryVO> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     */

    List<Orders> findOrdersById(List<Long> ids);

    /**
     * 批量删除订单
     * @param ids
     */
    void batchDeleteOrders(List<Long> ids);

    /**
     * 修改订单
     * @param orders
     */
    void update(Orders orders);
}
