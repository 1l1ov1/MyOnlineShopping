package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.WithdrawRecordPageQueryDTO;
import com.wan.entity.WithdrawRecord;
import com.wan.enumeration.OperationType;
import com.wan.vo.WithdrawRecordPageQueryVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WithdrawRecordMapper {
    /**
     * 分页查询
     * @param withdrawRecordPageQueryDTO
     * @return
     */
    Page<WithdrawRecordPageQueryVO> queryWithdrawRecord(WithdrawRecordPageQueryDTO withdrawRecordPageQueryDTO);

    /**
     * 插入
     * @param withdrawRecord
     */
    @AutoFill(OperationType.INSERT)
    void insertWithdrawRecord(WithdrawRecord withdrawRecord);
}
