package com.wan.service;


import com.wan.dto.WithdrawRecordPageQueryDTO;
import com.wan.result.PageResult;

public interface WithdrawRecordService {

    /**
     * 分页查询
     * @param withdrawRecordPageQueryDTO
     * @return
     */
    PageResult queryWithdrawRecord(WithdrawRecordPageQueryDTO withdrawRecordPageQueryDTO);
}
