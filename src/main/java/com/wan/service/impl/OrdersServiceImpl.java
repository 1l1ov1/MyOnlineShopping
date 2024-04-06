package com.wan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.MessageConstant;
import com.wan.constant.OrdersConstant;
import com.wan.dto.OrdersPageQueryDTO;
import com.wan.entity.Orders;
import com.wan.entity.User;
import com.wan.exception.OrdersException;
import com.wan.mapper.OrdersMapper;
import com.wan.mapper.UserMapper;
import com.wan.result.PageResult;
import com.wan.service.OrdersService;
import com.wan.vo.OrdersPageQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;

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
     *
     * @param ids
     */
    @Override
    @Transactional
    public void batchDeleteOrders(List<Long> ids) {
        // 检查传入的ID列表是否为空
        if (ids == null || ids.isEmpty()) {
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
    @Transactional
    public void updateOrders(Orders orders) {
        Long id = orders.getId();
        Integer status = orders.getStatus();
        // 根据id查询该订单
        Orders findOrders = ordersMapper.findOrders(id);
        // 如果订单不存在
        if (findOrders == null) {
            throw new OrdersException(MessageConstant.ORDERS_NUMBER_IS_NOT_EXIST);
        }

        if (status == null || status > OrdersConstant.SUCCESSFUL_ORDER || status < OrdersConstant.UNSHIPPED_ORDER) {
            throw new OrdersException(MessageConstant.ORDERS_STATUS_IS_WRONG);
        }
        // 如果订单存在
        // 如果要修改的订单的状态是退款状态
        if (status.equals(OrdersConstant.REFUNDED_ORDER)) {
            // 退款
            User user = refund(findOrders);
            // 修改用户的金额
            userMapper.update(user);
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




    /**
     * 用户退款
     *
     * @param refundOrders
     * @return
     */
    private User refund(Orders refundOrders) {
        // 得到该订单的产生者
        Long userId = refundOrders.getUserId();
        // 得到该用户
        User user = userMapper.getById(userId);
        // 得到总价
        BigDecimal totalPrice = refundOrders.getTotalPrice();
        // 加回去
        user.setMoney(user.getMoney().add(totalPrice));
        return user;
    }
}
