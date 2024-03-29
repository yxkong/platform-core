package com.github.platform.core.auth.context;

import com.github.platform.core.standard.entity.common.LoginInfo;

/**
 * @author: yxkong
 * @date: 2021/6/3 11:11 上午
 * @version: 1.0
 */
public interface SecurityContext<T extends LoginInfo> {
    /**
     * 获取用户登录信息
     *
     * @return
     */
    T getLoginInfo();

    /**
     * 判断用户是否登录
     * @return
     */
    Boolean isLogin();

    /**
     * 获取登录姓名
     * @return
     */
    String getLoginName();
    /**
     * 获取登录租户
     * @return
     */
    Integer getTenantId();

    /**
     * 获取token
     * @return
     */
    String getToken();
    /**
     * 获取用户手机号
     * @return
     */
    String getMobile();

}