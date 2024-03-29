package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.sys.SysRoleMapper;
import com.github.platform.core.persistence.mapper.sys.SysUserMapper;
import com.github.platform.core.persistence.mapper.sys.SysUserRoleMapper;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import com.github.platform.core.sys.domain.common.entity.SysRoleMenuBase;
import com.github.platform.core.sys.domain.common.entity.SysUserBase;
import com.github.platform.core.sys.domain.common.entity.SysUserRoleBase;
import com.github.platform.core.sys.domain.context.SysRoleContext;
import com.github.platform.core.sys.domain.context.SysRoleQueryContext;
import com.github.platform.core.sys.domain.dto.SysRoleDto;
import com.github.platform.core.sys.domain.gateway.ISysRoleGateway;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.convert.SysRoleInfraConvert;
import com.github.platform.core.sys.infra.service.sys.ISysRoleMenuService;
import com.github.platform.core.sys.infra.util.ResultPageBeanUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleGatewayImpl extends BaseGatewayImpl implements ISysRoleGateway {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleInfraConvert roleInfraConvert;

    @Resource
    private ISysRoleMenuService iSysRoleMenuService;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public PageBean<SysRoleDto> query(SysRoleQueryContext context) {
        SysRoleBase sysRoleBase = roleInfraConvert.toSysRoleBase(context);
        if (!AuthUtil.isSuperAdmin()) {
            sysRoleBase.setTenantId(LoginUserInfoUtil.getTenantId());
        }
        PageHelper.startPage(context.getPageNum(),context.getPageSize());
        List<SysRoleBase> list = sysRoleMapper.findListBy(sysRoleBase);
        PageInfo<SysRoleBase> pageInfo = new PageInfo<>(list);
        final List<SysRoleBase> roleDOList = pageInfo.getList();
        List<SysRoleDto> roleDtoList = roleInfraConvert.toDtos(roleDOList);
        if (CollectionUtil.isNotEmpty(roleDOList)){
            roleDtoList.forEach(s->{
                List<SysRoleMenuBase> roleMenuList = iSysRoleMenuService.findListBy(SysRoleMenuBase.builder().roleId(s.getId()).build());
                if (!CollectionUtil.isEmpty(roleMenuList)) {
                    s.setMenuIds(roleMenuList.stream().map(SysRoleMenuBase::getMenuId).toArray(Long[]::new));
                }
            });
        }

        return ResultPageBeanUtil.pageBean(pageInfo, roleDtoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysRoleContext roleContext) {
        String roleName = roleContext.getName();
        String key = roleContext.getKey();
        String loginName = LoginUserInfoUtil.getLoginName();
        Integer tenantId = LoginUserInfoUtil.getTenantId();
        // 校验当前租户是否存在当前角色
        SysRoleBase res = sysRoleMapper.findByNameOrKeyAndTenant(roleName,key, tenantId);

        if (Objects.nonNull(res)) {
            log.warn("[{}]添加角色,当前租户:{}中角色:{},已存在", loginName, tenantId, roleName);
            exception(SysInfraResultEnum.ROLE_ALREADY_EXIST);
        }
        SysRoleBase sysRole = roleInfraConvert.toSysRoleBase(roleContext);
        sysRole.setTenantId(LoginUserInfoUtil.getTenantId());
        sysRoleMapper.insert(sysRole);

        iSysRoleMenuService.insertList(roleContext.getMenuIds(),sysRole.getId(),null);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long roleId) {
        // 校验是否存在用户
        SysUserRoleBase userRole = SysUserRoleBase.builder().roleId(roleId).build();
        List<SysUserRoleBase> userRoleDOList = sysUserRoleMapper.findListBy(userRole);
        if (CollectionUtil.isNotEmpty(userRoleDOList)) {
            // 如果不为空，且用户未被删除，则不允许删除角色
            userRoleDOList.forEach(s->{
                SysUserBase sysUser = sysUserMapper.findById(s.getUserId());
                if (Objects.nonNull(sysUser) && sysUser.getStatus().equals(StatusEnum.ON.getStatus())){
                    exception(SysInfraResultEnum.FORBID_DELETE_ROLE);
                }
            });
        }
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public SysRoleDto findById(Long roleId) {
        SysRoleBase sysUser = sysRoleMapper.findById(roleId);
        if (Objects.isNull(sysUser)) {
            return null;
        }
        return roleInfraConvert.toDto(sysUser);
    }

    @Override
    public List<SysRoleDto> findByIds(List<Long> roleIds) {
        List<SysRoleBase> list = sysRoleMapper.findByIds(roleIds.toArray(new Long[0]));
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return roleInfraConvert.toDtos(list);
    }

    @Override
    public SysRoleDto findByRoleKey(String roleKey) {
        if (StringUtils.isEmpty(roleKey)){
            return null;
        }
        List<SysRoleBase> list = sysRoleMapper.findListBy(SysRoleBase.builder().key(roleKey).build());
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        return roleInfraConvert.toDto(list.get(0)) ;
    }

    @Override
    public void evictCacheUsers() {

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SysRoleContext context) {
        String roleName = context.getName();
        String loginName = LoginUserInfoUtil.getLoginName();
        Integer tenantId = LoginUserInfoUtil.getTenantId();
        // 校验当前租户是否存在当前角色
        SysRoleBase res = sysRoleMapper.findByExist(context.getId(),roleName,context.getKey(), tenantId);

        if (Objects.nonNull(res)) {
            log.warn("[{}]修改角色,当前租户:{}中角色:{},已存在", loginName, tenantId, roleName);
            exception(SysInfraResultEnum.ROLE_ALREADY_EXIST);
        }
        SysRoleBase sysRole = roleInfraConvert.toSysRoleBase(context);
        iSysRoleMenuService.deleteByRoleId(context.getId());

        iSysRoleMenuService.insertList(context.getMenuIds(),sysRole.getId(),null);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public List<SysRoleDto> findRoleByUserId(Long userId) {
        List<SysUserRoleBase> userRoleList = sysUserRoleMapper.findByIds(new Long[]{userId});
        if (CollectionUtil.isEmpty(userRoleList)) {
            return Lists.newArrayList();
        }

        Long[] roleIds = userRoleList.stream().map(SysUserRoleBase::getRoleId).toArray(Long[]::new);

        List<SysRoleBase> sysRoleBaseList = sysRoleMapper.findByIds(roleIds);
        return roleInfraConvert.toDtos(sysRoleBaseList);
    }

    @Override
    public void deleteUserRoleByUserId(Long userId) {

        int effected = sysUserRoleMapper.deleteById(userId);
        if (effected < 1) {
            log.warn("根据用户删除角色gateway.userId:{}没有角色或userId错误", userId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserRole(Long userId, Set<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)){
            return ;
        }
        List<SysRoleBase> sysRole = sysRoleMapper.findByIds(roleIds.toArray(new Long[0]));
        // 校验角色id对应的角色是否存在
        if (CollectionUtil.isEmpty(sysRole) || roleIds.size() != sysRole.size()) {
            exception(SysInfraResultEnum.ROLE_NOT_FIND);
        }

        sysUserRoleMapper.deleteById(userId);
        List<SysUserRoleBase> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRoleBase userRole = SysUserRoleBase.builder()
                    .userId(userId)
                    .roleId(roleId)
                    .build();
            list.add(userRole);

        }
        sysUserRoleMapper.insertList(list);
    }

    @Override
    public Set<Long> queryMenuIds(Long roleId) {

        List<SysRoleMenuBase> sysRoleMenuList = iSysRoleMenuService.findByIds(new Long[]{roleId});
        if (CollectionUtil.isEmpty(sysRoleMenuList)) {
            return Sets.newHashSet();
        }
        return sysRoleMenuList.stream().map(SysRoleMenuBase::getMenuId).collect(Collectors.toSet());

    }
    @Override
    public List<OptionsDto> roles(SysRoleQueryContext context) {
        List<OptionsDto> rst = new ArrayList<>();
        List<SysRoleBase> list = sysRoleMapper.findListBy(null);
        if (CollectionUtil.isNotEmpty(list)){
            list.forEach(s->{
                rst.add(new OptionsDto(s.getId()+"",s.getKey(),s.getName()));
            });
        }
        return rst;
    }
}
