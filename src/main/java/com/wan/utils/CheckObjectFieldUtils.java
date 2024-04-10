package com.wan.utils;

import java.lang.reflect.Field;
import java.util.*;

public class CheckObjectFieldUtils {
    /**
     * 默认不检查的字段名称数组
     */
    private static final List<String> DEFAULT_NOT_CHECK_FILED_NAME = Arrays.asList("createTime", "updateTime");


    /**
     * 检查给定对象中除指定排除字段外的所有字段是否均不为空。
     *
     * @param object    要检查的物体实例。要求传入的物体实例不能为空。
     * @param fieldName 一个或多个字段名称，表示不需要检查的字段。 已经默认排除了createTime和updateTime字段。
     *                  如果传入此参数，则会和默认排除字段合并后进行字段排除检查。
     * @return 如果除排除字段外的所有字段在给定对象中都不为空，则返回true；否则返回false。
     * 该方法会遍历对象的所有字段（包括私有字段），如果字段的值为空，则返回false。
     * @throws IllegalAccessException 如果访问字段时发生错误，例如没有权限访问私有字段。
     *                                如果在检查过程中遇到无法访问的字段，会抛出此异常。
     */
    public static boolean areAllNonExcludedFieldsNotNull(Object object, String... fieldName) throws IllegalAccessException {
        if (fieldName == null) {
            throw new IllegalArgumentException("fieldName不能为空");
        }
        // 创建一个HashSet用于存储所有需要排除的字段名
        Set<String> excludedFieldSet = new HashSet<>(Arrays.asList(fieldName));
        // 将默认排除的字段添加到排除字段集合中
        excludedFieldSet.addAll(DEFAULT_NOT_CHECK_FILED_NAME);
        // 遍历对象的所有声明字段
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);  // 设置字段为可访问，以避免访问权限问题
            try {
                // 如果字段名在排除集合中，则跳过该字段的检查
                if (excludedFieldSet.contains(field.getName())) {
                    continue;
                }
                // 检查字段的值是否为空，如果为空，则函数返回false
                if (field.get(object) == null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                // 处理或记录访问字段时的异常，然后重新抛出
                throw new IllegalAccessException(e.getMessage());
            }
        }
        // 如果所有非排除字段都不为空，则返回true
        return true;
    }


    /**
     * 检查给定对象中除默认排除字段（createTime、updateTime）外的所有字段是否均不为空。
     *
     * @param object 要检查的对象实例。不能为null。
     * @return 如果除默认排除字段外的所有字段在给定对象中都不为空，则返回true；否则返回false。
     * @throws IllegalAccessException 如果访问字段时发生错误，或者对象实例的字段是私有的且没有设置为可访问。
     */
    public static boolean areAllNonExcludedFieldsNotNullByDefault(Object object) throws IllegalAccessException {
        // 创建一个集合来存储默认排除的字段名
        Set<String> excludedFieldSet = new HashSet<>(DEFAULT_NOT_CHECK_FILED_NAME);

        // 遍历对象的所有声明字段
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true); // 设置字段为可访问

            // 如果当前字段在默认排除集合中，跳过该字段的检查
            if (excludedFieldSet.contains(field.getName())) {
                continue;
            }

            try {
                // 检查字段的值是否为null
                if (field.get(object) == null) {
                    return false; // 如果有任何一个非排除字段为null，则立即返回false
                }
            } catch (IllegalAccessException e) {
                // 处理或记录访问异常，然后重新抛出
                throw new IllegalAccessException(e.getMessage());
            }
        }

        return true; // 当所有非排除字段都不为null时，返回true
    }


    /**
     * 通过反射检查对象的所有字段是否为空。特定字段（如创建时间和更新时间）默认不参与检查。
     *
     * @param object 要检查其字段是否为空的对象实例。
     * @return 如果对象的所有非默认排除字段都不为空，则返回true；否则返回false。
     * @throws IllegalAccessException 如果在访问字段时遇到权限问题。
     */
    public static boolean allFieldNotNUll(Object object) throws IllegalAccessException {
        // 遍历对象的所有字段
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);  // 克服访问控制限制，使所有字段可访问

            try {
                // 检查字段值是否为空
                if (field.get(object) == null) {
                    return false;  // 发现任一空字段即返回false
                }
            } catch (IllegalAccessException e) {
                // 重新抛出访问字段时的异常
                throw new IllegalAccessException(e.getMessage());
            }
        }

        return true;  // 所有字段均非空，返回true
    }


}
