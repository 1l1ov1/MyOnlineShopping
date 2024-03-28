package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long storeId;
    private Long cartId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
