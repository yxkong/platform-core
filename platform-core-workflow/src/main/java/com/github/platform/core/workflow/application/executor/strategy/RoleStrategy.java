package com.github.platform.core.workflow.application.executor.strategy;

import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.domain.context.FlwRoleQueryContext;

import java.util.List;

/**
 * 角色策略
 * @author: yxkong
 * @date: 2023/11/22 09:57
 * @version: 1.0
 */
public interface RoleStrategy {

    /**
     * 获取角色
     * @param context
     * @return
     */
    List<OptionsDto> roles(FlwRoleQueryContext context);
}
