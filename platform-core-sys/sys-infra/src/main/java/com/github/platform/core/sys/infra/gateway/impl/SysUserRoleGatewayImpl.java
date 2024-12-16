package com.github.platform.core.sys.infra.gateway.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.persistence.mapper.sys.SysRoleMapper;
import com.github.platform.core.persistence.mapper.sys.SysUserRoleMapper;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import com.github.platform.core.sys.domain.common.entity.SysUserRoleBase;
import com.github.platform.core.sys.domain.dto.SysUserRoleDto;
import com.github.platform.core.sys.domain.gateway.ISysUserRoleGateway;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 用户角色
 * @Author: yxkong
 * @Date: 2024/11/25
 * @version: 1.0
 */
@Service
@Slf4j
public class SysUserRoleGatewayImpl extends BaseGatewayImpl implements ISysUserRoleGateway {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public List<SysUserRoleDto> findListBy(SysUserRoleBase record) {
        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserRole(Long userId, Integer tenantId, Set<String> roleKeys) {
        if (CollectionUtil.isEmpty(roleKeys)){
            return ;
        }
        List<SysRoleBase> list = sysRoleMapper.findByKeys(roleKeys.toArray(new String[]{}),tenantId);
        // 校验角色id对应的角色是否存在
        if (CollectionUtil.isEmpty(list)) {
            throw exception(SysInfraResultEnum.ROLE_NOT_FIND);
        }

        sysUserRoleMapper.deleteById(userId);
        List<SysUserRoleBase> rst = new ArrayList<>();
        for (SysRoleBase role : list) {
            SysUserRoleBase userRole = SysUserRoleBase.builder()
                    .userId(userId)
                    .roleId(role.getId())
                    .roleKey(role.getKey())
                    .tenantId(tenantId)
                    .createBy(LoginUserInfoUtil.getLoginName())
                    .createTime(LocalDateTimeUtil.dateTime())
                    .build();
            rst.add(userRole);
        }
        sysUserRoleMapper.insertList(rst);
    }
}
