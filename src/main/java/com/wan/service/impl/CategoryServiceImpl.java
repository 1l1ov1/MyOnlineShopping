package com.wan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.CategoryConstant;
import com.wan.constant.MessageConstant;
import com.wan.dto.CategoryDTO;
import com.wan.entity.Category;
import com.wan.entity.Goods;
import com.wan.exception.ApplyException;
import com.wan.exception.CategoryException;
import com.wan.mapper.CategoryMapper;
import com.wan.mapper.GoodsMapper;
import com.wan.result.PageResult;
import com.wan.service.CategoryService;
import com.wan.utils.CheckObjectFieldUtils;
import com.wan.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 分页查询
     *
     * @param categoryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CategoryDTO categoryDTO) {
        // PageHelper.startPage(categoryDTO.getPage(), categoryDTO.getPageSize());
        // Page<CategoryVO> page = categoryMapper.pageQuery(categoryDTO);
        // return PageResult.builder()
        //         .total(page.getTotal())
        //         .data(page.getResult())
        //         .build();

        return categoryDTO.executePageQuery(categoryMapper::pageQuery, categoryDTO);
    }

    /**
     * 添加分类
     *
     * @param categoryDTO
     */
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        // 如果都符合要求
        if (checkCategoryStatus(categoryDTO.getCategoryStatus())
                && checkCategoryName(categoryDTO.getCategoryName(), categoryDTO.getId())) {
            // 插入
            categoryMapper.insert(categoryDTO);
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        try {
            if (CheckObjectFieldUtils.areAllNonExcludedFieldsNotNull(categoryDTO,
                    categoryDTO::getPage, categoryDTO::getPageSize, categoryDTO::getSort)) {
                // 如果都不空
                Category category = categoryMapper.findCategory(categoryDTO.getId());
                if (category == null) {
                    throw new CategoryException(MessageConstant.CATEGORY_IS_NOT_EXIST);
                }
                // 如果名称和状态都符合要求
                if (checkCategoryName(categoryDTO.getCategoryName(), categoryDTO.getId())
                        && checkCategoryStatus(categoryDTO.getCategoryStatus())) {
                    // 如果分类为禁止状态的话
                    if (checkHasGoods(categoryDTO)) {
                        // 如果没有，就修改
                        categoryMapper.updateCategory(categoryDTO);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        // 检查传入的ID列表是否为空
        if (ids == null || ids.isEmpty()) {
            throw new ApplyException(MessageConstant.APPLY_IS_NOT_EXIST);
        }
        // 查找分类，确保所有ID都存在
        List<Category> categoryList = categoryMapper.findCategoryByIds(ids);
        // 如果分类为空或者数量不对
        if (categoryList == null || categoryList.size() != ids.size()) {
            throw new CategoryException(MessageConstant.CATEGORY_IS_NOT_EXIST);
        }

        // 如果数量都对应
        for (Category category : categoryList) {
            // 就去查询商品该分类是否全部下架，只有全部下架才能删除
            List<Goods> goodsList = goodsMapper.findGoodsByCategoryIdAndShelves(category.getId());
            // 如果有该分类的上架商品存在
            if (goodsList != null && goodsList.size() > 0) {
                throw new CategoryException(MessageConstant.HAVING_SHELVE_GOODS_IN_THE_CATEGORY);
            }
        }
        // 删除
        categoryMapper.batchDelete(ids);
    }

    @Override
    public CategoryVO getDetail(Long id) {
        if (id == null) {
            throw new CategoryException(MessageConstant.CATEGORY_IS_NOT_EXIST);
        }
        Category category = categoryMapper.findCategory(id);
        if (category == null) {
            throw new CategoryException(MessageConstant.CATEGORY_IS_NOT_EXIST);
        }
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }

    /**
     * 修改分类状态
     *
     * @param categoryDTO
     */
    @Override
    public void updateCategoryStatus(CategoryDTO categoryDTO) {
        try {
            // 校验指定的字段是否为空
            if (!CheckObjectFieldUtils.areAllIncludedFieldsIsNull(categoryDTO,
                    categoryDTO::getCategoryStatus, categoryDTO::getId)) {
                // 如果不空，就根据id查询
                Category category = categoryMapper.findCategory(categoryDTO.getId());
                if (category == null) {
                    throw new CategoryException(MessageConstant.CATEGORY_IS_NOT_EXIST);
                }
                // 检查状态
                if (checkCategoryStatus(categoryDTO.getCategoryStatus())) {
                    // 如果合法
                    // 如果分类为禁止状态的话
                    if (checkHasGoods(categoryDTO)) {
                        // 如果没有，就修改
                        categoryMapper.updateCategory(categoryDTO);
                    }
                }
            } else {
                throw new CategoryException(MessageConstant.CATEGORY_IS_NOT_EXIST);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkHasGoods(CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryStatus().equals(CategoryConstant.DISABLED)) {
            // 就去查询商品该分类是否全部下架，只有全部下架才能该为禁止
            List<Goods> goodsList = goodsMapper.findGoodsByCategoryIdAndShelves(categoryDTO.getId());
            // 如果有该分类的上架商品存在
            if (goodsList != null && goodsList.size() > 0) {
                throw new CategoryException(MessageConstant.HAVING_SHELVE_GOODS_IN_THE_CATEGORY);
            }
        }
        return true;
    }

    private boolean checkCategoryStatus(Integer categoryStatus) {
        // 如果都不空
        if (categoryStatus == null || categoryStatus < 0 || categoryStatus > 1) {
            throw new CategoryException(MessageConstant.CATEGORY_STATUS_IS_WRONG);
        }
        return true;
    }

    private boolean checkCategoryName(String categoryName, Long id) {
        if (categoryName == null) {
            throw new CategoryException(MessageConstant.CATEGORY_NAME_IS_EMPTY);
        }

        Category category = categoryMapper.findCategoryByName(categoryName);
        // 如果分类名称存在并且不是修改，即为添加状态
        if (category != null && id == null) {
            throw new CategoryException(MessageConstant.CATEGORY_IS_EXIST);
        }

        return true;
    }
}
