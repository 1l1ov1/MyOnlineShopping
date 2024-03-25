package com.wan.utils;

import java.lang.reflect.Field;

public class CheckObjectFieldUtils {

    /**
     * 用反射的方式 来判断某个对象的字段值是否全不为空
     * @param object
     * @return
     */
    public static boolean allFieldsNotNull(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(object) == null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                // Handle exception or log it
                return false;
            }
        }
        return true;
    }
}
