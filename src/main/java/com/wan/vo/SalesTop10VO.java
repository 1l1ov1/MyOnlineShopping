package com.wan.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * 销售额前十的视图对象类
 * 该类用于封装销售额前十的商品信息，包括商品名称列表和对应销售数量列表。
 */
@Data
@Builder
public class SalesTop10VO {
    private List<String> goodsNameList; // 商品名称列表

    private List<Integer> numberList; // 销售数量列表
}
