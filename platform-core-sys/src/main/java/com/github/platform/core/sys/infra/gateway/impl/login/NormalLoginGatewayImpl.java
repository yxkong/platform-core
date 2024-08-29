package com.github.platform.core.sys.infra.gateway.impl.login;

import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.gateway.ISysLoginGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 普通登录网关
 * @author: yxkong
 * @date: 2023/4/4 12:27 PM
 * @version: 1.0
 */
@Service("normalLoginGateway")
@Slf4j
public class NormalLoginGatewayImpl implements ISysLoginGateway {
    @Resource
    private ISysUserGateway userGateway;
    @Override
    public UserEntity login(LoginContext context) throws InfrastructureException {
        return userGateway.baseAccountCheck(context.getLoginName(),context.getTenantId(), context.getPwd());
    }
}
