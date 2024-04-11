package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.workflow.application.executor.IAssigneeExecutor;
import com.github.platform.core.workflow.application.executor.strategy.RoleStrategy;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.context.FlwRoleQueryContext;
import com.github.platform.core.workflow.domain.context.FlwUserQueryContext;
import com.github.platform.core.workflow.domain.dto.FlwUser;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.context.SysUserQueryContext;
import com.github.platform.core.sys.domain.gateway.ISysRoleGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程审批人执行器
 * @author: yxkong
 * @date: 2023/10/30 10:34
 * @version: 1.0
 */
@Service
public class AssigneeExecutorImpl extends BaseExecutor implements IAssigneeExecutor {
    @Resource
    private ISysRoleGateway roleGateway;
    @Resource
    private ISysUserGateway userGateway;

    /**
     * 获取审批角色,根据流程类型分策略执行
     * @param context
     * @return
     */
    @Override
    public List<OptionsDto> roles(FlwRoleQueryContext context) {
        ProcessTypeEnum processTypeEnum = ProcessTypeEnum.get(context.getProcessType());
        RoleStrategy roleStrategy = ApplicationContextHolder.getBean(processTypeEnum.getRoleBean(), RoleStrategy.class);
        return roleStrategy.roles(context);
    }

    /**
     * 查询用户
     * @param context
     * @return
     */
    @Override
    public PageBean<FlwUser> queryUsers(FlwUserQueryContext context) {
        SysUserQueryContext queryContext = SysUserQueryContext.builder()
                .deptId(context.getDeptId())
                .roleKey(context.getRoleKey())
                .loginName(context.getLoginName())
                .mobile(context.getMobile())
                .userName(context.getUserName())
                .pageNum(context.getPageNum())
                .pageSize(context.getPageSize())
                .build();
        PageBean<SysUserDto> pageBean = userGateway.queryByDept(queryContext);
        List<FlwUser> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(pageBean.getData())){
            pageBean.getData().forEach(s->{
                list.add(new FlwUser(s.getLoginName(),s.getUserName(),s.getDeptName()));
            });
        }
        return new PageBean<>(pageBean,list);
    }
}
