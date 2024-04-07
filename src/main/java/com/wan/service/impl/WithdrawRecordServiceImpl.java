package com.wan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.dto.WithdrawRecordPageQueryDTO;
import com.wan.mapper.WithdrawRecordMapper;
import com.wan.result.PageResult;
import com.wan.service.WithdrawRecordService;
import com.wan.vo.WithdrawRecordPageQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawRecordServiceImpl implements WithdrawRecordService {

    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;

    /**
     * 分页查询
     *
     * @param withdrawRecordPageQueryDTO
     * @return
     */
    @Override
    public PageResult queryWithdrawRecord(WithdrawRecordPageQueryDTO withdrawRecordPageQueryDTO) {
        PageHelper.startPage(withdrawRecordPageQueryDTO.getPage(), withdrawRecordPageQueryDTO.getPageSize());

        Page<WithdrawRecordPageQueryVO> pages = withdrawRecordMapper.queryWithdrawRecord(withdrawRecordPageQueryDTO);

        return PageResult.builder()
                .total(pages.getTotal())
                .data(pages.getResult())
                .build();
    }
}
