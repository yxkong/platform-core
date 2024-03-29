package com.github.platform.core.cache.domain.constant;

import lombok.Getter;

/**
 * 缓存模块
 *
 * @author: yxkong
 * @date: 2024/3/4 10:37
 * @version: 1.0
 */
@Getter
public enum CacheNameEnum {

    C10S(CacheConstant.c10s,10l,"缓存10秒"),
    C30S(CacheConstant.c30s,30l,"缓存30秒"),
    C1M(CacheConstant.c1m,60l,"缓存1分钟"),
    C2M(CacheConstant.c2m,2*60L,"缓存2分钟"),
    C3M(CacheConstant.c3m,3*60L,"缓存3分钟"),
    C5M(CacheConstant.c5m,5*60L,"缓存5分钟"),
    C10M(CacheConstant.c10m,10*60L,"缓存10分钟"),
    C15M(CacheConstant.c15m,15*60L,"缓存15分钟"),
    C30M(CacheConstant.c30m,30*60L,"缓存30分钟"),
    C1H(CacheConstant.c1h,60*60L,"缓存1小时"),
    C2H(CacheConstant.c2h,2*60*60L,"缓存2小时"),
    C8H(CacheConstant.c8h,8*60*60L,"缓存8小时"),
    C12H(CacheConstant.c12h,12*60*60L,"缓存12小时"),
    C1D(CacheConstant.c1d,24*60*60L,"缓存1天"),
    C2D(CacheConstant.c2d,2*24*60*60L,"缓存2天"),
    C7D(CacheConstant.c7d,7*24*60*60L,"缓存7天"),
    C14D(CacheConstant.c14d,14*24*60*60L,"缓存14天"),
    C15D(CacheConstant.c15d,15*24*60*60L,"缓存15天"),
    C30D(CacheConstant.c30d,30*24*60*60L,"缓存30天");
    private String cacheName;
    private long expireTime;
    private String desc;

    CacheNameEnum(String cacheName, long expireTime, String desc) {
        this.cacheName = cacheName;
        this.expireTime = expireTime;
        this.desc = desc;
    }

    public static long getExpireTime(String cacheName){
        for (CacheNameEnum cacheNameEnum:CacheNameEnum.values()){
            if (cacheNameEnum.getCacheName().equals(cacheName)){
                return cacheNameEnum.getExpireTime();
            }
        }
        return CacheNameEnum.C30M.getExpireTime();
    }


}
