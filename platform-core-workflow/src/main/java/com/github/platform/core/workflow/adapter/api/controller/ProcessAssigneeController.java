package com.github.platform.core.workflow.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.web.web.BaseController;
import com.github.platform.core.workflow.adapter.api.command.FlwRoleQuery;
import com.github.platform.core.workflow.adapter.api.command.FlwUserQuery;
import com.github.platform.core.workflow.adapter.api.convert.ProcessAssigneeConvert;
import com.github.platform.core.workflow.application.executor.IAssigneeExecutor;
import com.github.platform.core.workflow.domain.context.FlwRoleQueryContext;
import com.github.platform.core.workflow.domain.context.FlwUserQueryContext;
import com.github.platform.core.workflow.domain.dto.FlwUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 审批人
 * @author: yxkong
 * @date: 2023/10/30 10:21
 * @version: 1.0
 */
@RestController
@Tag(name = "processAssignee",description = "流程管理管理")
@RequestMapping("/api/workflow/assignee")
@Slf4j
public class ProcessAssigneeController extends BaseController {

    @Resource
    private IAssigneeExecutor assigneeExecutor;
    @Resource
    private ProcessAssigneeConvert assigneeConvert;
    @RequiredLogin
    @OptLog(module="processAssignee",title="角色列表",persistent = false)
    @Operation(summary = "审批人角色列表",tags = {"processAssignee"})
    @PostMapping("/roles")
    public ResultBean<List<OptionsDto>> roles(@RequestBody FlwRoleQuery roleQuery) {
        FlwRoleQueryContext roleQueryContext = assigneeConvert.toQueryRole(roleQuery);
        List<OptionsDto> list = assigneeExecutor.roles(roleQueryContext);
        return buildSucResp(list);
    }
    @OptLog(module="processAssignee",title="根据条件查询用户，可部门id，可角色id",persistent = false)
    @Operation(summary = "根据条件查询，可角色可部门",tags = {"processAssignee"})
    @PostMapping(value = "/users")
    public ResultBean<PageBean<FlwUser>> users(@RequestBody FlwUserQuery userQuery) {
        FlwUserQueryContext flwUserQueryContext = assigneeConvert.toQueryUsers(userQuery);
        PageBean<FlwUser> data = assigneeExecutor.queryUsers(flwUserQueryContext);
        return ResultBeanUtil.succ(data);
    }
}
