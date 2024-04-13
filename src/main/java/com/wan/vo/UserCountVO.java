package com.wan.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 查询用户数量和注册用户数量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCountVO implements Serializable {
    /**
     * 每个阶段的用户总数
     */
    private List<Integer> userCountList;
    /**
     * 注册用户数量
     */
    private List<Integer> registerUserCountList;

    /**
     * 开始时间
     */
    private LocalDate start;
    /**
     * 结束时间
     */
    private LocalDate end;
}
