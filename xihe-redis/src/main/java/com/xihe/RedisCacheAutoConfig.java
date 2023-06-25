package com.xihe;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 缓存配置
 *
 * @author lzc
 * @since 2021-10-12 10:56
 */
@AutoConfiguration
public class RedisCacheAutoConfig {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Resource
    private RedisConnectionFactory connectionFactory;

    @Bean
    RedisMessageListenerContainer listenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        return listenerContainer;
    }

    /**
     * json序列化
     *
     * @return json序列化对象
     */
    @Bean
    public RedisSerializer<Object> jackson2JsonRedisSerializer() {
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        ObjectMapper mapper = new ObjectMapper();
//        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//
        //取消时间转换格式，默认是时间戳，同时需要设置要表现的时间格式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyy-MM-dd HH:mm:ss"));

        //默认序列化没有实现，反序列化有实现
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(
            DATE_TIME_FORMATTER));// javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_FORMATTER));// javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TIME_FORMATTER));

        mapper.registerModule(javaTimeModule);
        //设置时区
        mapper.setTimeZone(TimeZone.getDefault());
        //设置格式化输出// MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        //设置格式化输出
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY);
        return new Jackson2JsonRedisSerializer<>(mapper, Object.class);
    }

    /**
     * 配置自定义redisTemplate
     *
     * @return {@link RedisTemplate}<{@link String}, {@link Object}>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setValueSerializer(jackson2JsonRedisSerializer());
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setStringSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        //开启事务
//        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
