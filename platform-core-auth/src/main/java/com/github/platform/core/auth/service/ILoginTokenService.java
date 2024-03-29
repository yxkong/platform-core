package com.github.platform.core.auth.service;


import com.github.platform.core.auth.constants.AuthTypeEnum;

/**
 * 登录信息操作
 * @author: yxkong
 * @date: 2023/4/20 5:22 PM
 * @version: 1.0
 */
public interface ILoginTokenService extends ITokenService{
    /**
     * 缓存用户登陆信息
     *
     * @param token
     * @param loginInfo
     * @return
     */
    boolean cacheUserInfo(AuthTypeEnum authType, String token, String loginName, String loginInfo);


    /**
     * 删除用户登陆信息
     * @param loginName
     */
    void delUserInfoCache(AuthTypeEnum authType,String loginName,String token);
}
