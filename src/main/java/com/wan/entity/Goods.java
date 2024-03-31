package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goods {
    // 商品id
    private Long id;
    // 关联商店的id 表明是哪家店的商品
    private Long storeId;
    // 表明商品的上架状态 0下架 1上架
    private Integer status;
    // 分类id
    private Long categoryId;
    // 商品名称
    private String goodsName;
    // 商品的价格
    private Double price;
    // 商品的总量
    private Long total;
    // 商品的折扣 [0-1]之间
    private Double discount;
    // 商品描述
    private String description;
    // 商品的封面
    private String coverPic;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;
    /**
     * 减少库存数量。
     * 该方法通过传入的数字减少当前库存的数量。注意，该方法没有返回值。
     *
     * @param number 需要减少的库存数量。是一个整数。
     */
    public void decreaseStock(Integer number) {
        // 设置新的库存数量为当前数量减去减少的数量
        setTotal(getTotal() - number);
    }
}
