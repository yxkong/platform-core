package com.github.platform.core.auth.service;

import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
    @Override
    @Cacheable(cacheNames = CacheConstant.apiToken, key = "''+#token", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public String getLoginInfoStr(String token) {
        return null;
    }
    @Override
    @Cacheable(cacheNames = CacheConstant.apiToken, key = "#tenantId+':'+#loginName", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public String getLoginInfoStr(Integer tenantId, String loginName) {
        String token = (String)cacheService.get(getMappingKey(tenantId, loginName));
        if (Objects.nonNull(token)){
            String tokenKey = String.format("%s:%s",CacheConstant.apiToken,token);
            return (String)cacheService.get(tokenKey);
        }
        return null;
    }


    @Override
    @CachePut(cacheNames = CacheConstant.apiToken, key = "''+#token", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public String saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo, boolean isLogin) {
        cacheService.set(getMappingKey(tenantId,loginName),token);
        return loginInfo;
    }
    @Override
    public void expireByLoginName(Integer tenantId, String loginName) {
        cacheService.del(getMappingKey(tenantId,loginName));
    }
    @Override
    @CacheEvict(cacheNames = CacheConstant.apiToken, key = "''+#token", cacheManager = CacheConstant.cacheManager)
    public void expireByToken(String token) {
    }
    private String getMappingKey(Integer tenantId, String loginName){
        return CacheConstant.apiUserTokenMapping+tenantId+ SymbolConstant.colon+loginName;
    }
}

