package com.wan.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.MessageConstant;
import com.wan.constant.OrdersConstant;
import com.wan.constant.PayConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.dto.GoodsPurchaseDTO;
import com.wan.entity.*;
import com.wan.exception.CategoryException;
import com.wan.exception.GoodsException;
import com.wan.exception.OrdersException;
import com.wan.exception.StoreException;
import com.wan.mapper.*;
import com.wan.result.PageResult;
import com.wan.server.GoodsService;
import com.wan.vo.GoodsPageQueryVO;
import com.wan.vo.GoodsSearchVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 商品分页查询
     *
     * @param goodsPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(GoodsPageQueryDTO goodsPageQueryDTO) {
        // 开启分页
        PageHelper.startPage(goodsPageQueryDTO.getPage(), goodsPageQueryDTO.getPageSize());
        Page<GoodsPageQueryVO> pages = goodsMapper.pageQuery(goodsPageQueryDTO);

        return PageResult.builder()
                .total(pages.getTotal())
                .data(pages.getResult())
                .build();
    }

    /**
     * 商品添加
     *
     * @param goodsPageQueryDTO
     */
    @Override
    public Long addGoods(GoodsPageQueryDTO goodsPageQueryDTO) {
        // 如果合法
        if (isValid(goodsPageQueryDTO)) {
            // 根据商店名查询商店
            String storeName = goodsPageQueryDTO.getStore().getStoreName();
            Store store = storeMapper.findStoreByStoreName(storeName);
            // 如果商店不存在
            if (store == null) {
                throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
            }

            // 查找分类
            Long categoryId = goodsPageQueryDTO.getCategoryId();
            if (categoryId == null) {
                throw new CategoryException(MessageConstant.CATEGORY_IS_NOT_EXIST);
            }
            Long storeId = store.getId();
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsPageQueryDTO, goods);
            goods.setStoreId(storeId);
            goodsMapper.insertGoods(goods);
            // 返回该商品的id
            return goods.getId();
        } else {
            return null;
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void batchDelete(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new GoodsException(MessageConstant.PLEASE_CHOOSE_ONE_MORE_DATA_TO_DELETE);
        }

        goodsMapper.delete(ids);
    }

    /**
     * 获取商品信息
     *
     * @param id
     * @return
     */
    @Override
    public GoodsPageQueryVO getGoodsInfo(Long id) {
        Goods goods = goodsMapper.findGoodsById(id);
        if (goods == null) {
            throw new GoodsException(MessageConstant.GOODS_IS_NOT_EXIST);
        }
        Store store = storeMapper.findStoreById(goods.getStoreId());

        GoodsPageQueryVO goodsPageQueryVO = new GoodsPageQueryVO();
        BeanUtils.copyProperties(goods, goodsPageQueryVO);
        goodsPageQueryVO.setStore(store);
        return goodsPageQueryVO;
    }

    /**
     * 修改商品
     *
     * @param goodsPageQueryDTO
     */
    @Override
    public void updateGoods(GoodsPageQueryDTO goodsPageQueryDTO) {
        if (isValid(goodsPageQueryDTO)) {
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsPageQueryDTO, goods);
            goodsMapper.update(goods);
        }
    }

    @Override
    public List<Goods> queryAll() {
        return goodsMapper.findShelvesGoodsByStoreId(null);
    }


    @Override
    public GoodsSearchVO searchGoods(String goodsName) {
        if (goodsName == null || "".equals(goodsName)) {
            throw new GoodsException(MessageConstant.GOODS_NAME_IS_NOT_ALLOWED_TO_BE_EMPTY);
        }
        List<Goods> goodsList = goodsMapper.searchGoods(goodsName);
        return GoodsSearchVO.builder()
                .goodsList(goodsList)
                .build();
    }


    @Override
    public GoodsSearchVO findGoods(Long id) {
        if (id == null) {
            throw new CategoryException(MessageConstant.CATEGORY_IS_NOT_EXIST);
        }
        // 查询分类的上架商品
        List<Goods> goodsList = goodsMapper.findGoodsByCategoryId(id);

        return GoodsSearchVO.builder()
                .goodsList(goodsList)
                .build();

    }

    /**
     * 购买商品
     *
     * @param goodsPurchaseDTO
     */
    @Override
    @Transactional
    public void buy(GoodsPurchaseDTO goodsPurchaseDTO) {
        Goods goods = goodsMapper.findGoodsById(goodsPurchaseDTO.getGoodsId());
        if (goods == null) {
            throw new GoodsException(MessageConstant.GOODS_IS_NOT_EXIST);
        }
        // 如果该商品存在
        Long total = goods.getTotal();
        Integer number = goodsPurchaseDTO.getNumber();
        // 如果购买数量大于剩余量
        if (total < number) {
            throw new GoodsException(MessageConstant.GOODS_TOTAL_IS_OUT_OF_VALID_RANGE);
        }
        Long userId = ThreadBaseContext.getCurrentId();

        BigDecimal totalPrice = goodsPurchaseDTO.getTotalPrice();
        Orders orders = Orders.builder()
                .goodsId(goodsPurchaseDTO.getGoodsId())
                .goodsName(goodsPurchaseDTO.getGoodsName())
                .number(goodsPurchaseDTO.getNumber())
                .pay(PayConstant.WALLET_PAYMENTS)
                .storeId(goodsPurchaseDTO.getStoreId())
                .userId(userId)
                .totalPrice(goodsPurchaseDTO.getTotalPrice())
                .status(OrdersConstant.UNSHIPPED_ORDER)
                .build();

        // 添加订单
        ordersMapper.insertOrders(orders);
        // 用户扣钱
        User user = userMapper.getById(userId);
        BigDecimal userMoney = user.getMoney();
        // 如果金额不足
        if (userMoney.compareTo(totalPrice) < 0) {
            throw new OrdersException(MessageConstant.THE_AMOUNT_IS_INSUFFICIENT);
        }

        // 如果金额足够
        user.setMoney(userMoney.subtract(totalPrice));
        userMapper.update(user);
    }

    /**
     * 防止后端接口暴露，通过Postman来访问接口 进行数据校验
     *
     * @param goodsPageQueryDTO
     * @return
     */
    private Boolean isValid(GoodsPageQueryDTO goodsPageQueryDTO) {
        String goodsName = goodsPageQueryDTO.getGoodsName();
        String description = goodsPageQueryDTO.getDescription();
        Double discount = goodsPageQueryDTO.getDiscount();
        Double price = goodsPageQueryDTO.getPrice();
        Integer status = goodsPageQueryDTO.getStatus();
        Long total = goodsPageQueryDTO.getTotal();
        if (goodsName == null || "".equals(goodsName)) {
            throw new GoodsException(MessageConstant.GOODS_NAME_IS_NOT_ALLOWED_TO_BE_EMPTY);
        } else if (description == null || "".equals(description)) {
            throw new GoodsException(MessageConstant.GOODS_DESCRIPTION_IS_NOT_ALLOWED_TO_BE_EMPTY);
        } else if (discount == null) {
            throw new GoodsException(MessageConstant.GOODS_DISCOUNT_IS_NOT_ALLOWED_TO_BE_EMPTY);
        } else if (discount > 1 || discount < 0) {
            throw new GoodsException(MessageConstant.GOODS_DISCOUNT_IS_OUT_OF_VALID_RANGE);
        } else if (price == null) {
            throw new GoodsException(MessageConstant.GOODS_PRICE_IS_NOT_ALLOWED_TO_BE_EMPTY);
        } else if (price < 1.00 || price > 99999.00) {
            throw new GoodsException(MessageConstant.GOODS_PRICE_IS_OUT_OF_VALID_RANGE);
        } else if (status == null) {
            throw new GoodsException(MessageConstant.GOODS_STATUS_IS_NOT_ALLOWED_TO_BE_EMPTY);
        } else if (status < 0 || status > 1) {
            throw new GoodsException(MessageConstant.GOODS_STATUS_IS_OUT_OF_VALID_RANGE);
        } else if (total == null) {
            throw new GoodsException(MessageConstant.GOODS_TOTAL_IS_NOT_ALLOWED_TO_BE_EMPTY);
        } else if (total < 0 || total > 999999) {
            throw new GoodsException(MessageConstant.GOODS_TOTAL_IS_OUT_OF_VALID_RANGE);
        }
        return true;
    }
}
