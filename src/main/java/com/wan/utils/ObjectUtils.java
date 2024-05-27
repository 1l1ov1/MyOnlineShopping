package com.wan.utils;

public class ObjectUtils {

    private ObjectUtils() {

    }

    public static Boolean isEmpty(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }
}
