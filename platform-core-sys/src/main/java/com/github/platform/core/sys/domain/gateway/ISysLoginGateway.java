package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.model.user.UserEntity;

/**
 * 登录网关
 * @author: yxkong
 * @date: 2023/4/4 12:25 PM
 * @version: 1.0
 */
public interface ISysLoginGateway {
    /**
     * 登录网关
     * @param context
     * @return
     */
    UserEntity login(LoginContext context)throws InfrastructureException;
}
