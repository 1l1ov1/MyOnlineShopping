package com.wan.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.MessageConstant;
import com.wan.constant.StoreConstant;
import com.wan.constant.UserConstant;
import com.wan.dto.StorePageQueryDTO;
import com.wan.entity.Goods;
import com.wan.entity.Store;
import com.wan.entity.User;
import com.wan.exception.AccountNotFountException;
import com.wan.exception.StatusException;
import com.wan.exception.StoreException;
import com.wan.mapper.GoodsMapper;
import com.wan.mapper.StoreMapper;
import com.wan.mapper.UserMapper;
import com.wan.result.PageResult;
import com.wan.server.StoreService;
import com.wan.vo.GoodsPageQueryVO;
import com.wan.vo.StorePageQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    /**
     * 添加商店
     *
     * @param store
     */
    @Override
    public void addStore(Store store) {
        if (store == null) {
            throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
        }
        // 添加商店
        storeMapper.insertStore(store);
    }

    /**
     * 根据id查询商店
     *
     * @param id
     * @return
     */
    @Override
    public Store findStoreById(Long id) {
        return storeMapper.findStoreById(id);
    }

    /**
     * 根据用户id查找
     * @param userId
     * @return
     */
    @Override
    public Store findStoreByUserId(Long userId) {
        return storeMapper.findStoreByUserId(userId);
    }

    /**
     * 分页
     *
     * @param storePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(StorePageQueryDTO storePageQueryDTO) {
        // 开启分页
        PageHelper.startPage(storePageQueryDTO.getPage(), storePageQueryDTO.getPageSize());
        Page<StorePageQueryVO> pages = storeMapper.pageQuery(storePageQueryDTO);

        return PageResult.builder()
                .total(pages.getTotal())
                .data(pages.getResult())
                .build();
    }

    /**
     * 修改商店信息
     *
     * @param storePageQueryDTO
     */
    @Override
    public void updateStore(StorePageQueryDTO storePageQueryDTO) {
        if (isValid(storePageQueryDTO)) {
            String storeName = storePageQueryDTO.getStoreName();
            Store store = storeMapper.findStoreByStoreName(storeName);
            if (store != null) {
                throw new StoreException(MessageConstant.STORE_EXIST);
            }
            store = new Store();
            BeanUtils.copyProperties(storePageQueryDTO, store);
            storeMapper.update(store);
        }
    }

    /**
     * 添加商店
     *
     * @param storePageQueryDTO
     */
    @Override
    public void addStore(StorePageQueryDTO storePageQueryDTO) {
        String storeName = storePageQueryDTO.getStoreName();
        String username = storePageQueryDTO.getUsername();
        // 先根据用户名查询该用户
        User user = userMapper.getByUsername(username);
        // 如果用户不存在就抛出异常
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 如果用户身份为管理员
        if (UserConstant.MANAGER == user.getStatus()) {
            throw new StoreException(MessageConstant.MANAGE_IS_NOT_ALLOWED_TO_OPEN_STORE);
        }
        // 如果用户身份为普通用户
        if (UserConstant.COMMON_USER == user.getStatus()) {
            throw new StoreException(MessageConstant.COMMON_USER_IS_NOT_ALLOWED_TO_OPEN_STORE);
        }
        // 根据店名查询商店
        Store store = storeMapper.findStoreByStoreName(storeName);
        // 如果店名已经存在
        if (store != null) {
            throw new StoreException(MessageConstant.STORE_EXIST);
        }
        // 根据userId查询商店
        store = storeMapper.findStoreByUserId(user.getId());
        // 如果用户已经有商店
        if (store != null) {
            throw new StoreException(MessageConstant.ONLY_HAS_ONE_STORE);
        }

        if ("".equals(storeName) || storeName == null) {
            throw new StoreException(MessageConstant.STORE_NAME_LENGTH_VALID);
        }
        int len = storeName.length();

        if (len < 2 || len > 15) {
            throw new StoreException(MessageConstant.STORE_NAME_LENGTH_VALID);
        }
        // 创建商店
        store = Store.builder()
                .storeName(storeName)
                .userId(user.getId())
                .status(StoreConstant.OPEN)
                .build();

        storeMapper.insertStore(store);

    }

    /**
     * 批量删除商店
     *
     * @param ids
     */
    @Override
    public void deleteBatchStore(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
        }

        for (Long id : ids) {
            // 根据id查找到商品
            Store store = storeMapper.findStoreById(id);
            List<Goods> goodsList = goodsMapper.findSHELVESGoodsByStoreId(store.getId());
            // 如果该商店有上架商品
            if (goodsList != null && goodsList.size() > 0) {
               throw new StoreException(MessageConstant.THE_STORE_HAS_ITEMS_ON_THE_SHELVES);
            }
        }
        // 删除的数据
        storeMapper.deleteByIds(ids);
    }

    /**
     * 开店或关店
     * @param status
     * @param id
     */
    @Override
    public void openOrClose(Integer status, Long id) {
        // 如果状态不存在
        if (!status.equals(StoreConstant.OPEN) && !status.equals(StoreConstant.CLOSE)) {
            throw new StatusException(MessageConstant.THE_STATUS_IS_NOT_EXIST);
        }

        Store store = Store.builder()
                .status(status)
                .id(id)
                .build();
        storeMapper.update(store);

    }

    /**
     * 得到商店详情
     * @param id
     * @return
     */
    @Override
    public StorePageQueryVO getStoreDetail(Long id) {
        Store store = storeMapper.findStoreById(id);
        if (store == null) {
            throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
        }

        return storeMapper.getStoreDetail(id);
    }

    /**
     * 防止后端接口暴露，通过Postman来访问接口 进行数据校验
     * @param storePageQueryDTO
     * @return
     */
    private Boolean isValid(StorePageQueryDTO storePageQueryDTO) {
        String storeName = storePageQueryDTO.getStoreName();
        Integer status = storePageQueryDTO.getStatus();

        if (storeName == null || "".equals(storeName)) {
            throw new StoreException(MessageConstant.STORE_NAME_IS_EMPTY);
        } else if (status == null) {
            throw new StoreException(MessageConstant.STORE_STATUS_IS_EMPTY);
        } else if (status < 0 || status > 1) {
            throw new StoreException(MessageConstant.STORE_STATUS_IS_VALID);
        }

        return true;
    }


}
