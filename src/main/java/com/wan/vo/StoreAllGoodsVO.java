package com.wan.vo;

import com.wan.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreAllGoodsVO implements Serializable {
    // 商店id
    private Long id;
    // 商店的商品
    private List<Goods> goodsList;
    // 商店的名称
    private String storeName;
    // 商店的状态
    private Integer status;
}
