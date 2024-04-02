package com.wan.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 查询用户数量和注册用户数量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCountVO {
    /**
     * 用户数量
     */
    private Integer userCount;
    /**
     * 注册用户数量
     */
    private List<Integer> registerUserCount;

    /**
     * 开始时间
     */
    private LocalDate start;
    /**
     * 结束时间
     */
    private LocalDate end;
}
