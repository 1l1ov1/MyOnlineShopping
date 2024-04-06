package com.wan.service.impl;

import com.wan.constant.MessageConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.FavoriteDTO;
import com.wan.entity.Favorite;
import com.wan.entity.Goods;
import com.wan.entity.Store;
import com.wan.entity.User;
import com.wan.enumeration.FavoriteType;
import com.wan.exception.AccountNotFountException;
import com.wan.exception.FavoriteException;
import com.wan.exception.GoodsException;
import com.wan.exception.StoreException;
import com.wan.mapper.*;
import com.wan.service.FavoriteService;
import com.wan.vo.FavoriteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {


    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private CartMapper cartMapper;

    /**
     * 批量添加收藏夹
     *
     * @param favoriteDTOList
     * @param target
     */
    @Override
    @Transactional
    public void batchSave(List<FavoriteDTO> favoriteDTOList, String target) {
        // 如果是商品
        if (FavoriteType.PRODUCT.getTarget().equals(target)) {
            batchSaveProduct(favoriteDTOList);
        } else if (FavoriteType.STORE.getTarget().equals(target)) {
            // 如果是添加商店
            batchSaveStore(favoriteDTOList);
        } else {
            // 处理未知目标类型的情况，例如抛出一个异常或记录一条错误日志
            throw new IllegalArgumentException("Unknown target type: " + target);
        }

    }

    private void batchSaveProduct(List<FavoriteDTO> favoriteDTOList) {
        if (favoriteDTOList == null || favoriteDTOList.size() == 0) {
            throw new FavoriteException(MessageConstant.GOODS_IS_NOT_EXIST);
        }

        // 遍历集合，如果userId或goodsId有一个为空，就应该报错
        for (FavoriteDTO favoriteDTO : favoriteDTOList) {
            if (favoriteDTO.getUserId() == null) {
                throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
            }

            if (favoriteDTO.getGoodsId() == null) {
                throw new FavoriteException(MessageConstant.GOODS_IS_NOT_EXIST);
            }
        }
        // 查询userId是否存在该用户，因为都是一个用户添加
        User user = userMapper.getById(favoriteDTOList.get(0).getUserId());
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 说明全部存在，就去查找该商品是否存在
        // 得到商品id列表
        List<Long> goodsIdList = favoriteDTOList.stream()
                .map(FavoriteDTO::getGoodsId).collect(Collectors.toList());

        // 得到商品
        List<Goods> goodsList = goodsMapper.findGoodsByIds(goodsIdList);
        if (goodsList == null || goodsIdList.size() != goodsList.size()) {
            throw new GoodsException(MessageConstant.GOODS_IS_NOT_EXIST);
        }
        // 如果全部存在就添加
        favoriteMapper.batchSave(favoriteDTOList);
        // 添加完后还需要将购物车对应的删除
        List<Long> cartIdList = new ArrayList<>();
        for (FavoriteDTO favoriteDTO : favoriteDTOList) {
            cartIdList.add(favoriteDTO.getCartId());
        }
        cartMapper.batchDeleteByIds(cartIdList);
    }

    // 添加商店
    private void batchSaveStore(List<FavoriteDTO> favoriteDTOList) {
        if (favoriteDTOList == null || favoriteDTOList.size() == 0) {
            throw new FavoriteException(MessageConstant.STORE_IS_NOT_EXIST);
        }

        // 遍历集合，如果userId或goodsId有一个为空，就应该报错
        for (FavoriteDTO favoriteDTO : favoriteDTOList) {
            if (favoriteDTO.getUserId() == null) {
                throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
            }

            if (favoriteDTO.getStoreId() == null) {
                throw new FavoriteException(MessageConstant.STORE_IS_NOT_EXIST);
            }
        }

        // 查询userId是否存在该用户，因为都是一个用户添加
        User user = userMapper.getById(favoriteDTOList.get(0).getUserId());
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 说明全部存在，就去查找该商品是否存在
        // 得到商店id列表
        List<Long> storeIdList = favoriteDTOList.stream()
                .map(FavoriteDTO::getStoreId).collect(Collectors.toList());

        // 得到商品
        List<Store> storeList = storeMapper.findStoreByIds(storeIdList);
        if (storeList == null || storeList.size() != storeIdList.size()) {
            throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
        }
        // 如果全部存在就添加
        favoriteMapper.batchSave(favoriteDTOList);
    }


    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void batchDelete(List<Long> ids, String target) {
        // 如果ids为空
        if (ids == null || ids.isEmpty()) {
            throw new FavoriteException(MessageConstant.THIS_FAVORITE_IS_NOT_EXIST);
        }
        // 将target转成枚举
        FavoriteType favoriteType = FavoriteType.valueOf(target.toUpperCase());
        // 去删除
        batchDeleteFavorites(ids, favoriteType);
    }

    private void batchDeleteFavorites(List<Long> ids, FavoriteType favoriteType) {
        // 得到用户id
        Long userId = ThreadBaseContext.getCurrentId();
        ThreadBaseContext.removeCurrentId();
        // 得到对应的收藏
        List<Favorite> favorites = favoriteMapper.queryOneTypeFavorite(ids, userId, favoriteType.getTarget());
        // 如果收藏为空
        if (favorites == null || favorites.size() != ids.size()) {
            String errorMessage = favoriteType == FavoriteType.PRODUCT ? MessageConstant.GOODS_IS_NOT_EXIST : MessageConstant.STORE_IS_NOT_EXIST;
            throw new FavoriteException(errorMessage);
        }

        // 删除
        favoriteMapper.batchDeleteFavorite(ids, userId, favoriteType.getTarget());
    }


    /**
     * 查询用户的所有收藏夹
     *
     * @param userId
     * @return
     */
    @Override
    public FavoriteVO queryFavoriteByUserId(Long userId) {
        // 查询该id的用户
        User user = userMapper.getById(userId);
        // 如果用户为空
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 如果用户不空
        List<Favorite> favorites = favoriteMapper.queryFavoriteByUserId(userId);
        return FavoriteVO.builder()
                .favoriteList(favorites)
                .build();
    }

    @Override
    public FavoriteVO queryFavorite(Long id, String target) {
        FavoriteType favoriteType = FavoriteType.valueOf(target.toUpperCase());
        if (FavoriteType.PRODUCT == favoriteType || FavoriteType.STORE == favoriteType) {
            return queryFavoriteByType(id, favoriteType);
        } else {
            // 处理未知目标类型的情况，例如抛出一个异常或记录一条错误日志
            throw new IllegalArgumentException("Unknown target type: " + target);
        }
    }

    private FavoriteVO queryFavoriteByType(Long id, FavoriteType favoriteType) {
        Long userId = ThreadBaseContext.getCurrentId();
        ThreadBaseContext.removeCurrentId();
        List<Long> targetIds = new ArrayList<>(); // 根据需要可能改为 goodsId 或 storeId
        targetIds.add(id);
        List<Favorite> favorites = favoriteMapper.queryOneTypeFavorite(targetIds, userId, favoriteType.getTarget());

        return FavoriteVO.builder()
                .favoriteList(favorites)
                .build();
    }
}
