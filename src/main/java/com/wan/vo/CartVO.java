package com.wan.vo;

import com.wan.entity.Cart;
import com.wan.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartVO implements Serializable {
    // 商店
    private Store store;
    //
    private List<Cart> cartList;
}
