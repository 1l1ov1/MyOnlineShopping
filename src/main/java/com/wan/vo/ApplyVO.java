package com.wan.vo;

import com.wan.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyVO implements Serializable {
    // id
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 状态
    private Integer status;
    // 地址
    private Address address;
    // 商店名称
    private String storeName;
    // 拒绝理由
    private String reason;

    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;

    private Integer page;
    private Integer pageSize;

    private Integer sort; // 1 升序 0 降序
}
