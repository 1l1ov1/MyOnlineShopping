package com.wan.constant;

public class CommentConstant {

    /**
     * 因举报人数过多被隐藏
     */
    public static final Integer HIDDEN = 1;

    /**
     * 正常显示
     */
    public static final Integer NORMAL = 0;

    /**
     * 全部
     */
    public static final Integer ALL = 0;
    /**
     * 好评
     */
    public static final Integer GOOD = 1;

    /**
     * 差评
     */
    public static final Integer BAD = 2;
    public static final int MAX_COMMENT_CONTENT_LENGTH = 300;
    public static final int MIN_COMMENT_CONTENT_LENGTH = 1;

    /**
     * 全不选 （点赞和点踩只能存在一个）
     */
    public static final int COMMENT_ACTION_NONE = 3;
    /**
     * 点赞
     */
    public static final int COMMENT_ACTION_LIKE = 1;

    /**
     * 点踩
     */
    public static final int COMMENT_ACTION_DISLIKE = 2;

}

