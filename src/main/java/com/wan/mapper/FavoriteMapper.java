package com.wan.mapper;

import com.wan.annotation.AutoFill;
import com.wan.dto.FavoriteDTO;
import com.wan.entity.Favorite;
import com.wan.enumeration.FavoriteType;
import com.wan.enumeration.OperationType;
import com.wan.vo.FavoriteVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    /**
     * 批量添加
     * @param favoriteDTOList
     */
    @AutoFill(OperationType.INSERT)
    void batchSave(List<FavoriteDTO> favoriteDTOList);

    /**
     * 查询该用户的所有收藏夹
     * @param userId
     * @return
     */
    List<Favorite> queryFavoriteByUserId(Long userId);

    /**
     * 根据favoriteType 来查询ids中的用户对应的收藏
     * @param ids
     * @param userId
     * @param target
     * @return
     */
    List<Favorite> queryOneTypeFavorite(List<Long> ids, Long userId, String target);

    /**
     * 批量删除
     * @param ids
     * @param userId
     * @param target
     */
    void batchDeleteFavorite(List<Long> ids, Long userId, String target);
}
