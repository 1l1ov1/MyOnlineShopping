package com.wan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChatDTO {
    private Long sendId;
    private Long receiveId;
    private Integer type;
}
