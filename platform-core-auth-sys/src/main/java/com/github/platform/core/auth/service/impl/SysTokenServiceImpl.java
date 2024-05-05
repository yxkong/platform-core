package com.github.platform.core.auth.service.impl;

import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * admin后端实现
 * @author: yxkong
 * @date: 2024/4/29 21:36
 * @version: 1.0
 */
@Service("sysTokenService")
@Slf4j
public class SysTokenServiceImpl implements ITokenService {
    @Autowired(required = false)
    private ITokenCacheGateway tokenCacheGateway;

    @Override
    @Cacheable(cacheNames = CacheConstant.sysToken, key = "#token", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public String getLoginInfoStr(String token) {
        if (Objects.nonNull(tokenCacheGateway)){
            TokenCacheEntity tokenCacheEntity = tokenCacheGateway.findByToken(token);
            if (Objects.nonNull(tokenCacheEntity) && LocalDateTimeUtil.dateTime().isBefore(tokenCacheEntity.getExpireTime())){
                return tokenCacheEntity.getLoginInfo();
            }
        }
        return null;
    }

    @Override
    public String getLoginInfoStr(Integer tenantId, String loginName) {
        return null;
    }

    @Override
    @CachePut(cacheNames = CacheConstant.sysToken, key = "#token", cacheManager = CacheConstant.cacheManager)
    public String saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo, boolean isLogin) {
        if (!isLogin){
            return loginInfo;
        }
        if (Objects.nonNull(tokenCacheGateway)){
            tokenCacheGateway.saveOrUpdate(tenantId,token,loginName,loginInfo,isLogin);
        }
        return loginInfo;
    }

    @Override
    public void expireByLoginName(Integer tenantId, String loginName) {
        if (Objects.nonNull(tokenCacheGateway)){
            tokenCacheGateway.expireByLoginName(tenantId,loginName);
        }
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.sysToken, key = "#token", cacheManager = CacheConstant.cacheManager)
    public void expireByToken(String token) {
        if (Objects.nonNull(tokenCacheGateway)){
            tokenCacheGateway.expireByToken(token);
        }
    }
}
