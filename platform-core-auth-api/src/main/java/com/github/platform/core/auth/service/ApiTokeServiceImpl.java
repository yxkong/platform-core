package com.github.platform.core.auth.service;

import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
    @Autowired(required = false)
    private ITokenCacheGateway tokenCacheGateway;
    @Override
    @Cacheable(cacheNames = CacheConstant.apiToken, key = "#token", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
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
    @Cacheable(cacheNames = CacheConstant.sysToken, key = "#tenantId+':'+#loginName", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public String getLoginInfoStr(Integer tenantId, String loginName) {
        if (Objects.nonNull(tokenCacheGateway)){
            List<TokenCacheEntity> list = tokenCacheGateway.findByLoginName(tenantId, loginName);
            if (CollectionUtil.isNotEmpty(list)){
                return list.get(0).getLoginInfo();
            }
        }
        return null;
    }


    @Override
    @CachePut(cacheNames = CacheConstant.apiToken, key = "#token", cacheManager = CacheConstant.cacheManager)
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
    public void expireByToken(String token) {
        if (Objects.nonNull(tokenCacheGateway)){
            tokenCacheGateway.expireByToken(token);
        }
    }
}

