package com.github.platform.core.cache.infra.service.impl;

import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存实现
 * @author: yxkong
 * @date: 2023/3/27 5:01 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class RedisCacheServiceImpl implements ICacheService {

    private static Long LOCK_SUC = 1L;
    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    public RedisCacheServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean getLock(String lockKey, String lockId, long expireTime) {
        // 当前时间
        try {
            String lockLuaScript = getLockLuaScript();
            if (log.isTraceEnabled()){
                log.trace("开始获取分布式锁-key[{}]", lockKey);
            }
            int count = 0;
            List<String> lockKeyList = Collections.singletonList(lockKey);
            if (redisLuaScriptExecute(lockKey, lockId, expireTime, lockLuaScript, count, lockKeyList)){
                return true;
            }
        } catch (Exception e) {
            log.error("尝试获取分布式锁-key[{}]异常", lockKey,e);
        }
        return false;
    }

    @Override
    public String getLock(String lockKey, long expireTime) {
        // 当前时间
        try {
            String lockId = StringUtils.uuidRmLine();
            String lockLuaScript = getLockLuaScript();
            if (log.isTraceEnabled()){
                log.trace("开始获取分布式锁-key[{}]", lockKey);
            }
            int count = 0;
            List<String> lockKeyList = Collections.singletonList(lockKey);
            if (redisLuaScriptExecute(lockKey, lockId, expireTime, lockLuaScript, count, lockKeyList)){
                return lockId;
            }
        } catch (Exception e) {
            log.error("尝试获取分布式锁-key[{}]异常", lockKey,e);
        }
        return null;
    }

    private String getLockLuaScript(){
        StringBuffer luaScript = new StringBuffer();
        luaScript.append("if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then  ")
                .append("   return redis.call('expire',KEYS[1],ARGV[2])  ")
                .append(" else ")
                .append("   return 0")
                .append(" end")
        ;
        return luaScript.toString();
    }
    @Override
    public boolean getLock(String lockKey, String lockId, long expireTime, long waitTimeout) {
        // 当前时间
        long nanoTime = System.nanoTime();
        try {
            String lockLuaScript = getLockLuaScript();
            if (log.isTraceEnabled()){
                log.trace("开始获取分布式锁-key[{}]", lockKey);
            }
            int count = 0;
            List<String> lockKeyList = Collections.singletonList(lockKey);
            do {
                if (redisLuaScriptExecute(lockKey, lockId, expireTime, lockLuaScript, count, lockKeyList)){
                    return true;
                }
                //休眠5毫秒
                Thread.sleep(5L);
                count++;
            } while ((System.nanoTime() - nanoTime) < TimeUnit.MILLISECONDS.toNanos(waitTimeout));
        } catch (Exception e) {
            log.error("尝试获取分布式锁-key[{}]异常", lockKey,e);
        }
        return false;
    }

    private boolean redisLuaScriptExecute(String lockKey, String lockId, long expireTime, String lockLuaScript, int count, List<String> lockKeyList) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(lockLuaScript, Long.class);
        if (log.isTraceEnabled()) {
            log.trace("尝试获取分布式锁-key[{}]requestId[{}]count[{}]", lockKey, lockId, count);
        }
        Object result = redisTemplate.execute(redisScript, lockKeyList, lockId, String.valueOf(expireTime));
        if (log.isTraceEnabled()){
            log.trace("获取分布式锁结果-key[{}],result[{}]", lockKey,result);
        }
        if (LOCK_SUC.equals(result)) {

            return true;
        }
        return false;
    }

    @Override
    public boolean releaseLock(String lockKey, String lockId) {
        if (StringUtils.isEmpty(lockId)){
            return false;
        }
        StringBuffer luaScript = new StringBuffer();
        luaScript.append("if redis.call('get',KEYS[1]) == ARGV[1] then ")
                .append("   return redis.call('del',KEYS[1])  ")
                .append(" else ")
                .append("   return 0")
                .append(" end")
        ;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript.toString(), Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockId);
        if (LOCK_SUC.equals(result)) {
            if (log.isTraceEnabled()){
                log.trace("释放锁成功key[{}]value[{}]", lockKey, lockId);
            }
            return true;
        }
        return false;
    }

    @Override
    public Long incrByAtom(String key, int expireTime) {
        StringBuffer luaScript = new StringBuffer();
        luaScript.append("local count = redis.call('incrby', KEYS[1],1) ")
                .append(" redis.call('expire', KEYS[1],ARGV[1]) ")
                .append(" return count ")
        ;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript.toString(), Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), String.valueOf(expireTime));
        if (Objects.isNull(result)){
            return null;
        }
        return Long.valueOf(result.toString());
    }

    @Override
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("expire key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("is error:", e);
            return false;
        }
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtil.arrayToList(keys));
            }
        }
    }

    @Override
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> multiGet(Collection<String> keys) {
        if (CollectionUtil.isEmpty(keys)){
            return new ArrayList<>();
        }
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("set key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean set(String key, String value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("set key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean set(String key, String value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
            return true;
        } catch (Exception e) {
            log.error("set key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public long incr(String key) {
        return incr(key, 1L);
    }

    @Override
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public long decr(String key) {
        return decr(key, -1L);
    }

    @Override
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    @Override
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }
    @Override
    public Map<Object, Object> hGetEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public List<Object> hGetValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    @Override
    public boolean hmSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("hmSet key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean hmSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hmSet key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("hSet key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hSet key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public void hPutAll(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key,map);
    }

    @Override
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    @Override
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    @Override
    public double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    @Override
    public double hDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    @Override
    public Set<String> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("sGet key:{} is error:",key, e);
            return null;
        }
    }

    @Override
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("sHasKey key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public long sSet(String key, String... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("sGet key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public long sSetAndTime(String key, long time, String... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("sSetAndTime key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("sGetSetSize key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("setRemove key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public List<String> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("lGet key:{} is error:",key, e);
            return null;
        }
    }

    @Override
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("lGetListSize key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("lGetIndex key:{} is error:",key, e);
            return null;
        }
    }

    @Override
    public boolean lSet(String key, String value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("lSet key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean lSet(String key, String value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("lSet key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean lSet(String key, List<String> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("lSet key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean lSet(String key, List<String> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("lSet key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public boolean lUpdateIndex(String key, long index, String value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("lUpdateIndex key:{} is error:",key, e);
            return false;
        }
    }

    @Override
    public long lRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("lRemove key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public Boolean zAdd(String key, String value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key,value,score);
        } catch (Exception e) {
            log.error("zAdd key:{} is error:",key, e);
            return false;
        }
    }
    @Override
    public long zCard(String key) {
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            log.error("zCard key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public long zCount(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().count(key,min,max);
        } catch (Exception e) {
            log.error("zCount key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public Double zScore(String key, String value) {
        try {
            return redisTemplate.opsForZSet().score(key,value);
        } catch (Exception e) {
            log.error("zScore key:{} is error:",key, e);
            return null;
        }
    }

    @Override
    public long zRem(String key, String... values) {
        try {
            return redisTemplate.opsForZSet().remove(key,values);
        } catch (Exception e) {
            log.error("zRem key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public long zRem(String key, Collection<String> values) {
        try {
            if (CollectionUtil.isEmpty(values)){
                return 0;
            }
            String[] tasksArray = values.toArray(new String[0]);
            return redisTemplate.opsForZSet().remove(key,tasksArray);
        } catch (Exception e) {
            log.error("zRem key:{} is error:",key, e);
            return 0;
        }
    }

    @Override
    public long zRemRangeByScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
        } catch (Exception e) {
            log.error("zRemRangeByScore key:{} min:{} max:{} is error:",min,max,key, e);
            return 0;
        }
    }

    @Override
    public Set<String> zRangeByScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,min,max);
        } catch (Exception e) {
            log.error("zRangeByScore key:{} min:{} max:{} is error:",key,min,max, e);
            return null;
        }
    }

    @Override
    public Set<String> zRangeByScore(String key, double min, double max, int offset, int count) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,min,max,offset,count);
        } catch (Exception e) {
            log.error("zRangeByScore key:{} min:{} max:{} is error:",key,min,max, e);
            return null;
        }
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key,min,max,offset,count);
        } catch (Exception e) {
            log.error("zRangeByScore key:{} min:{} max:{} is error:",key,min,max, e);
            return null;
        }
    }

    @Override
    public void publish(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
