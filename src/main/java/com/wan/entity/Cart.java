package com.wan.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 购物车
 *
 * @TableName cart
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {

    /**
     * 购物车id
     */
    @ApiModelProperty("购物车id")
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long goodsId;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;
    /**
     * 购买数量
     */
    @ApiModelProperty("购买数量")
    private Integer number;
    /**
     * 总价
     */
    @ApiModelProperty("总价")
    private BigDecimal totalPrice;
    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    private String coverPic;
    /**
     * 商品单价
     */
    @ApiModelProperty("商品单价")
    private BigDecimal goodsPrice;
    /**
     * 商品折扣
     */
    @ApiModelProperty("商品折扣")
    private double discount;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;


}
