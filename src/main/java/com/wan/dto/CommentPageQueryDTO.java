package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论查询
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageQueryDTO {

    private Long storeId;

    private Long goodsId;

    /**
     * 好评 1，差评 2
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;
}
