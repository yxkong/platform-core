package com.github.platform.core.sys.infra.gateway.impl.login;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.constant.RoleConstant;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.constant.DeptConstant;
import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.constant.UserLogBizTypeEnum;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.context.RegisterContext;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.gateway.ISysLoginGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.gateway.ThirdUserGateway;
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
import java.util.Objects;

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
    private ThirdUserGateway thirdUserGateway;
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
        ThirdUserEntity ldapUser = validate.getRight();
        ldapUser.setTenantId(context.getTenantId());
        //查找三方用户
        SysThirdUserDto thirdUser =  thirdUserGateway.findByChannel(ldapUser.getOpenId(),ldapUser.getChannel());
        /**获取或添加用户*/
        SysUserService userService = new SysUserService(userGateway);
        UserEntity userEntity =  userService.quietAddUser(getRegisterContext(ldapUser));
        if (Objects.nonNull(thirdUser)){
            /**三方用户已经存在没有被激活的时候，只能走默认的角色权限，可以申请权限*/
            if (thirdUser.isDisabled()){
                if (log.isDebugEnabled()){
                    log.debug("已注册未激活的用户:{}",context.getLoginName());
                }
                userEntity.setDefaultRoles(RoleConstant.thirdRole);
                return userEntity;
            }
        } else{
            /**初始化thirduser*/
            thirdUser = thirdUserGateway.insert(ldapUser, userEntity.getId());
        }
        return userEntity;
    }

    private  RegisterContext getRegisterContext(ThirdUserEntity ldapUser) {
        return RegisterContext.builder()
                .channel(UserChannelEnum.ldap)
                .loginName(ldapUser.getLoginName())
                .userName(ldapUser.getUserName())
                .email(ldapUser.getEmail())
                .deptId(DeptConstant.DEFAULT_ID)
                .roleIds(RoleConstant.thirdRole)
                .logBizTypeEnum(UserLogBizTypeEnum.third)
                .status(StatusEnum.OFF.getStatus())
                .createBy(ldapUser.getLoginName())
                .createTime(LocalDateTimeUtil.dateTime())
                .build();
    }


}
