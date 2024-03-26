package com.wan.server;

import com.wan.dto.CategoryDTO;
import com.wan.result.PageResult;
import com.wan.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    /**
     * 分页查询
     * @param categoryDTO
     * @return
     */
    PageResult pageQuery(CategoryDTO categoryDTO);

    /**
     * 插入分类
     * @param categoryDTO
     */
    void addCategory(CategoryDTO categoryDTO);

    /**
     * 修改分页
     * @param categoryDTO
     */
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Long> ids);

    /**
     * 得到详情
     * @param id
     * @return
     */
    CategoryVO getDetail(Long id);
}
