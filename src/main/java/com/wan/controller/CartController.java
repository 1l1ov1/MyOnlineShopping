package com.wan.controller;

import com.wan.dto.CartSubmitDTO;
import com.wan.entity.Cart;
import com.wan.result.Result;
import com.wan.server.AddressService;
import com.wan.server.CartService;
import com.wan.server.UserService;
import com.wan.vo.UserCartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result<String> addCart(@RequestBody Cart cart) {
        log.info("添加购物车 {}", cart);
        cartService.addCart(cart);
        return Result.success("添加成功");
    }

    @GetMapping("{id}")
    @ApiOperation("得到用户的购物车")
    public Result<UserCartVO> getUserCart(@PathVariable Long id) {
        log.info("用户查询其购物车 {}", id);
        UserCartVO userCartVO = cartService.getDetail(id);
        return Result.success(userCartVO);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户的某一项购物车")
    public Result<String> deleteUserCart(@RequestParam List<Long> ids) {
        log.info("删除用户的购物车 {}", ids);
        cartService.deleteUserCart(ids);
        return Result.success("删除成功");
    }

    @PostMapping("/submit")
    @ApiOperation("提交购物车")
    public Result<String> submitUserCart(@RequestBody CartSubmitDTO cartSubmitDTO) {
        log.info("提交购物车 {}", cartSubmitDTO);
        cartService.buy(cartSubmitDTO);

        return Result.success("购买成功");
    }
}
