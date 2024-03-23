package com.wan.server;

import com.wan.dto.CartSubmitDTO;
import com.wan.entity.Cart;
import com.wan.vo.UserCartVO;

import java.util.List;

public interface CartService {

    /**
     * 添加购物车
     * @param cart
     */
    void addCart(Cart cart);

    /**
     * 查询用户的购物车
     * @param id
     * @return
     */
    UserCartVO getDetail(Long id);

    /***
     * 删除用户购物车
     * @param ids
     */
    void deleteUserCart(List<Long> ids);

    /**
     * 购买
     * @param cartSubmitDTO
     */
    void buy(CartSubmitDTO cartSubmitDTO);
}
