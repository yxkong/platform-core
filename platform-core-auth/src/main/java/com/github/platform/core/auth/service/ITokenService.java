package com.github.platform.core.auth.service;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.standard.constant.SymbolConstant;

/**
 * token服务，由各个服务实现
 *
 * @author: yxkong
 * @date: 2022/11/24 6:40 下午
 * @version: 1.0
 */
public interface ITokenService {

    /**
     * 获取登录信息，优先从缓存中获取，
     * <br> 缓存中没有则从数据库获取，然后缓存,实现自动续期
     * @param token
     * @return
     */
    String getLoginInfoStr(String token);

    /**
     * 根据租户和用户名获取用户信息
     * <br> ps 只有单端登录的时候才有意义
     * @param tenantId
     * @param loginName
     * @return
     */
    String getLoginInfoStr(Integer tenantId,String loginName);

    /**
     * 缓存用户登陆信息,非登录情况属于续租
     * @param tenantId 租户信息
     * @param token 用户token
     * @param loginName 用户账户
     * @param optUser 操作用户
     * @param loginInfo
     * @param isLogin 是否登录
     * @return 返回结果放入缓存
     */
    String saveOrUpdate(Integer tenantId, String token, String loginName,String optUser, String loginInfo,boolean isLogin);

    /**
     * 演示环境下不过期历史登录，非演示环境，新的登录会过期历史登录
     * @param tenantId 租户信息
     * @param loginName
     */
    void expireByLoginName(Integer tenantId, String loginName);

    /**
     * 过期用户登陆信息,只是当前
     * @param token
     */
    void expireByToken(String token);
    /**
     * 获取缓存key
     * @param login 登录配置
     * @param token token的key
     * @return redis中缓存的key
     */
    default String getTokenKey(AuthProperties.Login login, String token){
        return  String.format("%s:%s",login.getToken(),token);
    }

    /**
     * 获取用户的缓存key
     * @param login 登录配置
     * @param tenantId 租户
     * @param loginName 登录用户名
     * @return redis中缓存的key
     */
    default String getMappingKey(AuthProperties.Login login,Integer tenantId, String loginName){
        return login.getUserTokenMapping() +tenantId+ SymbolConstant.colon+loginName;
    }


}
