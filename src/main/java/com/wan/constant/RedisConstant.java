package com.wan.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 缓存的键常量
 */
public class RedisConstant {


    private RedisConstant() {

    }

    /**
     * 验证码
     */
    public static final String VERIFY_CODE = "verify_code";

    /**
     * 查询用户个人信息
     */
    public static final String USER_DETAIL = "user_detail-";

    /**
     * 管理员查询用户的分页查询
     */
    public static final String ADMINISTRATOR_USER_PAGE = "administrator_user_page";

    /**
     * 管理员查询商品分页
     */
    public static final String ADMINISTRATOR_GOODS_PAGE = "administrator_goods_page";

    /**
     * 用户所有首页商品
     */
    public static final String USER_ALL_GOODS_PAGE = "user_all_goods_page";
    /**
     * 管理员查询商店分页
     */
    public static final String ADMINISTRATOR_STORE_PAGE = "administrator_store_page";

    /**
     * 管理员查询全部订单分页
     */
    public static final String ADMINISTRATOR_ALL_ORDERS_PAGE = "administrator_o_all_orders_page";

    /**
     * 管理员查询未发货订单
     */
    public static final String ADMINISTRATOR_UNSHIPPED_ORDERS_PAGE = "administrator_o_unshipped_orders_page";

    /**
     * 管理员查询已发货订单
     */
    public static final String ADMINISTRATOR_SHIPPED_ORDERS_PAGE = "administrator_o_shipped_orders_page";

    /**
     * 管理员查询已退款订单
     */
    public static final String ADMINISTRATOR_REFUNDED_ORDERS_PAGE = "administrator_o_refunded_orders_page";

    /**
     * 管理员查询用户已签收
     */
    public static final String ADMINISTRATOR_USER_RECEIVE_PRODUCT_ORDERS_PAGE = "administrator_user_receive_product_orders_page";

    /**
     * 管理员查询交易成功订单
     */
    public static final String ADMINISTRATOR_SUCCESSFUL_ORDERS_PAGE = "administrator_o_successful_orders_page";

    /**
     * 管理员查询分类分页
     */
    public static final String ADMINISTRATOR_CATEGORY_PAGE = "administrator_category_page";
    /**
     * 清空订单缓存的正则表达式
     */
    public static final String ADMINISTRATOR_CLEAR_ORDERS_PATTERN = "administrator_o_*";

    /**
     * 管理员查看提现记录
     */
    public static final String ADMINISTRATOR_WITHDRAW_RECORD_PAGE = "administrator_withdraw_record_page";

    /**
     * 管理员查看全部申请
     */
    public static final String ADMINISTRATOR_APPLY_PAGE = "administrator_ap_apply_page";

    /**
     * 管理员查看待审核申请
     */
    public static final String ADMINISTRATOR_UNDER_REVIEW_APPLY_PAGE = "administrator_ap_under_review_apply_page";

    /**
     * 管理员查看审核通过申请
     */
    public static final String ADMINISTRATOR_APPROVED_APPLY_PAGE = "administrator_ap_approved_apply_page";

    /**
     * 管理员查看拒绝申请
     */
    public static final String ADMINISTRATOR_REVIEW_REJECTION_APPLY_PAGE = "administrator_ap_review_rejection_apply_page";

    /**
     * 清空申请缓存的正则表达式
     */
    public static final String ADMINISTRATOR_APPLY_PATTERN = "administrator_ap_*";


    /**
     * 商家查询商品分页
     */
    public static final String STORE_GOODS_PAGE = "store_goods_page-";

    /**
     * 用户的购物车
     */
    public static final String USER_CART = "user_cart-";

    /**
     * 用户的收藏夹
     */
    public static final String USER_FAVORITE = "user_favorite-";

    /**
     * 用户的订单
     */
    public static final String USER_ORDERS = "user_orders-";

    /**
     * 商店全部评论
     */
    public static final String STORE_COMMENT = "store_comment-";

    /**
     * 商店好评
     */

    public static final String STORE_COMMENT_GOOD = "store_comment_good-";

    /**
     * 商店差评
     */

    public static final String STORE_COMMENT_BAD = "store_comment_bad-";

    /**
     * 商店评论行为
     */
    public static final String STORE_COMMENT_ACTION = "store_comment_action-";
}
