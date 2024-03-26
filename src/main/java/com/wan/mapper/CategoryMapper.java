package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.CategoryDTO;
import com.wan.entity.Category;
import com.wan.enumeration.OperationType;
import com.wan.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 分页查询
     *
     * @param categoryDTO
     * @return
     */
    Page<CategoryVO> pageQuery(CategoryDTO categoryDTO);

    /**
     * 修改分类
     *
     * @param categoryDTO
     */
    @AutoFill(OperationType.INSERT)
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(List<Long> ids);

    /**
     * 批量查找
     *
     * @param ids
     * @return
     */
    List<Category> findCategoryByIds(List<Long> ids);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from category where id = #{id}")
    Category findCategory(Long id);

    /**
     * 根据分类名称查询
     * @param categoryName
     * @return
     */
    @Select("select * from category where category_name = #{categoryName}")
    Category findCategoryByName(String categoryName);

    /**
     * 插入
     * @param categoryDTO
     */
    @AutoFill(OperationType.INSERT)
    void insert(CategoryDTO categoryDTO);
}
