package com.github.platform.core.auth.service;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.cache.infra.service.ICacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * api系统token操作
 * @author: yxkong
 * @date: 2024/4/29 13:42
 * @version: 1.0
 */
@Service("apiTokenService")
@Slf4j
public class ApiTokeServiceImpl implements ITokenService {
    @Resource
    private ICacheService cacheService;
    @Resource
    private AuthProperties authProperties;
    @Override
    @Cacheable(
            cacheResolver = "apiAuthCacheResolver",
            key = "''+#token",
            unless = "#result == null")
    public String getLoginInfoStr(String token) {
        return null;
    }
    @Override
    @Cacheable(
            cacheResolver = "apiAuthCacheResolver",
            key = "#tenantId+':'+#loginName",
            unless = "#result == null")
    public String getLoginInfoStr(Integer tenantId, String loginName) {
        String token = (String)cacheService.get(getMappingKey(authProperties.getApi().getLogin(), tenantId, loginName));
        if (Objects.nonNull(token)){
            return (String)cacheService.get(getTokenKey(authProperties.getApi().getLogin(), token));
        }
        return null;
    }


    @Override
    @CachePut(
            cacheResolver = "apiAuthCacheResolver",
            key = "''+#token",
            unless = "#result == null")
    public String saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo, boolean isLogin) {
        cacheService.set(getMappingKey(authProperties.getApi().getLogin(), tenantId,loginName),token);
        return loginInfo;
    }
    @Override
    public void expireByLoginName(Integer tenantId, String loginName) {
        cacheService.del(getMappingKey(authProperties.getApi().getLogin(), tenantId,loginName));
    }
    @Override
    @CacheEvict(
            cacheResolver = "apiAuthCacheResolver",
            key = "''+#token"
    )
    public void expireByToken(String token) {
    }


}

