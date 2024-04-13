package com.wan.utils;

import com.wan.constant.RedisConstant;
import com.wan.exception.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RedisUtils {


    /**
     * 将实体类所有字段及其值存入Redis Hash。（没有过期时间）
     *
     * @param entity        实体对象，其类型为泛型。
     * @param redisTemplate Redis模板，用于执行Redis操作。
     * @param key           存储数据的Redis Hash键。
     * @throws RuntimeException 如果访问字段时发生异常。
     */
    public static <T> void redisHashPut(RedisTemplate<String, Object> redisTemplate,
                                        String key, T entity) {
        // 获取实体类的Class对象
        Class<?> aClass = entity.getClass();
        // 遍历类的所有声明字段
        for (Field field : aClass.getDeclaredFields()) {
            // 设置字段可访问，以读取其值
            field.setAccessible(true);
            try {
                // 将字段名和字段值存入Redis Hash
                redisTemplate.opsForHash().put(key, field.getName(), field.get(entity));
            } catch (IllegalAccessException e) {
                // 访问字段异常，抛出运行时异常
                clearRedisCache(redisTemplate, key);
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                clearRedisCache(redisTemplate, key);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 将实体对象的属性作为字段名和属性值存入Redis Hash中，并设置过期时间。
     *
     * @param redisTemplate Redis模板对象，用于操作Redis。
     * @param key           Redis中键的名称。
     * @param entity        要存入Redis的实体对象。
     * @param expireTime    过期时间。
     * @param timeUnit      时间单位。
     * @throws RuntimeException 当访问字段发生异常时抛出。
     */
    public static <T> void redisHashPut(RedisTemplate<String, Object> redisTemplate,
                                        String key, T entity,
                                        Long expireTime, TimeUnit timeUnit) {
        // 获取实体类的Class对象
        Class<?> aClass = entity.getClass();
        // 遍历类的所有声明字段
        for (Field field : aClass.getDeclaredFields()) {
            // 设置字段可访问，以读取其值
            field.setAccessible(true);
            try {
                // 将字段名和字段值存入Redis Hash
                redisTemplate.opsForHash().put(key, field.getName(), field.get(entity));
            } catch (IllegalAccessException e) {
                // 访问字段异常，清理缓存并抛出运行时异常
                clearRedisCache(redisTemplate, key);
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                // 异常处理，清理缓存并抛出运行时异常
                clearRedisCache(redisTemplate, key);
                throw new RuntimeException(e);
            }
        }
        // 设置过期时间
        redisTemplate.expire(key, expireTime, timeUnit);
    }


    /**
     * 从Redis获取哈希表的某个键的所有值。
     *
     * @param redisTemplate Redis模板，用于操作Redis数据。
     * @param key           Redis中哈希表的键。
     * @param cls           要转换成的Java类类型。
     * @return 返回哈希表中所有的值，如果键不存在，则返回空列表。
     * @throws IllegalArgumentException 如果键为空，抛出此异常。
     * @throws RuntimeException         如果类实例化失败，抛出此异常。
     */
    public static <T> T redisGetHashValues(RedisTemplate<String, Object> redisTemplate,
                                           String key,
                                           Class<T> cls) {
        // 参数校验
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("键为空");
        }

        if (cls == null) {
            throw new IllegalArgumentException("类类型不能为空");
        }
        // 从Redis获取指定键的所有值
        List<Object> values = redisTemplate.opsForHash().values(key);
        // 如果values是空数组， 即缓存中没有值，则返回null
        if (values.isEmpty()) {
            return null;
        }
        try {
            // 通过类字节码文件获取对象的无参构造函数创建对象
            T entity = cls.getDeclaredConstructor().newInstance();

            int index = values.size() - 1;
            // 遍历类的字段
            for (Field field : cls.getDeclaredFields()) {
                // 设置所有字段可访问
                field.setAccessible(true);
                // 将字段的值设置到新实例中
                field.set(entity, values.get(index--));
            }
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RedisException("实例化或访问字段失败");
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RedisException("实例化对象失败");
        }

    }

    /**
     * 将给定的实体对象存储到Redis中，以字符串键的形式。
     *
     * @param redisTemplate Redis模板，用于操作Redis数据库。
     * @param key           键，用于在Redis中标识存储的数据。
     * @param entity        要存储的实体对象。
     * @throws IllegalArgumentException 如果键为空或者实例为空，则抛出此异常。
     */
    public static <T> void redisStringSet(RedisTemplate<String, Object> redisTemplate,
                                          String key, T entity) {
        // 参数校验
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("键为空");
        }

        if (entity == null) {
            throw new IllegalArgumentException("实例不能为空");
        }

        // 将实体对象设置到Redis的指定键下
        redisTemplate.opsForValue().set(key, entity);
    }

    /**
     * 将给定的实体对象存储到Redis中，指定键和过期时间。
     *
     * @param redisTemplate Redis模板，用于操作Redis。
     * @param key Redis中键的名称。
     * @param entity 要存储在Redis中的实体对象。
     * @param expire 过期时间。
     * @param timeUnit 过期时间的单位。
     * @throws IllegalArgumentException 如果键为空或者实体对象为空，则抛出此异常。
     */
    public static <T> void redisStringSet(RedisTemplate<String, Object> redisTemplate,
                                          String key, T entity, Long expire, TimeUnit timeUnit) {
        // 参数校验
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("键为空");
        }

        if (entity == null) {
            throw new IllegalArgumentException("实例不能为空");
        }

        // 将实体对象设置到Redis的指定键下
        redisTemplate.opsForValue().set(key, entity);

        // 为键设置过期时间
        redisTemplate.expire(key, expire, timeUnit);
    }

    public static <T> T redisStringGet(RedisTemplate<String, Object> redisTemplate,
                                       String key, Class<T> cls) {
        // 参数校验
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("键为空");
        }

        if (cls == null) {
            throw new IllegalArgumentException("类类型不能为空");
        }

        return (T) redisTemplate.opsForValue().get(key);

    }

    /**
     * 清除Redis缓存。
     *
     * @param redisTemplate Redis模板，用于执行Redis操作。
     * @param keys          需要删除的缓存的键。
     *                      该方法通过传入的键值，从Redis缓存中删除对应的数据。
     */
    public static void clearRedisCache(RedisTemplate<String, Object> redisTemplate, String... keys) {
        if (keys.length == 0) {
            // 如果没有要删除的键，直接返回，避免执行无效操作
            return;
        }
        // 删除指定键的缓存
        redisTemplate.delete(Arrays.asList(keys));
    }


}
