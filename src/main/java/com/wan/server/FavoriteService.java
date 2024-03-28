package com.wan.server;

import com.wan.dto.FavoriteDTO;
import com.wan.vo.FavoriteVO;

import java.util.List;

public interface FavoriteService {
    /**
     * 批量添加收藏
     *
     * @param favoriteDTOList
     * @param target
     */
    void batchSave(List<FavoriteDTO> favoriteDTOList, String target);

    /**
     * 批量删除
     *
     * @param ids
     * @param target
     */
    void batchDelete(List<Long> ids, String target);

    /**
     * 根据用户id查询用户的所有收藏夹
     *
     * @param userId
     * @return
     */
    FavoriteVO queryFavoriteByUserId(Long userId);
}
