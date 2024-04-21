package com.wan.service;

import com.wan.dto.ReportPageQueryDTO;
import com.wan.result.PageResult;

public interface ReportService {
    /**
     * 分页查询
     * @param reportPageQueryDTO
     * @return
     */
    PageResult pageQueryReport(ReportPageQueryDTO reportPageQueryDTO);
}
