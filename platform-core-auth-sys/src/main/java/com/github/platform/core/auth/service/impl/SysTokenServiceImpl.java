package com.github.platform.core.auth.service.impl;

import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
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
    private SecureRandom randGen = new SecureRandom();

    /**
     * 三层缓存
     * <br> 第一层：p:s:t:+token 缓存，默认30分钟，可配置，如果通过网关，网关只查第一层缓存，会有一定的概率出现1008
     * <br> 第二层：c30m:s:t:+token 缓存缓存30分钟
     * <br> 第三层：数据库服务
     * @param token
     * @return
     */
    @Override
    @Cacheable(cacheNames = CacheConstant.sysToken, key = "''+#token", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public String getLoginInfoStr(String token) {
        if (Objects.nonNull(tokenCacheGateway)){
            TokenCacheEntity tokenCacheEntity = tokenCacheGateway.findByToken(token);
            if (Objects.nonNull(tokenCacheEntity)){
                return tokenCacheEntity.getLoginInfo();
            }
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.sysToken, key = "#tenantId+':'+#loginName", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public String getLoginInfoStr(Integer tenantId, String loginName) {
        if (Objects.nonNull(tokenCacheGateway)){
            TokenCacheEntity tokenCacheEntity = tokenCacheGateway.findByLoginName(tenantId, loginName);
            if (Objects.nonNull(tokenCacheEntity)){
                return tokenCacheEntity.getLoginInfo();
            }
        }
        return null;
    }

    @Override
    @CachePut(cacheNames = CacheConstant.sysToken, key = "''+#token", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public String saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo, boolean isLogin) {
        if (!isLogin && isRenew()){
            return loginInfo;
        }
        log.debug("登录：{} token:{} loginName:{}",isLogin,token,loginName);
        if (Objects.nonNull(tokenCacheGateway)){
            tokenCacheGateway.saveOrUpdate(tenantId,token,loginName,loginInfo,isLogin);
        }
        return loginInfo;
    }
    private boolean isRenew(){
        if (Objects.isNull(tokenCacheGateway)){
            return true;
        }
        if (randGen.nextInt(100) % 10 == 0 ){
            return false;
        }
        return true;
    }
    @Override
    public void expireByLoginName(Integer tenantId, String loginName) {
        if (Objects.nonNull(tokenCacheGateway)){
            tokenCacheGateway.expireByLoginName(tenantId,loginName);
        }
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.sysToken, key = "''+#token", cacheManager = CacheConstant.cacheManager)
    public void expireByToken(String token) {
        if (Objects.nonNull(tokenCacheGateway)){
            tokenCacheGateway.expireByToken(token);
        }
    }
}
