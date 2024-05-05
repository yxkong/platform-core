package com.github.platform.core.auth.service;

/**
 * 认证
 * @author: yxkong
 * @date: 2024/4/24 21:12
 * @version: 1.0
 */
public interface IAuthorizationService {

    /**
     * 令牌认证
     * @param secretKey
     * @return
     */
    String bearer(String secretKey);

    /**
     * 基本认证
     * @param user
     * @param pwd
     * @return
     */
    String basic(String user,String pwd);
}
