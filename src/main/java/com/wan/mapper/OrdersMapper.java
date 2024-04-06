package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.OrdersPageQueryDTO;
import com.wan.entity.Orders;
import com.wan.enumeration.OperationType;
import com.wan.vo.OrdersPageQueryVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrdersMapper {

    /**
     * 批量添加订单
     *
     * @param ordersList
     */
    @AutoFill(OperationType.INSERT)
    void batchInsertOrder(List<Orders> ordersList);

    /**
     * 单独插入
     *
     * @param orders
     */
    @AutoFill(OperationType.INSERT)
    void insertOrders(Orders orders);

    /**
     * 分页查询
     *
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
     *
     * @param ids
     */
    void batchDeleteOrders(List<Long> ids);

    /**
     * 修改订单
     *
     * @param orders
     */
    @AutoFill(OperationType.UPDATE)
    void update(Orders orders);

    /**
     * 查询某种状态的所有订单
     *
     * @param status
     * @return
     */
    @Select("select * from orders where status = #{status}")
    List<Orders> queryOneTypeOrders(Integer status);

    /**
     * 批量修改订单
     *
     * @param ordersList
     */
    @AutoFill(OperationType.UPDATE)
    void batchUpdate(List<Orders> ordersList);

    /**
     * 查询订单
     * @param id
     * @return
     */
    @Select("select * from  orders where id = #{id}")
    Orders findOrders(Long id);


}
