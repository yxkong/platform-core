package com.github.platform.core.cache.infra.utils;

import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.cache.infra.constant.BaseSequence;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.standard.exception.CommonException;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.TimeoutUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 序号生成
 * @author yxkong
 * @create 2023/2/16 下午2:10
 * @desc SequenceUtil
 */
@Slf4j
public class SequenceUtil {

    /**
     * 返回序列号
     * @param se
     * @return
     */
    public static String nextSequenceNum(BaseSequence se) {
        return nextSequenceNum(se.getPrefix(),se.getFormat(),se.getCacheInit(),se.getDbInit());
    }

    /**
     * 添加重载方法，防止过度依赖SequenceEnum 枚举
     * @param prefix 缓存前缀
     * @param format 建议到天，按天递增，缓存key，只需要缓存1天即可
     * @param cacheInit 缓存初始值，1天内最夺的序列号位数
     * @param dbInit 数据库初始值，redis故障的时候，切换到数据库
     * @return 序列号 prefix+format + 自增序列
     */
    public static String nextSequenceNum(String prefix,String format,String cacheInit,Long dbInit) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(LocalDateTimeUtil.dateTime(format));
        int len = cacheInit.length();
        String sequenceName = sb.toString();
        ICacheService cacheService = ApplicationContextHolder.getBean(ICacheService.class);
        String redisKey = getKey(sequenceName);
        try {
            Long num = cacheService.incr(redisKey) ;
            if (Objects.nonNull(dbInit)){
                if (num > dbInit) {
                    throw new CommonException("-1", "序列号" + sequenceName + "溢出:" + num);
                }
            }
            cacheService.expire(redisKey, TimeoutUtils.toSeconds(1, TimeUnit.DAYS));
            String suffix = String.format("%0" + len + "d", num);
            return sb.append(suffix).toString();
        } catch (Exception e){
            log.error("序列号" + sequenceName + "获取失败",e);
            // TODO 如果redis中获取不到，需要从数据库中获取并自增
//            Long dbSequenceNum = null;
//            return sb.append(dbSequenceNum).toString();

        }
        return null;
    }

    /**
     * 获取redis的key
     * @param sequenceName
     * @return
     */
    private static String getKey(String sequenceName){
        CacheProperties properties = ApplicationContextHolder.getBean(CacheProperties.class);
        return properties.getSequence()+sequenceName;
    }
}
