package com.wan.common;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * SerializedLambda 是 Java 中用于序列化和反序列化 Lambda 表达式的一个内部类，它位于 java.lang.invoke 包下。
 * 当你有一个实现了 java.io.Serializable 接口的 Lambda 表达式，并且这个 Lambda 需要被序列化时，
 * Java 运行时系统会使用 SerializedLambda 对象来表示 Lambda 的元数据。这个元数据包含了关于 Lambda 表达式的重要信息
 *
 * @param <T>
 */
@FunctionalInterface
public interface Supplier<T> extends Serializable {
    T get();

    /**
     * 获取当前对象的序列化lambda表达式实例。
     * 该方法通过调用对象的私有方法`writeReplace`来获取一个代表该对象的`SerializedLambda`实例。
     * `SerializedLambda`是一个用于序列化lambda表达式的接口，可以提供关于lambda表达式的具体信息，
     * 如捕获变量、实现接口、方法名等。
     *
     * @return SerializedLambda 返回当前对象的序列化lambda表达式实例。
     * @throws Exception 如果在获取`SerializedLambda`实例过程中发生错误，则抛出异常。
     */
    default SerializedLambda getSerializedLambda() throws Exception {
        // 设置`writeReplace`方法为可访问，并调用该方法以获取对象的序列化代理
        Method write = this.getClass().getDeclaredMethod("writeReplace");
        write.setAccessible(true);
        return (SerializedLambda) write.invoke(this);
    }

    /**
     * 获取序列化lambda表达式的实现类名称。
     * 此方法尝试从序列化的lambda中提取其实现类的名称，并以字符串形式返回。
     * 如果提取过程中发生异常，则返回null。
     *
     * @return 实现类的名称，如果无法获取则返回null。
     */
    default String getImplClass() {
        try {
            // 尝试获取序列化lambda的实现类名称
            return getSerializedLambda().getImplClass();
        } catch (Exception e) {
            // 如果过程中发生异常，则返回null
            return null;
        }
    }

    default String getImplMethodName() {
        try {
            return getSerializedLambda().getImplMethodName();
        } catch (Exception e) {
            return null;
        }
    }
}
