package com.wan.utils;

import com.wan.constant.RedisConstant;
import com.wan.exception.RedisException;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
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
        // 得到字段名和值
        Map<Object, Object> fieldValueMap = redisTemplate.opsForHash().entries(key);
        // 如果values是空数组， 即缓存中没有值，则返回null
        if (values.isEmpty()) {
            return null;
        }
        try {

            // 通过类字节码文件获取对象的无参构造函数创建对象
            T entity = cls.getDeclaredConstructor().newInstance();

            // 遍历
            for (Map.Entry<Object, Object> entry : fieldValueMap.entrySet()) {
                // 得到字段名
                String fileName = (String) entry.getKey();
                // 根据字段名查找字段
                Field field = cls.getDeclaredField(fileName);
                // 设置可以访问
                field.setAccessible(true);
                if ("total".equals(fileName)) {
                    // 如果是total
                    Integer total = (Integer) entry.getValue();
                    // 添加到实体中
                    if (total == null) {
                        total = 0;
                    }
                    field.set(entity, total.longValue());
                } else {
                    // 添加到实体中
                    field.set(entity, entry.getValue());
                }

            }
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RedisException("实例化或访问字段失败");
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RedisException("实例化对象失败");
        } catch (NoSuchFieldException e) {
            throw new RedisException("没有找到字段");
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
     * @param key           Redis中键的名称。
     * @param entity        要存储在Redis中的实体对象。
     * @param expire        过期时间。
     * @param timeUnit      过期时间的单位。
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
        if (redisTemplate == null) {
            // 如果Redis模板为空，则直接返回
            return;
        }
        if (keys.length == 0) {
            // 如果没有要删除的键，直接返回，避免执行无效操作
            return;
        }
        // 删除指定键的缓存
        redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 根据模式清除 Redis 缓存
     *
     * 本方法通过指定的模式字符串来匹配 Redis 中的键，进而删除所有匹配到的键。请谨慎使用，特别是当模式字符串为 "*" 时，会删除所有键。
     *
     * @param redisTemplate Redis 模板，用于执行 Redis 操作。
     * @param pattern       模式字符串，用于匹配需要删除的键。支持 Redis 的键匹配模式。
     */
    public static void clearRedisCacheByPattern(RedisTemplate<String, Object> redisTemplate, String pattern) {
        // 检查模式字符串是否为空或仅为空格
        if (pattern == null || pattern.trim().isEmpty()) {
            // 显式提醒关于空模式的风险
            return;
        }

        try {
            ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).build();

            // 使用SCAN命令获取匹配模式的键的游标
            Cursor<String> cursor = redisTemplate.scan(scanOptions);

            // 遍历匹配的键，逐个删除
            while (cursor.hasNext()) {
                String key = cursor.next();
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            System.err.println("清除 Redis 缓存时发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }



}
