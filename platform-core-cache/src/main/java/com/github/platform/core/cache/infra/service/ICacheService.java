package com.github.platform.core.cache.infra.service;

import com.github.platform.core.log.infra.annotation.CacheResolve;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存统一操作
 *
 * @author: yxkong
 * @date: 2023/3/27 4:50 PM
 * @version: 1.0
 */
public interface ICacheService {
    /**
     * 获取分布式锁：一次性
     * @param lockKey 锁key
     * @param lockId 锁id,自己加的锁，自己能释放
     * @param expireTime 单位秒|你认为此方法需要多少时间，设置一个最长时间，此时间必须大于需要调用锁的业务方法逻辑的最大时间，否则锁会冲突
     * @return 是否获取成功
     */
    boolean getLock(String lockKey, String lockId, long expireTime);
    /**
     * 获取分布式锁：一次性
     * @param lockKey 锁key
     * @param expireTime 单位秒|你认为此方法需要多少时间，设置一个最长时间，此时间必须大于需要调用锁的业务方法逻辑的最大时间，否则锁会冲突
     * @return 返回加锁id
     */
    String getLock(String lockKey, long expireTime);
    /**
     * 获取分布式锁，等待一定时间waitTimeout
     * @param lockKey     锁key
     * @param lockId    锁id,自己加的锁，自己能释放
     * @param expireTime  单位秒|你认为此方法需要多少时间，设置一个最长时间，此时间必须大于需要调用锁的业务方法逻辑的最大时间，否则锁会冲突
     * @param waitTimeout 单位毫秒|如果拿不到锁，那么休眠，然后反反复复重试，直到拿到锁为止
     *                    CacheService.getLock("lock", uuid, 10, 1500) 调用方法的业务逻辑最多需要1秒执行完成，1500毫秒一直等待（默认休眠500毫秒一次轮回）线程不执行，等到1500毫秒到了，才执行完整个方法
     * @return 是否获取成功
     */
    boolean getLock(String lockKey, String lockId, long expireTime, long waitTimeout);

    /**
     * 释放锁
     *
     * @param lockKey 锁
     * @param lockId   锁标识
     * @return 是否释放成功
     */
    boolean releaseLock(String lockKey, String lockId);

    /**
     * redis自增，原子操作
     * @param key
     * @param expireTime
     * @return
     */
    Long incrByAtom(String key,int expireTime);

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    @CacheResolve(command = "expire",key = "#key",expire = "#time")
    boolean expire(String key, long time);

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(String key);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    boolean hasKey(String key);
    /**
     * 删除缓存
     *
     * @param key 可以传一个值
     */
    @CacheResolve(command = "del",key = "#key")
    void del(String key);
    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     */
    @CacheResolve(command = "del",key = "#keys")
    void del(String... keys);

    /**
     * 批量删除
     *
     * @param keys
     * @author yxkong
     * @createDate 2018年2月1日
     * @updateDate
     */
    void delete(Collection<String> keys);

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 查询多个
     * @param key
     * @return
     */
    List<String> multiGet(Collection<String> key);

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    @CacheResolve(command = "set",key = "#key")
    boolean set(String key, String value);


    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    @CacheResolve(command = "set",key = "#key",expire = "#time")
    boolean set(String key, String value, long time);

    /**
     * 递增 1
     *
     * @param key 键
     * @return
     */
    long incr(String key);

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    long incr(String key, long delta);

    /**
     * 递减
     *
     * @param key 键
     * @return
     */
    long decr(String key);

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    long decr(String key, long delta);
    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    Object hget(String key, String item);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> hmget(String key);

    /**
     * 根据key获取hash所有的value
     * @param key
     * @return
     */
    List<Object> hValues(String key);

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    @CacheResolve(type = "hash",command = "hmSet",key = "#map")
    boolean hmSet(String key, Map<String, Object> map);

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    @CacheResolve(type = "hash",command = "hmSet",key = "#map")
    boolean hmSet(String key, Map<String, Object> map, long time);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    @CacheResolve(type = "hash",command = "hSet",key = "#key")
    boolean hSet(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    @CacheResolve(type = "hash",command = "hSet",key = "#key",expire = "#time")
    boolean hSet(String key, String item, Object value, long time) ;

    /**
     *
     * @param key
     * @param map
     */
    void hPutAll(String key, Map<String, String> map);
    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    void hDel(String key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    boolean hHasKey(String key, String item);

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    double hIncr(String key, String item, double by);
    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    double hDecr(String key, String item, double by);
    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    Set<String> sGet(String key);

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    boolean sHasKey(String key, Object value);

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @CacheResolve(type = "set",command = "sSet",key = "#key")
    long sSet(String key, String... values);

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @CacheResolve(type = "set",command = "sSet",key = "#key",expire = "#time")
    long sSetAndTime(String key, long time, String... values);

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    long sGetSetSize(String key);

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    long setRemove(String key, Object... values);

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    List<String> lGet(String key, long start, long end);

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    long lGetListSize(String key);

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    Object lGetIndex(String key, long index);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    boolean lSet(String key, String value);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    boolean lSet(String key, String value, long time);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    boolean lSet(String key, List<String> value);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    boolean lSet(String key, List<String> value, long time);

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    boolean lUpdateIndex(String key, long index, String value);

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    long lRemove(String key, long count, Object value);

    // ===============================zset=================================

    /**
     * 新增或修改数据
     * @param key  指定key
     * @param value value
     * @param score
     * @return
     */
    Boolean zAdd(String key, String value, double score);

    /**
     * 获取有序集合的成员数
     * @param key
     * @return
     */
    long zCard(String key);



    /**
     * 获取指定score区间的数量
     * @param key
     * @param min
     * @param max
     * @return
     */
    long zCount(String key, double min, double max);

    /**
     * 根据元素获取分值
     * @param key
     * @param value
     * @return
     */
    Double zScore(String key,String value);
    /**
     * 删除1个或多个value
     * @param key
     * @param values
     * @return
     */
    long zRem(String key, String... values);

    /**
     * 根据score删除数据
     * @param key
     * @param min
     * @param max
     * @return
     */
    long zRemRangeByScore(String key, double min, double max);

    /**
     * 获取指定区间的数据
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<String> zRangeByScore(String key, double min, double max);

    /**
     * 根据score分页
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    Set<String> zRangeByScore(String key, double min, double max, int offset, int count);
    /**
     * 根据key和区间范围，翻页
     * @param key
     * @param min
     * @param max
     * @param offset 游标，页面，从0 开始
     * @param count 每页多少条
     * @return
     */
    Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, int offset, int count);

}
