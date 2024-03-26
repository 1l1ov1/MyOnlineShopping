package com.wan.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CheckObjectFieldUtils {
    private static Set<String> set = new HashSet<String>();

    static {
        // set.add("id");
        set.add("createTime");
        set.add("updateTime");
        set.add("page");
        set.add("pageSize");
        set.add("sort");
    }

    /**
     * 用反射的方式 来判断某个对象的字段值是否全不为空
     *
     * @param object
     * @return
     */
    public static boolean allFieldsNotNull(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                // 如果在排除集合中，就不检查
                if (set.contains(field.getName())) {
                    continue;
                }
                if (field.get(object) == null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                // Handle exception or log it
                throw new IllegalAccessException(e.getMessage());
            }
        }
        return true;
    }
}
