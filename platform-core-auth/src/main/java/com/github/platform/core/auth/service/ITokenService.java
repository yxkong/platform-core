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
     * @param loginName 登录账户
     * @param loginInfo
     * @param isLogin 是否登录
     * @return 返回结果放入缓存
     */
    String saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo,boolean isLogin);

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
