package com.wan.constant;

/**
 * 信息提示常量类
 */
public class MessageConstant {

    private MessageConstant() {

    }

    // 账号
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String ACCOUNT_NOT_FOUND = "账号不存在";
    public static final String ACCOUNT_LOCKED = "账号被锁定";
    public static final String ALREADY_EXISTS = "已存在";
    public static final String UNKNOWN_ERROR = "未知错误";
    public static final String USER_NOT_LOGIN = "用户未登录";
    public static final String LOGIN_FAILED = "登录失败";
    public static final String VERIFY_CODE_ERROR = "验证码错误";
    public static final String ACCOUNT_EXIST = "账号已存在";
    // 商店

    public static final String MANAGE_IS_NOT_ALLOWED_TO_OPEN_STORE = "管理员不允许开店";
    public static final String STORE_EXIST = "商店已存在";
    public static final String STORE_IS_NOT_EXIST = "商店不存在";
    public static final String STORE_NAME_IS_EMPTY = "商店名为空";
    public static final String STORE_STATUS_IS_EMPTY = "商店状态为空";
    public static final String STORE_STATUS_IS_VALID = "商店状态为非法状态";
    public static final String COMMON_USER_IS_NOT_ALLOWED_TO_OPEN_STORE = "普通用户不允许开店，请先成为店家";
    public static final String STORE_NAME_LENGTH_VALID = "商店名称长度非法";
    public static final String ONLY_HAS_ONE_STORE = "一个商家只能拥有一个商店";

    public static final String THE_STATUS_IS_NOT_EXIST = "这个状态不存在";

    public static final String STORE_LOGO_IS_EMPTY = "商店logo为空";
    public static final String STORE_ADDRESS_IS_NOT_ALLOWED_TO_BE_EMPTY = "商店地址不允许为空";
    public static final String THE_STORE_HAS_ITEMS_ON_THE_SHELVES = "商店还有上架商品，无法删除";
    // 商品
    public static final String GOODS_IS_NOT_EXIST = "商品不存在";
    public static final String GOODS_NAME_IS_NOT_ALLOWED_TO_BE_EMPTY = "商品名不允许为空";
    public static final String GOODS_DESCRIPTION_IS_NOT_ALLOWED_TO_BE_EMPTY = "商品描述不允许为空";
    public static final String GOODS_DISCOUNT_IS_NOT_ALLOWED_TO_BE_EMPTY = "商品折扣不允许为空";
    public static final String GOODS_DISCOUNT_IS_OUT_OF_VALID_RANGE = "商品折扣超出有效范围";
    public static final String GOODS_PRICE_IS_NOT_ALLOWED_TO_BE_EMPTY = "商品价格不允许为空";
    public static final String GOODS_PRICE_IS_OUT_OF_VALID_RANGE = "商品价格超出有效范围";
    public static final String GOODS_STATUS_IS_NOT_ALLOWED_TO_BE_EMPTY = "商品状态不允许为空";
    public static final String GOODS_STATUS_IS_OUT_OF_VALID_RANGE = "商品状态超出有效范围";
    public static final String GOODS_TOTAL_IS_NOT_ALLOWED_TO_BE_EMPTY = "商品总量不允许为空";
    public static final String GOODS_TOTAL_IS_OUT_OF_VALID_RANGE = "商品总量超出有效范围";
    public static final String PLEASE_CHOOSE_ONE_MORE_DATA_TO_DELETE = "请选择至少一条数据来删除";

    // 购物车
    public static final String USER_IS_NOT_EXIST = "用户不存在";
    public static final String CART_IS_NOT_EXIST = "购物车不存在";

    public static final String THE_AMOUNT_IS_INSUFFICIENT = "金额不足，请充值";
    public static final String INSUFFICIENT_PRODUCT_QUANTITY = "商品数量不足";

    // 订单
    public static final String ORDERS_NUMBER_IS_NOT_EXIST = "订单号不存在";
    public static final String ORDERS_STATUS_IS_WRONG = "订单状态出错";

    // 申请
    public static final String APPLY_IS_NOT_EXIST = "申请不存在";
    public static final String APPLY_IS_EXIST = "已发送过审核，请勿重复发送";

    public static final String APPLY_STATUS_IS_WRONG = "申请状态异常";

    // 分类
    public static final String CATEGORY_IS_NOT_EXIST = "分类不存在";

    public static final String CATEGORY_IS_EXIST = "分类已经存在";
    public static final String CATEGORY_STATUS_IS_WRONG = "分类状态异常";
    public static final String CATEGORY_NAME_IS_EMPTY = "分类名称为空";

    public static final String HAVING_SHELVE_GOODS_IN_THE_CATEGORY = "该分类下还有上架商品，无法禁用或删除";

    // 收藏夹
    public static final String THIS_FAVORITE_IS_NOT_EXIST = "该收藏品不存在";

    // 地址
    public static final String ADDRESS_IS_EXIST = "地址已经存在";

    public static final String ADDRESS_IS_NOT_EXIST = "地址不存在";

    public static final String OUT_OF_MAX_ADDRESS_NUMBER = "超出最大地址数";

}
