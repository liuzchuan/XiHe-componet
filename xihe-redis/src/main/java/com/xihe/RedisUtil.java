package com.xihe;

import cn.hutool.extra.spring.SpringUtil;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

/**
 * redis 操作工具类  需要在RedisTemplate被spring容器管理后方可调用
 *
 * @author lzc
 * @since 2021-10-12 11:39
 */
@SuppressWarnings("unchecked")
public class RedisUtil {

    private RedisUtil() {
        throw new UnsupportedOperationException("con not be instantiated");
    }

    private static final RedisTemplate<String, Object> REDIS_TEMPLATE = SpringUtil.getBean("redisTemplate");


    /**
     * 设置有效时间
     *
     * @param key     redis 键
     * @param timeOut 超时间时间 （时间单位：分）
     * @return true=>设置成功；false=>设置失败
     */
    public static boolean expire(final String key, final long timeOut) {
        return expire(key, timeOut, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key      redis 键
     * @param timeOut  超时间时间
     * @param timeUnit 时间单位
     * @return true=>设置成功；false=>设置失败
     */
    public static boolean expire(final String key, final long timeOut, final TimeUnit timeUnit) {
        return Boolean.TRUE.equals(REDIS_TEMPLATE.expire(key, timeOut, timeUnit));
    }

    /**
     * 删除单个键
     *
     * @param key redis 键
     * @return true=>删除成功；false=>删除失败
     */
    public static boolean deleted(final String key) {
        return Boolean.TRUE.equals(REDIS_TEMPLATE.delete(key));
    }

    /**
     * 键 查询所有匹配的key
     *
     * @param keys 键
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> keys(final String keys) {
        return REDIS_TEMPLATE.keys(keys);
    }

    /**
     * 设置过期时间
     *
     * @param key        关键
     * @param expireTime 到期时间
     * @return long
     */
    public static long inr(String key, long expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, REDIS_TEMPLATE.getConnectionFactory());
        counter.expire(expireTime, TimeUnit.SECONDS);
        return counter.incrementAndGet();
    }

    /**
     * 删除键 模糊删除所有的keys
     *
     * @param keyPatten 关键彭定康
     * @return {@link Long}
     */
    public static Long deleteKeys(String keyPatten) {
        return REDIS_TEMPLATE.delete(keys(keyPatten));
    }

    /**
     * 删除单个键
     *
     * @param keys redis 键
     * @return 删除成功的个数
     */
    public static long deleted(final Collection<String> keys) {
        Long delete = REDIS_TEMPLATE.delete(keys);
        return Optional.ofNullable(delete).orElse(0L);
    }

    /**
     * 存入普通对象
     *
     * @param key   redis 键
     * @param value 值
     */
    public static void set(final String key, final Object value) {
        REDIS_TEMPLATE.opsForValue().set(key, value);
    }

    /**
     * 存入普通对象
     *
     * @param key     redis 键
     * @param value   值
     * @param timeOut 超时时间
     */
    public static void set(final String key, final Object value, final long timeOut) {
        set(key, value, timeOut, TimeUnit.SECONDS);
    }

    /**
     * 存入普通对象
     *
     * @param key      redis 键
     * @param value    值
     * @param timeOut  超时时间
     * @param timeUnit 超时时间单位
     */
    public static void set(final String key, final Object value, final long timeOut, final TimeUnit timeUnit) {
        REDIS_TEMPLATE.opsForValue().set(key, value, timeOut, timeUnit);
    }

    public static <T> T get(final String key) {
        return (T) REDIS_TEMPLATE.opsForValue().get(key);
    }

    public static String getToStr(final String key) {
        StringRedisTemplate template = SpringUtil.getBean(StringRedisTemplate.class);
        return template.opsForValue().get(key);
    }

    /**
     * 存入hash数据
     *
     * @param key     redis 键
     * @param hashKey hash键
     * @param value   值
     */
    public static void hashPut(final String key, final String hashKey, final Object value) {
        REDIS_TEMPLATE.opsForHash().put(key, hashKey, value);
    }

    /**
     * 存入hash数据
     *
     * @param key    redis 键
     * @param values hash键值对
     */
    public static <T> void hashPutAll(final String key, final Map<String, T> values) {
        REDIS_TEMPLATE.opsForHash().putAll(key, values);
    }

    /**
     * 获取hash中的数据
     *
     * @param key     redis 键
     * @param hashKey hash键
     */
    public static <T> T hashGet(final String key, final String hashKey) {
        return (T) REDIS_TEMPLATE.opsForHash().get(key, hashKey);
    }

    /**
     * 获取hash中的所有数据
     *
     * @param key redis 键
     */
    public static <T> T hashGetValues(final String key) {
        return (T) REDIS_TEMPLATE.opsForHash().values(key);
    }

    /**
     * 获取hash中的数据
     *
     * @param key      redis 键
     * @param hashKeys hash键
     */
    public static <T> T hashMultiGet(final String key, final Collection<Object> hashKeys) {
        return (T) REDIS_TEMPLATE.opsForHash().multiGet(key, hashKeys);
    }

    /**
     * 删除hash中的数据
     *
     * @param key   redis 键
     * @param value hash值
     * @return 删除成功的个数
     */
    public static long hashDeleted(final String key, final Object... value) {
        return REDIS_TEMPLATE.opsForHash().delete(key, value);
    }

    /**
     * 往set中存入数据
     *
     * @param key   redis 键
     * @param value 值
     * @return 存入成功的个数
     */
    public static long sAdd(final String key, final Object... value) {
        Long add = REDIS_TEMPLATE.opsForSet().add(key, value);
        return Optional.ofNullable(add).orElse(0L);
    }

    /**
     * 删除set中的数据
     *
     * @param key   redis 键
     * @param value 值
     * @return 删除成功的个数
     */
    public static long sRemove(final String key, final Object... value) {
        Long add = REDIS_TEMPLATE.opsForSet().remove(key, value);
        return Optional.ofNullable(add).orElse(0L);
    }

    /**
     * 往list中存入数据（向右最追加数据）
     *
     * @param key   redis 键
     * @param value 值
     * @return 存入成功的个数
     */
    public static long lPush(final String key, final Object value) {
        Long add = REDIS_TEMPLATE.opsForList().rightPush(key, value);
        return Optional.ofNullable(add).orElse(0L);
    }

    /**
     * 往list中存入数据（向右最追加数据）
     *
     * @param key    redis 键
     * @param values 值
     * @return 存入成功的个数
     */
    public static long lPushAll(final String key, final Collection<Object> values) {
        Long add = REDIS_TEMPLATE.opsForList().rightPushAll(key, values);
        return Optional.ofNullable(add).orElse(0L);
    }

    /**
     * 往list中存入数据（向右最追加数据）
     *
     * @param key   redis 键
     * @param value 值
     * @return 存入成功的个数
     */
    public static <T> long lPushAll(final String key, final T... value) {
        Long add = REDIS_TEMPLATE.opsForList().rightPushAll(key, value);
        return Optional.ofNullable(add).orElse(0L);
    }

    /**
     * 获取 list中的数据
     *
     * @param key   redis 键
     * @param start 开始位置
     * @param end   结束位置（start=0,end=-1 表示获取全部元素）
     * @param <T>   返回值泛型
     * @return list中的数据
     */
    public static <T> List<T> lGet(final String key, final int start, final int end) {
        return (List<T>) REDIS_TEMPLATE.opsForList().range(key, start, end);
    }

    /**
     * 删除 list中的数据
     *
     * @param key   redis 键
     * @param start 开始位置
     * @param end   结束位置（start=0,end=-1 表示获取全部元素）
     * @return 删除的个数
     */
    public static long lRemove(final String key, final int start, final int end) {
        Long add = REDIS_TEMPLATE.opsForList().remove(key, start, end);
        return Optional.ofNullable(add).orElse(0L);
    }

    /**
     * 添加热点 存储单个热点数据
     *
     * @param key   关键
     * @param value 价值
     * @param score 分数
     */
    public static void addHotSpot(final String key, final Object value, final Double score) {
        REDIS_TEMPLATE.opsForZSet().add(key, value, score);
    }

    /**
     * 添加热点 批量存储热点数据
     *
     * @param key   关键
     * @param value 价值
     */
    public static void addHotSpot(final String key, final Set<ZSetOperations.TypedTuple<Object>> value) {
        REDIS_TEMPLATE.opsForZSet().add(key, value);
    }

    /**
     * 反向排名 获取单个热点数据排行
     *
     * @param key   关键
     * @param value 价值
     * @return {@link Long}
     */
    public static Long reverseRank(final String key, final Object value) {
        return REDIS_TEMPLATE.opsForZSet().reverseRank(key, value);
    }

    /**
     * 反向范围 获取指定排名前多少数值的热点数据
     *
     * @param key   关键
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> reverseRange(final String key, final Long start, final Long end) {
        return (Set<T>) REDIS_TEMPLATE.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 反向分数范围 获取指定排名前多少数值的热点数据以及分值
     *
     * @param key   关键
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link ZSetOperations.TypedTuple}<{@link Object}>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(final String key, final Long start,
        final Long end) {
        return REDIS_TEMPLATE.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 分数 获取单个热点数据分值
     *
     * @param key   关键
     * @param value 价值
     * @return {@link Double}
     */
    public static Double score(final String key, final Object value) {
        return REDIS_TEMPLATE.opsForZSet().score(key, value);
    }

    /**
     * 分数 获取分值区间的数据数量
     *
     * @param key        关键
     * @param startScore 开始分
     * @param endScore   最终得分
     * @return {@link Long}
     */
    public static Long score(final String key, final Double startScore, final Double endScore) {
        return REDIS_TEMPLATE.opsForZSet().count(key, startScore, endScore);
    }
}
