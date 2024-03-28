package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.entity.Cart;
import com.wan.entity.CartWithStore;
import com.wan.enumeration.OperationType;
import com.wan.vo.CartVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartMapper {
    /**
     * 添加购物车
     *
     * @param cart
     */
    @AutoFill(OperationType.INSERT)
    @Insert("insert into cart(user_id, goods_id, goods_name, number, goods_price, discount," +
            "total_price, update_time, create_time) " +
            "VALUES (#{userId}, #{goodsId}, #{goodsName}, #{number},#{goodsPrice}, #{discount}, " +
            "#{totalPrice}, #{updateTime}, #{createTime})")
    void insert(Cart cart);

    /**
     * 根据用户id查询
     *
     * @param id
     * @return
     */
    List<CartWithStore> findUserCartsById(Long id);

    /**
     * 根据购物车id查找
     *
     * @param id
     * @return
     */
    @Select("select * from cart where id = #{id}")
    Cart queryByCartId(Long id);

    /**
     * 删除购物车
     *
     * @param id
     */
    @Delete("delete from cart where id = #{id}")
    void deleteCart(Long id);

    /**
     * 批量删除购物车
     *
     * @param cartList
     */
    void batchDeleteCarts(List<Cart> cartList);

    /**
     * 批量删除
     * @param ids
     */
    void batchDeleteByIds(List<Long> ids);
    /**
     * 查询某个用户的某个商品
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("select * from cart where user_id = #{userId} and goods_id = #{goodsId}")
    Cart findGoodsByGoodsIdAndUserId(Long userId, Long goodsId);

    /**
     * 修改购物车
     * @param cart
     */
    @AutoFill(OperationType.UPDATE)
    void updateCart(Cart cart);
}
