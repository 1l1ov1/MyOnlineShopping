package com.wan.aspect;

import com.wan.annotation.AutoFill;
import com.wan.constant.AutoFillConstant;
import com.wan.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// 配置为切面类
@Aspect
// 放到容器中
@Component
@Slf4j
public class AutoFillAspect {
    // 设置切入点 为com.wan.mapper包下所有的类的所有方法并且拥有AutoFill注解的方法
    @Pointcut("execution(* com.wan.mapper.*.*(..)) && @annotation(com.wan.annotation.AutoFill)")
    public void autoFillAspect() {
    }

    // 前置通知  在执行数据库操作之前先填充字段
    @Before("autoFillAspect()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始自动填充公共字段....");
        // 获取封装了署名信息的对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 查看这个方法上是否有AutoFill注解
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        // 获取AutoFill注解中的参数
        OperationType operationType = autoFill.value();
        // 获取当前被拦截的方法参数...实体对象
        Object[] args = joinPoint.getArgs();
        // 如果没有参数
        if (args == null || args.length == 0) {
            return;
        }

        // 得到实体类  默认将实体类放到0索引
        Object entity = args[0];

        // 得到现在的时间
        LocalDateTime now = LocalDateTime.now();
        // 如果操作是修改操作
        if (operationType == OperationType.UPDATE) {
            // 从实体类中设置公共字段
            try {
                // 如果得到的实体是集合类型
                if (entity instanceof Collection) {
                    // 如果是List
                    if (entity instanceof List) {
                        // 创建ArrayList对象
                        List list = new ArrayList();
                        // 拷贝
                        list.addAll((Collection) entity);
                        // 遍历
                        for (Object o : list) {
                            // 通过反射得到对应的方法
                            Method setUpdateTime = o.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                            // 通过反射赋值
                            setUpdateTime.invoke(o, now);
                        }
                    }

                } else {
                    // 通过反射得到对应的方法
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    // 通过反射赋值
                    setUpdateTime.invoke(entity, now);
                }

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            // 如果操作是插入操作
        } else if (operationType == OperationType.INSERT) {

            try {
                // 如果得到的实体是集合类型
                if (entity instanceof Collection) {
                    // 如果是List
                    if (entity instanceof List) {
                        // 创建ArrayList对象
                        List list = new ArrayList();
                        // 拷贝
                        list.addAll((Collection) entity);
                        // 遍历
                        for (Object o : list) {
                            // 通过反射得到对应的方法
                            Method setUpdateTime = o.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                            Method setCreateTime = o.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                            // 通过反射赋值
                            setUpdateTime.invoke(o, now);
                            setCreateTime.invoke(o, now);
                        }
                    }

                } else {
                    // 通过反射得到对应的方法
                    Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    // 通过反射赋值
                    setCreateTime.invoke(entity, now);
                    setUpdateTime.invoke(entity, now);
                }

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
