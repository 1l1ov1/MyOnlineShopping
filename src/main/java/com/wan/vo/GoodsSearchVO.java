package com.wan.vo;

import com.wan.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsSearchVO {
    // 商品列表
    private List<Goods> goodsList;
}
