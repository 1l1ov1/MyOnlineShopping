package com.wan.server;

import com.wan.dto.ApplyDTO;
import com.wan.entity.Apply;
import com.wan.result.PageResult;
import com.wan.vo.ApplyVO;

import java.util.List;

public interface ApplyService {
    /**
     * 添加申请
     * @param applyDTO
     */
    void addApply(ApplyDTO applyDTO);

    /**
     * 检查申请格式
     * @param applyDTO
     */
    void checkApply(ApplyDTO applyDTO);

    /**
     * 查询该用户是否审核通过
     * @param username
     * @return
     */
    Apply findApply(String username);

    PageResult pageQuery(ApplyDTO applyDTO);

    /**
     * 修改申请
     * @param applyDTO
     */
    void update(ApplyDTO applyDTO);

    /**
     * 批量删除
     * @param ids
     */
    void batchDeleteApply(List<Long> ids);

    /**
     * 回显申请信息
     * @param id
     * @return
     */
    ApplyVO getDetail(Long id);
}
