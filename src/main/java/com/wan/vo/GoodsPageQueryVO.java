package com.wan.vo;

import com.wan.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsPageQueryVO implements Serializable {
    // 商品id
    private Long id;
    // 商店id
    private Long storeId;
    // 商品名
    private String goodsName;
    // 表明商品的上架状态 0下架 1上架
    private Integer status;
    // 分类id
    private Long categoryId;
    // 分类名称
    private String categoryName;
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
    // 店
    private Store store;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;
}
