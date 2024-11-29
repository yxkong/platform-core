package com.github.platform.core.workflow.application.executor.strategy.impl;

import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.context.SysRoleQueryContext;
import com.github.platform.core.sys.domain.gateway.ISysRoleGateway;
import com.github.platform.core.workflow.application.executor.strategy.RoleStrategy;
import com.github.platform.core.workflow.domain.context.FlwRoleQueryContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统角色策略
 * @author: yxkong
 * @date: 2023/11/22 09:59
 * @version: 1.0
 */
@Service("sysRoleStrategy")
public class SysRoleStrategyImpl implements RoleStrategy {
    @Resource
    private ISysRoleGateway roleGateway;
    @Override
    public List<OptionsDto> roles(FlwRoleQueryContext context) {
        SysRoleQueryContext sysRoleQueryContext = SysRoleQueryContext.builder()
                .name(context.getName()).status(StatusEnum.ON.getStatus())
                .tenantId(context.getTenantId())
                .build();
        return roleGateway.roles(sysRoleQueryContext);
    }
}
