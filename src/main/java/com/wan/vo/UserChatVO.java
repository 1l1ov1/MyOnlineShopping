package com.wan.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChatVO {
    private Long id;
    private Long sendId;
    private Long receiveId;
    private String content;
    private LocalDateTime createTime;
    private Integer status;
    private Integer receiveStatus;

    private String avatar;
    private String logo;
    private String username;
    private String storeName;
}
