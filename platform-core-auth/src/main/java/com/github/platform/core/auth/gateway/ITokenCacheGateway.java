package com.github.platform.core.auth.gateway;

import com.github.platform.core.auth.entity.TokenCacheEntity;

import java.util.List;

/**
 * token缓存网关，由各业务系统实现
 * @author: yxkong
 * @date: 2024/4/29 22:05
 * @version: 1.0
 */
public interface ITokenCacheGateway {
    /**
     * 查询token
     * @param token
     * @return
     */
    TokenCacheEntity findByToken(String token);

    /**
     * 查询用户信息
     * @param tenantId
     * @param loginName
     * @return
     */
    TokenCacheEntity findByLoginName(Integer tenantId, String loginName);

    /**
     * 保存或更新token
     * @param tenantId
     * @param token
     * @param loginName
     * @param loginInfo
     * @return
     */
    TokenCacheEntity saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo, boolean isLogin);

    /**
     * 过期token
     * @param token
     */

    void expireByToken(String token);

    /**
     * 根据id过期登录信息
     * @param id
     */
    void expireById(Long id);

    /**
     * 过期指定用户登录信息
     * @param tenantId
     * @param loginName
     */
    void expireByLoginName(Integer tenantId, String loginName);
}
