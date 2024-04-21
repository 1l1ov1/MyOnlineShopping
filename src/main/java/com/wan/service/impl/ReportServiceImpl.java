package com.wan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.dto.ReportPageQueryDTO;
import com.wan.mapper.ReportMapper;
import com.wan.result.PageResult;
import com.wan.service.ReportService;
import com.wan.vo.ReportPageQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public PageResult pageQueryReport(ReportPageQueryDTO reportPageQueryDTO) {
        PageHelper.startPage(reportPageQueryDTO.getPage(), reportPageQueryDTO.getPageSize());

        Page<ReportPageQueryVO> page = reportMapper.pageQuery(reportPageQueryDTO);

        return PageResult.builder()
                .total(page.getTotal())
                .data(page.getResult())
                .build();
    }
}
