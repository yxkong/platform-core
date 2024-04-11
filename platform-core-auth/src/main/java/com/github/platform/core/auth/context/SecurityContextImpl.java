package com.github.platform.core.auth.context;

import com.github.platform.core.common.utils.StringUtils;
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

    /**
     * 统一登录校验
     * @return
     */
    @Override
    public Boolean isLogin() {
        if (Objects.nonNull(loginInfo) && StringUtils.isNotEmpty(loginInfo.getLoginName()) && Objects.nonNull(loginInfo.getId())) {
           return true;
        }
        return false;
    }
    private void noLoginThrow(){
        if (!isLogin()) {
            throw new NoLoginException("未登录或token失效，请重新登录！");
        }
    }

    @Override
    public String getLoginName() {
        noLoginThrow();
        return this.loginInfo.getLoginName();
    }

    @Override
    public Integer getTenantId() {
        noLoginThrow();
        return this.loginInfo.getTenantId();
    }

    @Override
    public String getToken() {
        noLoginThrow();
        return this.loginInfo.getToken();
    }

    @Override
    public String getMobile() {
        noLoginThrow();
        return this.loginInfo.getMobile();
    }
}