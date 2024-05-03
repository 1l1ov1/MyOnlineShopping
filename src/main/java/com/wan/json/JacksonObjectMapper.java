package com.wan.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * 对象映射器:基于jackson将Java对象转为json，或者将json转为Java对象
 * 将JSON解析为Java对象的过程称为 [从JSON反序列化Java对象]
 * 从Java对象生成JSON的过程称为 [序列化Java对象到JSON]
 */

public class JacksonObjectMapper extends ObjectMapper {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 构造一个自定义的JacksonObjectMapper实例，用于增强对Java 8日期时间类型的序列化与反序列化处理。
     *
     * @see com.fasterxml.jackson.databind.ObjectMapper
     */
    public JacksonObjectMapper() {
        super();


        // 配置忽略未知属性：在反序列化过程中，若遇到目标对象未声明的属性，不抛出异常，而是直接忽略。
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);


        // 反序列化时，对于目标对象不存在的属性提供兼容性处理：当目标对象中没有与JSON输入中的属性相对应的字段时，不触发异常，保持兼容性。

        this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        /*
         * 定义一个简单模块（JavaTimeModule），
         * 注册自定义的序列化器与反序列化器以支持Java 8日期时间类型（LocalDateTime, LocalDate, LocalTime）。
         * 使用预定义的日期时间格式字符串进行格式化。
         */
        JavaTimeModule javaTimeModule = (JavaTimeModule) new JavaTimeModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))

                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))

                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));

        /*
         * 注册自定义的SimpleModule到当前ObjectMapper实例中，使其生效。
         * 通过这种方式，可以添加更多的自定义序列化器和反序列化器以满足特定需求。
         */
        this.registerModule(javaTimeModule);
    }

}
