package com.github.platform.core.sys.domain.service;

import com.github.platform.core.common.domain.DomainBaseService;
import com.github.platform.core.standard.annotation.DomainService;
import com.github.platform.core.sys.domain.context.RegisterContext;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 用户领域服务
 *
 * @author: yxkong
 * @date: 2023/1/4 3:03 PM
 * @version: 1.0
 */
@DomainService
@Builder
@Slf4j
public class SysUserService extends DomainBaseService {

    ISysUserGateway userGateway;

    public SysUserService(ISysUserGateway userGateway) {
        this.userGateway = userGateway;
    }


    /**
     * 后台新增用户
     *
     * @param context
     * @return
     */
    public UserEntity addUser(RegisterContext context) {
        UserEntity userEntity = userGateway.findByLoginName(context.getLoginName());
        if (Objects.nonNull(userEntity)) {
            exception(SysInfraResultEnum.REGISTERED);
        }
        userEntity = userGateway.findByMobile(context.getMobile());
        if (Objects.nonNull(userEntity)) {
            exception(SysInfraResultEnum.MOBILE_REGISTERED);
        }
        return userGateway.addUser(context);
    }

    /**
     * 静默添加用户
     * @param context
     * @return
     */
    public UserEntity quietAddUser(RegisterContext context){
        UserEntity userEntity = userGateway.findByLoginName(context.getLoginName());
        if (Objects.nonNull(userEntity)){
            return userEntity;
        }
        userEntity = userGateway.findByMobile(context.getMobile());
        if (Objects.nonNull(userEntity)){
            return userEntity;
        }
        return userGateway.addUser(context);
    }


    /**
     * 修改用户信息
     *
     * @param context
     * @return
     */
    public void editUser(RegisterContext context) {
        UserEntity userEntity = userGateway.findByLoginName(context.getLoginName());
        if (Objects.isNull(userEntity)) {
            exception(SysInfraResultEnum.NOT_FOUND_USER);
        }
        UserEntity userEntityByMobile = userGateway.findByMobile(context.getMobile());
        if (Objects.nonNull(userEntityByMobile) && !userEntityByMobile.getLoginName().equals(context.getLoginName())) {
            exception(SysInfraResultEnum.MOBILE_REGISTERED);
        }
        context.setId(userEntity.getId());
        userGateway.editUser(context);
    }


}
