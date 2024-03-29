package com.github.platform.core.auth.context;

import com.github.platform.core.standard.entity.common.LoginInfo;
import com.github.platform.core.standard.exception.NoLoginException;

import java.util.Objects;

/**
 * @author: yxkong
 * @date: 2021/6/3 11:30 上午
 * @version: 1.0
 */
public class SecurityContextImpl<T extends LoginInfo> implements SecurityContext {

    private T loginInfo;

    public SecurityContextImpl(T loginInfo) {
        this.loginInfo = loginInfo;
    }

    public SecurityContextImpl() {
    }

    @Override
    public T getLoginInfo() {
        return this.loginInfo;
    }

    @Override
    public Boolean isLogin() {
        if (Objects.isNull(loginInfo)) {
           return false;
        }
        return true;
    }

    @Override
    public String getLoginName() {
        if (Objects.isNull(loginInfo)) {
            throw new NoLoginException("用户未登陆");
        }
        return this.loginInfo.getLoginName();
    }

    @Override
    public Integer getTenantId() {
        if (Objects.isNull(loginInfo)) {
            throw new NoLoginException("用户未登陆");
        }
        return this.loginInfo.getTenantId();
    }

    @Override
    public String getToken() {
        if (Objects.isNull(loginInfo)) {
            throw new NoLoginException("用户未登陆");
        }
        return this.loginInfo.getToken();
    }

    @Override
    public String getMobile() {
        if (Objects.isNull(loginInfo)) {
            throw new NoLoginException("用户未登陆");
        }
        return this.loginInfo.getMobile();
    }
}