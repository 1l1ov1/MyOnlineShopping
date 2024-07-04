package com.wan.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 由于@ServerEndpoint注解的类不是Spring MVC的控制器，而是WebSocket处理类，它不在Spring的控制范围内，
 * 因此Spring无法自动处理@Autowired。所以要使用service类需要引入应用上下文
 */
// 获取上下文工具类
public class SpringContextUtils {

    private static ApplicationContext applicationContext;

    private SpringContextUtils() {

    }

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return SpringContextUtils.applicationContext;
    }

    /**
     * 通过类类型获取Spring Bean实例。
     * <p>
     * 该方法利用Spring框架的ApplicationContext来动态获取指定类型的Bean实例。它提供了一种泛型的方式来获取Bean，
     * 不需要知道具体的Bean名称，只需要知道Bean的类类型。
     * 这种方式在需要根据条件动态决定获取哪个Bean，或者在不希望代码与特定Bean名称耦合的情况下非常有用。
     *
     * @param clazz 需要获取的Bean的类类型。这个参数指定了我们要获取的Bean的类型，方法会返回这个类型的一个实例。
     * @return 返回指定类型的Bean实例。如果找不到对应的Bean，或者存在多个相同类型的Bean，则可能会抛出异常。
     * @since 1.0
     */
    public static <T> T getBean(Class<T> clazz) {
        // 通过SpringContextUtils的静态方法获取指定类型的Bean实例。
        return SpringContextUtils.applicationContext.getBean(clazz);
    }

    /**
     * 通过类和名称获取Spring Bean。
     * <p>
     * 该方法提供了一种泛型方式来从Spring的应用上下文中获取指定名称和类型的Bean。使用此方法，
     * 不需要进行强制类型转换，因为它使用了泛型来确保类型安全。
     *
     * @param name  Bean的名称，在应用上下文中必须唯一。
     * @param clazz Bean所期望的类型。这个参数用来指定我们希望获取的Bean的类型，
     *              确保了在获取Bean时的类型安全。
     * @param <T>   Bean的泛型类型，用于编译时类型检查，避免运行时类型转换。
     * @return 返回与给定名称和类型匹配的Bean实例。如果找不到匹配的Bean，或者存在多个匹配的Bean，
     * 则会抛出相应的异常。
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        // 通过SpringContextUtils的应用上下文获取指定名称和类型的Bean
        return SpringContextUtils.applicationContext.getBean(name, clazz);
    }
}
