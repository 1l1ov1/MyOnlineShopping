package com.wan.dto;

import com.wan.entity.Address;
import com.wan.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateStoreDTO {
    private Long id;
    private Long userId;
    private String username;
    private String password;
    private Address address;
    private Store store;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
