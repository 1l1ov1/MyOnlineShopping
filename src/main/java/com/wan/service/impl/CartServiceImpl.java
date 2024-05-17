package com.wan.service.impl;

import com.wan.constant.MessageConstant;
import com.wan.constant.OrdersConstant;
import com.wan.constant.PayConstant;
import com.wan.dto.CartSubmitDTO;
import com.wan.entity.*;
import com.wan.exception.AccountNotFountException;
import com.wan.exception.CartException;
import com.wan.exception.GoodsException;
import com.wan.mapper.*;
import com.wan.service.CartService;
import com.wan.utils.SnowFlakeUtil;
import com.wan.vo.CartVO;
import com.wan.vo.UserCartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 添加购物车
     *
     * @param cart
     */
    @Override
    @Transactional
    public void addCart(Cart cart) {
        if (isValid(cart)) {
            // 插入购物车
            Long userId = cart.getUserId();
            Long goodsId = cart.getGoodsId();
            Cart c = cartMapper.findGoodsByGoodsIdAndUserId(userId, goodsId);
            if (c != null) {
                // 如果存在了
                c.setNumber(cart.getNumber() + c.getNumber());
                // 并修改合并
                cartMapper.updateCart(c);
            } else {
                // 如果不存在就添加
                cartMapper.insert(cart);
            }
        }
    }

    /**
     * 得到购物车详情
     *
     * @param id
     * @return
     */
    @Override
    public UserCartVO getDetail(Long id) {
        User user = userMapper.getById(id);
        if (user == null) {
            throw new AccountNotFountException(MessageConstant.USER_IS_NOT_EXIST);
        }
        // 根据用户id查询CartVO
        List<CartWithStore> cartWithStoreList = cartMapper.findUserCartsById(id);
        // 然后对store_id进行分组
        HashMap<Long, CartVO> map = new HashMap<>();
        // 遍历
        for (CartWithStore cartWithStore : cartWithStoreList) {
            // 创建或获取CartVO对象
            // 如果map集合里面没有就创建一个
            CartVO cartVO = map.getOrDefault(cartWithStore.getStore().getId(), new CartVO());
            // 然后设置或更新cartVO中的store
            cartVO.setStore(cartWithStore.getStore());
            // 如果cartVO没有list集合，就创建
            if (cartVO.getCartList() == null) {
                cartVO.setCartList(new ArrayList<>());
            }
            // 将购物车项添加到CartVO的cartList中
            cartVO.getCartList().add(cartWithStore.getCart());
            // 放回map中
            map.put(cartWithStore.getStore().getId(), cartVO);
        }

        return UserCartVO.builder()
                .cartList(new ArrayList<CartVO>(map.values()))
                .build();
    }

    /**
     * 删除用户购物车
     *
     * @param ids
     */
    @Override
    public void deleteUserCart(List<Long> ids) {

        List<Cart> cartList = ids.stream()
                .map(cartMapper::queryByCartId)  // 尝试根据每个ID查询购物车
                .filter(Objects::nonNull)// 过滤掉查询结果为null的购物车（即不存在的购物车）
                .collect(Collectors.toList()); // 收集查询结果到列表中

        // 检查是否有购物车不存在
        if (cartList.size() != ids.size()) {
            // 如果过滤后的购物车列表大小不等于原始ID列表的大小，说明有购物车不存在
            throw new CartException(MessageConstant.CART_IS_NOT_EXIST);
        }

        // 批量删除
        cartMapper.batchDeleteCarts(cartList);
    }


    /**
     * 购买
     *
     * @param cartSubmitDTO
     */
    @Override
    @Transactional
    public void buy(CartSubmitDTO cartSubmitDTO) {
        // 得到用户id
        Long userId = cartSubmitDTO.getUserId();
        // 得到购物车列表
        List<Cart> cartList = cartSubmitDTO.getCartList();
        if (cartList == null || cartList.isEmpty()) {
            throw new CartException(MessageConstant.CART_IS_NOT_EXIST);
        }
        // 得到需要的商品列表
        // 将流中的id取出来放到集合中 然后扔给findGoodsById
        List<Goods> goodsList = goodsMapper
                .findGoodsByIds(cartList.stream()
                        .map(Cart::getGoodsId)
                        .collect(Collectors.toList()));
        // 创建订单集合
        List<Orders> ordersList = new ArrayList<>();
        // 得到总金额
        BigDecimal totalAmount = cartSubmitDTO.getTotalAmount();
        User user = userMapper.getById(userId);
        // 如果用户金额小于商品总金额
        if (user.getMoney().compareTo(totalAmount) < 0) {
            throw new CartException(MessageConstant.THE_AMOUNT_IS_INSUFFICIENT);
        }
        // 遍历购物车列表
        for (Cart cart : cartList) {
            // 过滤商品集合 保留goods的id和cart中goodsId相等的goods，返回第一个元素
            // 如果为空就返回默认值 null
            Goods goods = goodsList.stream()
                    .filter(g -> g.getId().equals(cart.getGoodsId()))
                    .findFirst().orElse(null);
            // 如果商品为空
            if (goods == null || goods.getTotal() < cart.getNumber()) {
                throw new GoodsException(MessageConstant.INSUFFICIENT_PRODUCT_QUANTITY);
            }
            // 创建订单
            Orders orders = createOrders(cart, userId);
            // 添加到订单集合中
            ordersList.add(orders);
            // 修改商品库存
            updateGoodsStock(goods, cart);
        }
        // 删除购物车
        cartMapper.batchDeleteCarts(cartList);
        // 添加订单
        ordersMapper.batchInsertOrder(ordersList);
        // 修改金额
        user.setMoney(user.getMoney().subtract(totalAmount));
        // 修改用户
        userMapper.update(user);
    }

    private Orders createOrders(Cart cart, Long userId) {
        Orders orders = new Orders();
        orders.setGoodsId(cart.getGoodsId());
        orders.setOrdersNumber(SnowFlakeUtil.nextId());
        orders.setGoodsName(cart.getGoodsName());
        orders.setNumber(cart.getNumber());
        orders.setTotalPrice(cart.getTotalPrice());
        orders.setPay(PayConstant.WALLET_PAYMENTS);
        orders.setUserId(userId);
        orders.setStoreId(cart.getStoreId());
        orders.setStatus(OrdersConstant.UNSHIPPED_ORDER);
        return orders;
    }

    private void updateGoodsStock(Goods goods, Cart cart) {
        goods.setTotal(goods.getTotal() - cart.getNumber());
        goodsMapper.update(goods);
    }

    private boolean isValid(Cart cart) {
        String goodsName = cart.getGoodsName();
        Integer number = cart.getNumber();
        BigDecimal totalPrice = cart.getTotalPrice();
        User user = userMapper.getById(cart.getUserId());

        if (user == null) {
            throw new CartException(MessageConstant.USER_IS_NOT_EXIST);
        } else if (goodsName == null || "".equals(goodsName)) {
            throw new CartException(MessageConstant.GOODS_IS_NOT_EXIST);
        } else if (number == null || number < 0) {
            throw new CartException(MessageConstant.GOODS_TOTAL_IS_OUT_OF_VALID_RANGE);
        } else if (totalPrice == null || totalPrice.compareTo(new BigDecimal(0)) < 0) {
            throw new CartException(MessageConstant.GOODS_PRICE_IS_OUT_OF_VALID_RANGE);
        }

        return true;
    }
}
