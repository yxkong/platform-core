package com.github.platform.core.auth.application.executor;

import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.standard.entity.BaseEntity;

import java.util.Objects;

/**
 * 获取sys系统的租户id
 * @Author: yxkong
 * @Date: 2024/11/22
 * @version: 1.0
 */
public class SysExecutor extends BaseExecutor  {
    /**
     * 获取租户id,
     *  只有管理员可以指定租户id，否则获取当前登录用的租户id
     * @param entity
     * @param <T>
     */
    public <T extends BaseEntity> Integer getTenantId(T entity) {
        if (!AuthUtil.isLogin()){
            return null;
        }
        if (AuthUtil.isSuperAdmin()){
            return Objects.nonNull(entity)?entity.getTenantId():null;
        }
        return LoginUserInfoUtil.getTenantId();
    }
    /**
     * 获取租户id,
     *  只有管理员可以指定租户id，否则获取当前登录用的租户id
     * @param entity
     * @param <T>
     */
    public <T extends BaseEntity> Integer getMustTenantId(T entity) {
        if (!AuthUtil.isLogin()){
            return null;
        }
        if (AuthUtil.isSuperAdmin() && Objects.nonNull(entity) && Objects.nonNull(entity.getTenantId())){
            return entity.getTenantId();
        }
        return LoginUserInfoUtil.getTenantId();
    }
}
