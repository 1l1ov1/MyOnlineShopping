package com.wan.annotation;

import com.wan.enumeration.OperationType;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：用于标识某个方法需要进行功能字段自动填充处理
 */
// 放到方法上
@Target(ElementType.METHOD)
// 运行时校验
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    // 设置参数
    OperationType value();
}
