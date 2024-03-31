package com.wan.constant;

public class OrdersConstant {
    private OrdersConstant() {

    }

    /**
     * 全部订单
     */
    public static final Integer ALL_ORDERS = 0;
    /**
     * 未发货订单
     */
    public static final Integer UNSHIPPED_ORDER = 1;
    /**
     * 已发货订单
     */
    public static final Integer SHIPPED_ORDER = 2;
    /**
     * 退款订单
     */
    public static final Integer REFUNDED_ORDER = 3;

    /**
     * 用户已签收
     */
    public static final Integer USER_RECEIVE_PRODUCT = 4;
    /**
     * 交易成功订单
     */
    public static final Integer SUCCESSFUL_ORDER = 5;
}
