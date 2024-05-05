package com.github.platform.core.cache.domain.constant;

import com.github.platform.core.standard.constant.SymbolConstant;
import org.springframework.util.Assert;

/**
 * 默认缓存key,
 * ps: 尽可能压缩key的长度，减少redis的内存空间使用
 * @author: yxkong
 * @date: 2023/4/20 5:11 PM
 * @version: 1.0
 */
public interface CacheConstant {
    /**
     *默认有效期60分钟
     */
    Long defaultExpire = 60L;
    String cacheManager = "redisCacheManager";
    String apiTokenService = "apiTokenService";
    String sysTokenService = "sysTokenService";
    /**
     * 默认有效期1分钟
     */
    Long defaultOneMinExpire = 1L;
    /**配置默认缓存前置,  p:c:c 表示：platform:common:config*/
    String config = "p:c:c";
    /**字典配置默认缓存前缀 p:c:d: 表示：platform:common:*/
    String dict = "p:c:d:";
    /**防重校验默认缓存前缀,p:w:a 表示：platform:web:access:*/
    String repeatSubmit = "p:w:a:";
    /**序列号生成序号默认缓存前缀,p:s 表示：platform:sequence:*/
    String sequence = "p:s:";
    /**灰度缓存默认key,  p:gr 表示：platform:grayrule*/
    String grayRule = "p:gr";
    /**token缓存默认前缀,p:a:t 表示：platform:api:token:*/
    String apiToken = "p:a:t:";
    /**用户与token的映射默认前缀,p:a:u 表示，platform:api:user*/
    String apiUserTokenMapping = "p:a:u:";
    /**token缓存默认前缀,p:s:t 表示，platform:sys:token:*/
    String sysToken = "p:s:t:";
    /**用户与token的映射默认前缀,p:s:u 表示，platform:sys:user*/
    String sysUserTokenMapping = "p:s:u:";
    /**验证码缓存key前缀，p:c 表示：platform:captcha:*/
    String captcha = "p:c:" ;
    /**在线用户前缀 表示：p:ol:u表示 platform:online:users:*/
    String onlineUsers = "p:ol:u" ;
    /**分布式锁前缀 platform:distribute:lock */
    String distributeLock = "p:d:l:" ;
    String c10s = "c10s";
    String c30s = "c30s";
    String c1m = "c1m";
    String c2m = "c2m";
    String c3m = "c3m";
    String c5m = "c5m";
    String c10m = "c10m";
    String c15m = "c15m";
    String c30m = "c30m";
    String c1h = "c1h";
    String c2h = "c2h";
    String c8h = "c8h";
    String c12h = "c12h";
    String c1d = "c1d";
    String c2d = "c2d";
    String c7d = "c7d";
    String c14d = "c14d";
    String c15d = "c15d";
    String c30d = "c30d";
    /**分布式锁时间，60秒*/
    Long distributeLockTime_60 = 60L;
    /**分布式锁时间，30秒*/
    Long distributeLockTime_30 = 30L;
    /**分布式锁时间，10秒*/
    Long distributeLockTime_10 = 10L;

    /**
     * 获取后缀
     * @param values
     * @return
     */
    static String getDistributeKey(String ...values){
        Assert.notNull(values,"分布式锁业务属性不能为空！");
        return CacheConstant.distributeLock + String.join(SymbolConstant.colon, values);
    }
}
