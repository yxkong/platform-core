package com.github.platform.core.sys.infra.gateway.impl.login;

import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.gateway.ISysLoginGateway;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import org.springframework.stereotype.Service;

/**
 * 支付宝登录网关
 * @author: yxkong
 * @date: 2023/4/4 12:30 PM
 * @version: 1.0
 */
@Service("alipayLoginGateway")
public class AlipayLoginGatewayImpl implements ISysLoginGateway {
    @Override
    public UserEntity login(LoginContext context)throws InfrastructureException {
        return null;
    }
}
