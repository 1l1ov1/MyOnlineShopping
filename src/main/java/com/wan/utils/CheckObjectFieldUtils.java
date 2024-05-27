package com.wan.utils;

import com.wan.common.Supplier;
import com.wan.dto.ForbiddenOrBanDTO;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    @Deprecated
    public static boolean areAllNonExcludedFieldsNotNull(Object object, String... fieldName) throws IllegalAccessException {
        // 如果不空，就能往下走，如果空，就抛异常
        Objects.requireNonNull(object);

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
     * 检查给定对象的所有非排除字段是否都不为null。
     *
     * @param object    要检查的物体对象。
     * @param suppliers 一个或多个Supplier实例，其implMethodName将被视为排除字段。
     * @return 如果所有非排除字段都不为null，则返回true；否则返回false。
     * @throws IllegalAccessException   如果访问字段时发生异常。
     * @throws IllegalArgumentException 如果suppliers参数为null。
     */
    public static <T> boolean areAllNonExcludedFieldsNotNull(Object object, Supplier<T>... suppliers) throws IllegalAccessException {
        // 如果不空，就能往下走，如果空，就抛异常
        Objects.requireNonNull(object);

        if (suppliers == null) {
            throw new IllegalArgumentException("方法不能为空");
        }
        // 创建一个HashSet用于存储所有需要排除的字段名
        Set<String> excludedFieldSet = new HashSet<>();
        for (Supplier<T> method : suppliers) {
            // 得到方法引用名
            String implMethodName = method.getImplMethodName();
            // 将方法名去除掉get和is前缀并将首字母小写放入到数组中
            excludedFieldSet.add(deletePrefix(implMethodName));
        }
        // 将默认排除的字段添加到排除字段集合中
        excludedFieldSet.addAll(DEFAULT_NOT_CHECK_FILED_NAME);

        // 遍历对象的所有声明字段
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);  // 设置字段为可访问，以避免访问权限问题
            try {
                if (excludedFieldSet.contains(field.getName())) {
                    // 如果说在排除集合中，则跳过该字段的检查
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

    private static String deletePrefix(String fieldName) {
        String prefix = "";
        if (fieldName.startsWith("get")) {
            prefix = "get";
        } else if (fieldName.startsWith("is")) {
            // 如果类型是基本类型，并且是is打头那么lombok产生的getter就是is开头
            prefix = "is";
        }
        // 然后切割掉前缀
        return capitalize(fieldName.substring(prefix.length()));
    }

    /**
     * 将给定字符串的第一个字符转换为小写。
     *
     * @param str 输入的字符串。
     * @return 转换后的字符串，其中第一个字符为小写。
     */
    private static String capitalize(String str) {

        // 将字符串的第一个字符转换为小写，并与剩余部分合并
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 检查给定对象中除默认排除字段（createTime、updateTime）外的所有字段是否均不为空。
     *
     * @param object 要检查的对象实例。不能为null。
     * @return 如果除默认排除字段外的所有字段在给定对象中都不为空，则返回true；否则返回false。
     * @throws IllegalAccessException 如果访问字段时发生错误，或者对象实例的字段是私有的且没有设置为可访问。
     */
    public static boolean areAllFieldsNotNullExcludeDefault(Object object) throws IllegalAccessException {
        // 如果不空，就能往下走，如果空，就抛异常
        Objects.requireNonNull(object);
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
        // 如果不空，就能往下走，如果空，就抛异常
        Objects.requireNonNull(object);
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

    /**
     * 只检测指定的字段是否为空
     *
     * @param object
     * @param fieldName
     * @return
     */
    @Deprecated
    public static Boolean areAllIncludedFieldsIsNull(Object object, String... fieldName) throws IllegalAccessException {
        // 如果不空，就能往下走，如果空，就抛异常
        Objects.requireNonNull(object);

        if (fieldName == null) {
            throw new IllegalArgumentException("fieldName不能为空");
        }
        // 创建一个HashSet用于存储所有需要排除的字段名
        Set<String> includedFieldSet = new HashSet<>(Arrays.asList(fieldName));
        // 将默认排除的字段添加到排除字段集合中

        // 遍历对象的所有声明字段
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);  // 设置字段为可访问，以避免访问权限问题
            try {
                // 如果字段名在包含集合中，则检查该字段
                if (includedFieldSet.contains(field.getName())) {
                    // 检查字段的值是否为空，如果为空，则函数返回true
                    if (field.get(object) == null) {
                        return true;
                    }
                }

            } catch (IllegalAccessException e) {
                // 处理或记录访问字段时的异常，然后重新抛出
                throw new IllegalAccessException(e.getMessage());
            }
        }
        // 如果指定字段都不为空，则返回false
        return false;
    }

    /**
     * 检测指定对象中，由suppliers参数指定的字段是否全部为空。该方法通过反射访问对象的字段，
     * 并根据字段名判断是否需要检查其值是否为空。
     *
     * @param object    需要被检查的对象。不能为null。
     * @param suppliers 一个或多个Supplier实例，每个实例代表一个需要检查的字段。
     *                  Supplier的getImplMethodName方法返回的字符串，
     *                  会被当作字段名来识别。不能为null。
     * @return 如果指定字段的所有值有空，则返回true；否则返回false。
     * @throws IllegalAccessException   如果访问字段时发生权限异常。
     * @throws IllegalArgumentException 如果suppliers为null。
     */
    public static <T> Boolean areAllIncludedFieldsIsNull(Object object, Supplier<T>... suppliers) throws IllegalAccessException {
        // 如果不空，就能往下走，如果空，就抛异常
        Objects.requireNonNull(object);

        if (suppliers == null) {
            throw new IllegalArgumentException("suppliers不能为空");
        }

        // 创建一个HashSet用于存储所有需要检测的字段名
        Set<String> includedFieldSet = new HashSet<>();
        // 将suppliers中每个Supplier实例对应的字段名（通过getImplMethodName获取）添加到检测字段集合中
        for (Supplier<T> supplier : suppliers) {
            String implMethodName = supplier.getImplMethodName();
            includedFieldSet.add(deletePrefix(implMethodName));
        }

        // 遍历对象的所有声明字段
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);  // 设置字段为可访问，以避免访问权限问题
            try {
                // 如果字段名在检测字段集合中，则检查该字段的值是否为空
                if (includedFieldSet.contains(field.getName())) {
                    // 如果字段的值为空，则函数返回false
                    if (field.get(object) == null) {
                        return true;
                    }
                }

            } catch (IllegalAccessException e) {
                // 处理或记录访问字段时的异常，然后重新抛出
                throw new IllegalAccessException(e.getMessage());
            }
        }
        // 如果所有指定字段的值都不为空，则返回true
        return false;
    }

}
