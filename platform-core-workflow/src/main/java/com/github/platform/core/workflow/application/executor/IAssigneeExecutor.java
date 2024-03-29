package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.domain.context.FlwRoleQueryContext;
import com.github.platform.core.workflow.domain.context.FlwUserQueryContext;
import com.github.platform.core.workflow.domain.dto.FlwUser;

import java.util.List;

/**
 * 流程审批人执行器
 * @author: yxkong
 * @date: 2023/12/27 11:32
 * @version: 1.0
 */
public interface IAssigneeExecutor {
    /**
     * 获取审批角色,根据流程类型分策略执行
     *
     * @param context
     * @return
     */
    List<OptionsDto> roles(FlwRoleQueryContext context);
    /**
     * 查询用户
     * @param context
     * @return
     */
    PageBean<FlwUser> queryUsers(FlwUserQueryContext context);
}
