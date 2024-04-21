package com.wan.vo;

import com.wan.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportPageQueryVO {
    private Long total;

    private List<ReportVO> reportList;
}
