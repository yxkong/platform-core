package com.github.platform.core.cache.infra.utils;

import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.cache.infra.constant.SequenceEnum;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.standard.exception.CommonException;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import org.springframework.data.redis.core.TimeoutUtils;

import java.util.concurrent.TimeUnit;

/**
 * 序号生成
 * @author wangxiaozhou
 * @create 2023/2/16 下午2:10
 * @desc SequenceUtil
 */
public class SequenceUtil {

    public static String nextSequenceNum(SequenceEnum se) {
        return nextSequenceNum(se.getPrefix(),se.getFormat(),se.getCacheInit(),se.getDbInit());
    }

    /**
     * 添加重载方法，防止过度依赖SequenceEnum 枚举
     * @param prefix
     * @param format
     * @param cacheInit
     * @param dbInit
     * @return
     */
    public static String nextSequenceNum(String prefix,String format,String cacheInit,String dbInit) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(LocalDateTimeUtil.dateTime(format));
        int len = cacheInit.length();
        String sequenceName = sb.toString();
        ICacheService cacheService = ApplicationContextHolder.getBean(ICacheService.class);
        Long num = cacheService.incr(getKey(sequenceName)) - 1;
        if (num > Long.valueOf(dbInit.replaceAll("0", "9"))) {
            throw new CommonException("-1", "序列号" + sequenceName + "溢出:" + num);
        }
        cacheService.expire(getKey(sequenceName), TimeoutUtils.toSeconds(1, TimeUnit.DAYS));
        String suffix = String.format("%0" + len + "d", num);
        return sb.append(suffix).toString();
    }

    private static String getKey(String sequenceName){
        CacheProperties properties = ApplicationContextHolder.getBean(CacheProperties.class);
        return properties.getSequence()+sequenceName;
    }
}
