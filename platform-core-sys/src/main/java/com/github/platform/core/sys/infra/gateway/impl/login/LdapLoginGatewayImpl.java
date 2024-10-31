package com.github.platform.core.sys.infra.gateway.impl.login;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.gateway.ISysLoginGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.gateway.IThirdUserGateway;
import com.github.platform.core.sys.domain.model.user.ThirdUserEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.domain.service.SysUserService;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.service.ILdapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ldap登录网关
 * @author: yxkong
 * @date: 2023/4/4 12:28 PM
 * @version: 1.0
 */
@Service("ldapLoginGateway")
@Slf4j
public class LdapLoginGatewayImpl extends BaseGatewayImpl implements ISysLoginGateway {
    @Autowired
    private AuthProperties properties;
    @Resource
    private ILdapService ldapService;
    @Resource
    private IThirdUserGateway thirdUserGateway;
    @Resource
    private ISysUserGateway userGateway;
    @Resource(name = "normalLoginGateway")
    private ISysLoginGateway loginGateway;

    @Override
    public UserEntity login(LoginContext context)throws InfrastructureException {
        List<String> innerUsers = properties.getSys().getInnerUsers();
        /**
         * 内置账户还是要走普通登录
         */
        if (innerUsers.contains(context.getLoginName())){
            return loginGateway.login(context);
        }
        Pair<Boolean, ThirdUserEntity> validate = ldapService.validate(context.getLoginName(), context.getPwd());
        if (!validate.getLeft()){
            exception(SysInfraResultEnum.LDAP_LOGIN_FAIL);
        }
        ThirdUserEntity thirdUserEntity = validate.getRight();
        //设置租户
        thirdUserEntity.setTenantId(context.getTenantId());
        thirdUserEntity.setChannel(UserChannelEnum.ldap);

        /**获取或添加用户*/
        SysUserService userService = new SysUserService(userGateway,thirdUserGateway);
        return userService.quietAddUserWithLoginName(thirdUserEntity);
    }
}
