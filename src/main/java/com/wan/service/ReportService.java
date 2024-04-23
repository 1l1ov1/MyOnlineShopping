package com.wan.service;

import com.wan.dto.ReportPageQueryDTO;
import com.wan.result.PageResult;

import java.util.List;

public interface ReportService {
    /**
     * 分页查询
     * @param reportPageQueryDTO
     * @return
     */
    PageResult pageQueryReport(ReportPageQueryDTO reportPageQueryDTO);

    /**
     * 批量删除举报
     * @param ids
     */
    void batchDeleteReport(List<Long> ids);
}
