package com.wan.controller;

import com.wan.constant.OrdersConstant;
import com.wan.constant.RedisConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.CartSubmitDTO;
import com.wan.entity.Cart;
import com.wan.result.Result;
import com.wan.service.AddressService;
import com.wan.service.CartService;
import com.wan.service.UserService;
import com.wan.utils.RedisUtils;
import com.wan.vo.UserCartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cart")
@Api("购物车接口")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result<String> addCart(@RequestBody Cart cart) {
        log.info("添加购物车 {}", cart);
        cartService.addCart(cart);
        RedisUtils.clearRedisCache(redisTemplate, RedisConstant.USER_CART + cart.getUserId());
        return Result.success("添加成功");
    }

    @GetMapping("{id}")
    @ApiOperation("得到用户的购物车")
    public Result<UserCartVO> getUserCart(@PathVariable Long id) {
        log.info("用户查询其购物车 {}", id);
        UserCartVO userCartVO = RedisUtils.redisGetHashValues(redisTemplate,
                RedisConstant.USER_CART + id, UserCartVO.class);
        if (userCartVO != null) {
            return Result.success(userCartVO);
        }
        userCartVO = cartService.getDetail(id);
        RedisUtils.redisHashPut(redisTemplate, RedisConstant.USER_CART + id, userCartVO, 1L, TimeUnit.HOURS);
        return Result.success(userCartVO);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户的某一项购物车")
    public Result<String> deleteUserCart(@RequestParam List<Long> ids) {
        log.info("删除用户的购物车 {}", ids);
        cartService.deleteUserCart(ids);
        Long userId = ThreadBaseContext.getCurrentId();
        ThreadBaseContext.removeCurrentId();
        RedisUtils.clearRedisCache(redisTemplate, RedisConstant.USER_CART + userId);
        return Result.success("删除成功");
    }

    @PostMapping("/submit")
    @ApiOperation("提交购物车")
    public Result<String> submitUserCart(@RequestBody CartSubmitDTO cartSubmitDTO) {
        log.info("提交购物车 {}", cartSubmitDTO);
        cartService.buy(cartSubmitDTO);
        // 清空对应用户的购物车缓存和对应用户的未发货订单缓存
        RedisUtils.clearRedisCache(redisTemplate,
                RedisConstant.USER_CART + cartSubmitDTO.getUserId(),
                RedisConstant.USER_ORDERS + OrdersConstant.UNSHIPPED_ORDER + "-" + cartSubmitDTO.getUserId());
        return Result.success("购买成功");
    }
}
