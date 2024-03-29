package com.github.platform.core.auth.service.impl;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.constants.AuthTypeEnum;
import com.github.platform.core.auth.service.ILoginTokenService;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.TimeoutUtils;

import java.util.concurrent.TimeUnit;

/**
 * 登录登出缓存操作
 * @author: yxkong
 * @date: 2023/4/20 5:28 PM
 * @version: 1.0
 */
@Slf4j
public class DefaultLoginTokenServiceImpl extends DefaultTokenServiceImpl implements ILoginTokenService {
    public DefaultLoginTokenServiceImpl(AuthProperties authProperties, ICacheService cacheService) {
        super(authProperties, cacheService);
    }

    @Override
    public boolean cacheUserInfo(AuthTypeEnum authType,String token, String loginName,String loginInfo) {
        //缓存用户信息 token:userInfo
        String cacheKey = this.getTokenKey(authType,token);
        long seconds = TimeoutUtils.toSeconds(this.getExpire(authType), TimeUnit.MINUTES);
        cacheService.set(cacheKey, loginInfo, seconds);
        //缓存用户名和token关系 loginName:token
        cacheService.set( this.getMappingKey(authType,loginName),token, seconds);
        return true;
    }

    @Override
    public void delUserInfoCache(AuthTypeEnum authType, String loginName, String token) {
        //取出token后 删除用户名和token关系 loginName:token
        String mappingKey = getMappingKey(authType, loginName);
        if (StringUtils.isEmpty(token)){
            token = (String)cacheService.get(mappingKey);
        }
        //删除用户上次token的用户信息 token:userInfo
        String tokenKey = this.getTokenKey(authType, token);
        if (log.isDebugEnabled()){
            log.debug("清除 key: {} 与 {} 的缓存",mappingKey,tokenKey);
        }
        cacheService.del(tokenKey);
        cacheService.del(mappingKey);
    }
}
