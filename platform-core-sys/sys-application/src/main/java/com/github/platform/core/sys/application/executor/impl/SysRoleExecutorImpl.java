package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.application.constant.SysAppResultEnum;
import com.github.platform.core.sys.application.executor.IRoleExecutor;
import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import com.github.platform.core.sys.domain.common.entity.SysUserBase;
import com.github.platform.core.sys.domain.common.entity.SysUserRoleBase;
import com.github.platform.core.sys.domain.context.SysRoleContext;
import com.github.platform.core.sys.domain.context.SysRoleQueryContext;
import com.github.platform.core.sys.domain.dto.SysRoleDto;
import com.github.platform.core.sys.domain.dto.SysRoleMenuDto;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.sys.domain.dto.SysUserRoleDto;
import com.github.platform.core.sys.domain.gateway.ISysRoleGateway;
import com.github.platform.core.sys.domain.gateway.ISysRoleMenuGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserRoleGateway;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysRoleExecutorImpl extends SysExecutor implements IRoleExecutor {

    @Resource
    private ISysRoleGateway roleGateway;
    @Resource
    private ISysRoleMenuGateway sysRoleMenuGateway;
    @Resource
    private ISysUserRoleGateway sysUserRoleGateway;
    @Resource
    private ISysUserGateway sysUserGateway;

    @Override
    public PageBean<SysRoleDto> query(SysRoleQueryContext context) {
        context.setTenantId(getTenantId(context));
        return roleGateway.query(context);
    }
    @Override
    public List<OptionsDto> select(SysRoleQueryContext context) {
        context.setTenantId(getTenantId(context));
        context = SysRoleQueryContext.builder().status(StatusEnum.ON.getStatus()).tenantId(context.getTenantId()).build();
        return roleGateway.roles(context);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(SysRoleContext context) {
        context.setId(null);
        context.setTenantId(getTenantId(context));
        String key = context.getKey();
        String loginName = LoginUserInfoUtil.getLoginName();
        // 校验当前租户是否存在当前角色
        SysRoleDto res = roleGateway.findByNameOrKeyAndTenant(context.getName(),key, context.getTenantId());
        if (Objects.nonNull(res)) {
            log.warn("[{}]添加角色,制定租户:{}中角色:{},已存在", loginName,  context.getTenantId(), context.getName());
            throw exception(SysAppResultEnum.ROLE_ALREADY_EXIST);
        }
        SysRoleDto dto = roleGateway.insert(context);
        if (Objects.isNull(dto.getId())){
            throw exception(SysAppResultEnum.ROLE_ADD_ERROR);
        }
        sysRoleMenuGateway.insertList(context.getMenuIds(),dto,context.getTenantId());
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long roleId) {
        // 校验是否存在用户
        List<SysUserRoleDto> userRoleDOList = sysUserRoleGateway.findListBy(SysUserRoleBase.builder().roleId(roleId).build());
        if (CollectionUtil.isNotEmpty(userRoleDOList)) {
            // 如果不为空，且用户未被删除，则不允许删除角色
            userRoleDOList.forEach(s->{
                SysUserDto sysUserDto = sysUserGateway.findByUserId(s.getUserId());
                if (Objects.nonNull(sysUserDto) && sysUserDto.getStatus().equals(StatusEnum.ON.getStatus())){
                    throw exception(SysAppResultEnum.FORBID_DELETE_ROLE);
                }
            });
        }
        roleGateway.deleteById(roleId);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleContext context) {
        // TODO 修改完角色以后，需要重新加载对应角色用户的权限
        context.setTenantId(getTenantId(context));

        String loginName = LoginUserInfoUtil.getLoginName();
        // 校验当前租户是否存在当前角色
        SysRoleBase res = roleGateway.findByExist(context.getId(),context.getName(),context.getKey(), context.getTenantId());
        if (Objects.nonNull(res)) {
            log.warn("[{}]修改角色,当前租户:{}中角色:{},已存在", loginName, context.getTenantId(), context.getName());
             throw exception(SysAppResultEnum.ROLE_ALREADY_EXIST);
        }
        sysRoleMenuGateway.deleteByRoleId(context.getId());
        context.setCreateBy(loginName);
        SysRoleDto roleDto = roleGateway.updateById(context);
        sysRoleMenuGateway.insertList(context.getMenuIds(),roleDto,context.getTenantId());
    }
    @Override
    public SysRoleDto findById(Long id) {
        SysRoleDto role = roleGateway.findById(id);
        if (Objects.isNull(role)) {
            throw  exception(ResultStatusEnum.NO_DATA);
        }
        List<SysRoleMenuDto> list = sysRoleMenuGateway.findByRoleId(id);
        if (!CollectionUtil.isEmpty(list)) {
             role.setMenuIds(list.stream().map(SysRoleMenuDto::getMenuId).collect(Collectors.toList()));
        }
        return role;
    }


}
