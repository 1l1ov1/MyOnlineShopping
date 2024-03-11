package com.wan.mapper;

import com.github.pagehelper.Page;
import com.wan.annotation.AutoFill;
import com.wan.dto.StorePageQueryDTO;
import com.wan.dto.UserPageQueryDTO;
import com.wan.enumeration.OperationType;
import com.wan.vo.StorePageQueryVO;
import com.wan.vo.UserPageQueryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerMapper {
    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    // Page<UserPageQueryVO> pageQuery(UserPageQueryDTO userPageQueryDTO);
    List<UserPageQueryVO> pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 修改用户
     * @param userPageQueryDTO
     */
    @AutoFill(OperationType.UPDATE)
    void update(UserPageQueryDTO userPageQueryDTO);
    /**
     * 分页查询
     * @param storePageQueryDTO
     * @return
     */
    Page<StorePageQueryVO> storePageQuery(StorePageQueryDTO storePageQueryDTO);

    // /**
    //  * 修改商店
    //  * @param storePageQueryDTO
    //  */
    // @AutoFill(OperationType.UPDATE)
    // void updateStore(StorePageQueryDTO storePageQueryDTO);

    /**
     * 查询商店详情
     * @param id
     * @return
     */
    StorePageQueryVO getStoreDetail(Long id);
}
