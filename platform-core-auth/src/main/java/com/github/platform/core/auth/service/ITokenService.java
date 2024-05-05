package com.github.platform.core.auth.service;

/**
 * token服务，由各个服务实现
 *
 * @author: yxkong
 * @date: 2022/11/24 6:40 下午
 * @version: 1.0
 */
public interface ITokenService {

    /**
     * 获取登录信息
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
     * 缓存用户登陆信息
     * @param tenantId 租户信息
     * @param token 用户token
     * @param loginName 登录账户
     * @param loginInfo
     * @param isLogin 是否登录
     * @return 返回结果放入缓存
     */
    String saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo,boolean isLogin);

    /**
     * 过期用户登陆信息, 对应用户的所有登录信息
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
