package com.github.platform.core.sys.infra.gateway.impl.login;

import com.github.platform.core.common.service.BaseServiceImpl;
import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.gateway.ISysLoginGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.model.sms.VerifyEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.domain.service.VerifyStrategy;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.convert.SysUserInfraConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 短信登录网关实现
 * @author: yxkong
 * @date: 2023/4/4 12:25 PM
 * @version: 1.0
 */
@Service("smsLoginGateway")
@Slf4j
public class SmsLoginGatewayImpl extends BaseServiceImpl implements ISysLoginGateway {
    @Resource(name="smsVerifyStrategy")
    private VerifyStrategy verifyStrategy;
    @Resource
    private ISysUserGateway userGateway;
    @Resource
    private SysUserInfraConvert userConvert;
    @Override
    public UserEntity login(LoginContext context)throws InfrastructureException {
        VerifyEntity verifyEntity = userConvert.verify(context);
        verifyStrategy.verify(verifyEntity);
        UserEntity userEntity = userGateway.findByLoginName(context.getLoginName(),context.getTenantId());
        if (Objects.isNull(userEntity)){
            exception(SysInfraResultEnum.NOT_FOUND_USER);
        }
        return userEntity;

    }
}
