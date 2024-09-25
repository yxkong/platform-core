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
public interface ITokenService extends IGatewayTokenService {

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



}
