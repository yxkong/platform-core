package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.application.executor.IRoleExecutor;
import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import com.github.platform.core.sys.domain.context.SysRoleContext;
import com.github.platform.core.sys.domain.context.SysRoleQueryContext;
import com.github.platform.core.sys.domain.dto.SysRoleDto;
import com.github.platform.core.sys.domain.dto.resp.RoleDetailDto;
import com.github.platform.core.sys.domain.gateway.ISysRoleGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class SysRoleExecutorImpl extends BaseExecutor implements IRoleExecutor {

    @Resource
    private ISysRoleGateway roleGateway;

    @Override
    public PageBean<SysRoleDto> query(SysRoleQueryContext context) {
        return roleGateway.query(context);
    }
    @Override
    public List<OptionsDto> select(SysRoleQueryContext context) {
        context = SysRoleQueryContext.builder().status(StatusEnum.ON.getStatus()).build();
        return roleGateway.roles(context);
    }

    @Override
    public void addRole(SysRoleContext roleContext) {
        roleContext.setId(null);
        roleGateway.add(roleContext);
    }
    @Override
    public void deleteById(Long roleId) {
        roleGateway.deleteById(roleId);
    }
    @Override
    public void editRole(SysRoleContext context) {
        // TODO 修改完角色以后，需要重新加载对应角色用户的权限
        roleGateway.update(context);
    }
    @Override
    public RoleDetailDto queryDetail(Long id) {
        SysRoleDto role = roleGateway.findById(id);
        if (Objects.isNull(role)) {
            exception(ResultStatusEnum.NO_DATA);
        }
        Set<Long> menuIds = roleGateway.queryMenuIds(id);
        RoleDetailDto roleDetailDto = RoleDetailDto.builder()
                .id(id)
                .name(role.getName())
                .dataScope(role.getDataScope())
                .menuIds(menuIds)
                .build();
        return roleDetailDto;
    }


}
