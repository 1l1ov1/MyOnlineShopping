package com.wan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAction {
    private Long id;

    private Long commentId;

    private Long userId;

    private Long goodsId;

    private Long storeId;
    // 1:点赞，-1：取消点赞    2:点踩， -2：取消点踩 3:全不选
    private Integer action;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
