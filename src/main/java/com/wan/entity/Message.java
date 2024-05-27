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
public class Message {
    private Long id;

    private String content;

    private Long sendId;

    private Long receiveId;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
