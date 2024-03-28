package com.wan.vo;

import com.wan.entity.Favorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteVO {
    // 收藏夹
    private List<Favorite> favoriteList;
}
