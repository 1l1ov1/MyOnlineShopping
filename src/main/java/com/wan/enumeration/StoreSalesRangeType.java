package com.wan.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * 门店销售范围类型枚举
 * 该枚举定义了查询的时间类型，例如：昨日 近7日 近30日 本周 本月等。
 */
public enum StoreSalesRangeType {
    YESTERDAY(-1, "昨日"),
    LAST_7_DAYS(-7, "近7日"),
    LAST_30_DAYS(-30, "近30日"),
    THIS_WEEK(7, "本周"),
    THIS_MONTH(30, "本月");

    /**
     * 采用map集合来存储类型和枚举实例的映射关系，以便快速查找。
     */
    private static final Map<Integer, StoreSalesRangeType> TYPE_MAP = new HashMap<>();

    // 静态代码块，在类加载时执行
    static {
        for (StoreSalesRangeType storeSalesRangeType : StoreSalesRangeType.values()) {
            TYPE_MAP.put(storeSalesRangeType.getDay(), storeSalesRangeType);
        }
    }

    private final Integer day;
    private final String name;

    StoreSalesRangeType(Integer type, String name) {
        this.day = type;
        this.name = name;
    }

    public Integer getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return day + "";
    }

    /**
     * 根据给定的类型获取相应的销售范围类型。
     *
     * @param day 销售范围类型的标识，为整数类型。
     * @return 返回匹配的StoreSalesRangeType枚举实例，如果没有匹配的类型，则返回null。
     */
    public static StoreSalesRangeType getStoreSalesRangeType(Integer day) {
        // 直接通过映射表查询，无需遍历枚举
        return day == null ? null : TYPE_MAP.get(day);
    }
}
