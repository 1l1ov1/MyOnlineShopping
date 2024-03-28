package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long storeId;
    // private String goodsName;
    // private String storeName;
    // private String coverPic;
    // private String logo;
    private Long cartId;
    private Goods goods;
    private Store store;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
