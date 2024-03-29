//package com.github.platform.core.auth.service;
//
//import com.github.platform.core.auth.entity.LoginUserInfo;
//
///**
// * LoginToken 服务封装
// *
// * @author: yxkong
// * @date: 2023/1/5 1:36 PM
// * @version: 1.0
// */
//public interface LoginTokenService extends ITokenService {
//
//    /**
//     * 缓存用户登陆信息
//     *
//     * @param token
//     * @param loginUserInfo
//     * @return
//     */
//    boolean cacheUserInfo(String token, LoginUserInfo loginUserInfo);
//
//
//    /**
//     * 删除用户登陆信息
//     * @param loginName
//     */
//    void delUserInfoCache(String loginName,String token);
//    /**
//     * 获取 LoginUserInfo
//     *
//     * @param token
//     * @return LoginUserInfo
//     */
//    LoginUserInfo getLoginUserInfo(String token);
//}
