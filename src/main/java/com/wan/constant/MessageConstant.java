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
    public static final String VERIFY_CODE_EXPIRE = "验证码过期，请刷新页面或刷新验证码";
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

    /**
     * 用户已下单，请注意查收
     */
    public static final String USER_HAS_ORDERED = "用户已下单，请注意查收";

    /**
     * 用户催单了，请尽快发货
     */
    public static final String USER_URGE_ORDER = "用户催单了，请尽快发货";

    /**
     * 您的账号因违法该平台相关条例被永久封禁
     */
    public static final String ACCOUNT_IS_FOREVER_BAN = "您的账号因违法该平台相关条例，被永久封禁";

    /**
     * 您的账号因违反该平台相关条例被永久禁言
     */
    public static final String ACCOUNT_IS_FOREVER_FORBIDDEN = "您的账号因违反该平台相关条例，被永久禁言";

    /**
     * 您的账号已被管理员解封，恢复正常
     */
    public static final String ACCOUNT_IS_UNBAN = "您的账号已被管理员解封，恢复正常";

    /**
     * 您的账号封禁时间到期，已解封
     */
    public static final String ACCOUNT_IS_UNBAN_BY_TIME = "您的账号封禁时间到期，已解封";
    /**
     * 评论不能为空
     */
    public static final String COMMENT_CONTENT_IS_EMPTY = "评论内容不能为空";

    /**
     * 评论评分超出有效范围
     */
    public static final String COMMENT_STAR_OUT_OF_RANGE = "评论评分超出有效范围";

    /**
     * 字段不能为空
     */
    public static final String FIELD_IS_EMPTY = "字段不能为空";


    /**
     * 评论内容长度超出有效范围
     */
    public static final String COMMENT_CONTENT_LENGTH_OUT_OF_RANGE = "评论内容长度超出有效范围";

    /**
     * 当天举报次数超出范围
     */
    public static final String REPORT_COUNT_OUT_OF_RANGE = "当天举报次数超出范围";

    /**
     * 已经举报过该评论
     */

    public static final String ALREADY_REPORTED = "已经举报过该评论";

    /**
     * 举报内容长度超出有效范围
     */
    public static final String REPORT_CONTENT_LENGTH_ERROR = "举报内容长度超出有效范围，应为1-300个字符，不能全是空格";

    /**
     * 封禁或禁言类型错误
     */
    public static final String TYPE_IS_WORRY = "封禁或禁言类型错误";

    /**
     * 用户已经封禁，无法操作
     */
    public static final String USER_HAS_BANNED = "该账号已经被管理员封禁，无法操作，结束时间为：";

    /**
     * 用户已被禁言，无法发表评论
     */
    public static final String USER_HAS_FORBIDDEN_COMMENT = "该账号已被管理员禁言，无法发表评论，结束时间为：";

    /**
     * 举报不存在
     */
    public static final String REPORT_IS_NOT_EXIST = "举报不存在";

    /**
     * 评论不存在
     */
    public static final String COMMENT_IS_NOT_EXIST = "评论不存在";

    /**
     * 评论状态非法
     */
    public static final String COMMENT_STATUS_IS_INVALID = "评论状态非法";

    /**
     * 非法的奖励金额
     */
    public static final String AWARD_ACCOUNT_IS_VALID = "非法的奖励金额";
    /**
     * 奖励举报
     */
    public static final String USER_REPORT_REWARD_SUCCESS = "感谢您积极举报违规行为，为维护平台良好氛围做出贡献。您的举报奖励已成功发放至您的账号，请及时查收。如有疑问，欢迎随时联系我们。共计：";


    /**
     * 发送的消息为空
     */
    public static final String MESSAGE_IS_NULL = "信息为空";

}
