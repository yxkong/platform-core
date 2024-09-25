package com.github.platform.core.auth.service.impl;

import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.auth.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

/**
 * admin后端实现
 * <p> 这个实现有限制，要求业务h和网关使用一套redis，限制比较大</p>
 * @author: yxkong
 * @date: 2024/4/29 21:36
 * @version: 1.0
 */
@Service("sysTokenService")
@Slf4j
public class SysTokenServiceImpl implements ITokenService {
    @Resource
    private Optional<ITokenCacheGateway> tokenCacheGateway;
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
    @Cacheable(
            cacheResolver = "sysAuthCacheResolver",
            key = "''+#token",
            unless = "#result == null")
    public String getLoginInfoStr(String token) {
        return tokenCacheGateway
                .map(gateway -> gateway.findByToken(token))
                .filter(Objects::nonNull)
                .map(TokenCacheEntity::getLoginInfo)
                .orElse(null);
    }

    @Override
    @Cacheable(
            cacheResolver = "sysAuthCacheResolver",
            key = "#tenantId+':'+#loginName",
            unless = "#result == null")
    public String getLoginInfoStr(Integer tenantId, String loginName) {
        return tokenCacheGateway
                .map(gateway -> gateway.findByLoginName(tenantId,loginName))
                .filter(Objects::nonNull)
                .map(TokenCacheEntity::getLoginInfo)
                .orElse(null);
    }

    @Override
    @CachePut(
            cacheResolver = "sysAuthCacheResolver",
            key = "''+#token",
            unless = "#result == null")
    public String saveOrUpdate(Integer tenantId, String token, String loginName,String optUser, String loginInfo, boolean isLogin) {
        if (!isLogin && isRenew()){
            return loginInfo;
        }
        if (log.isDebugEnabled()){
            log.debug("登录：{} token:{} loginName:{}",isLogin,token,loginName);
        }
        tokenCacheGateway.ifPresent(gateway -> gateway.saveOrUpdate(tenantId, token, loginName,optUser, loginInfo, isLogin));
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
        tokenCacheGateway.ifPresent(gateway -> gateway.expireByLoginName(tenantId, loginName));
    }

    @Override
    @CacheEvict(
            cacheResolver = "sysAuthCacheResolver",
            key = "''+#token"
    )
    public void expireByToken(String token) {
        tokenCacheGateway.ifPresent(gateway -> gateway.expireByToken(token));
    }
}
