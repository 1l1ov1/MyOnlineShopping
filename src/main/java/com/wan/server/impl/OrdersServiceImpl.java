package com.wan.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.MessageConstant;
import com.wan.dto.OrdersPageQueryDTO;
import com.wan.entity.Orders;
import com.wan.exception.OrdersException;
import com.wan.mapper.OrdersMapper;
import com.wan.result.PageResult;
import com.wan.server.OrdersService;
import com.wan.vo.OrdersPageQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 订单分页查询
     *
     * @param ordersPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        Page<OrdersPageQueryVO> page = ordersMapper.pageQuery(ordersPageQueryDTO);

        return PageResult.builder()
                .total(page.getTotal())
                .data(page.getResult())
                .build();
    }

    /**
     * 批量删除订单
     * @param ids
     */
    @Override
    @Transactional
    public void batchDeleteOrders(List<Long> ids) {
        // 检查传入的ID列表是否为空
        if (ids == null || ids.isEmpty() ) {
            throw new OrdersException(MessageConstant.ORDERS_NUMBER_IS_NOT_EXIST);
        }
        // 查找订单，确保所有ID都存在
        List<Orders> ordersList = ordersMapper.findOrdersById(ids);
        if (ordersList == null || ordersList.size() != ids.size()) {
            throw new OrdersException(MessageConstant.ORDERS_NUMBER_IS_NOT_EXIST);
        }
        // 批量删除订单
        ordersMapper.batchDeleteOrders(ids);
    }

    @Override
    public void updateOrders(Orders orders) {
        if (orders.getId() == null) {
            throw new OrdersException(MessageConstant.ORDERS_NUMBER_IS_NOT_EXIST);
        }

        if (orders.getStatus() == null || orders.getStatus() > 3 || orders.getStatus() < 1) {
            throw new OrdersException(MessageConstant.ORDERS_STATUS_IS_WRONG);
        }

        ordersMapper.update(orders);
    }

    /**
     * 添加订单
     *
     * @param orders
     */
    @Override
    public void addOrder(Orders orders) {

    }


}
