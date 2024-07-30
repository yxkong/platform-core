package com.github.platform.core.workflow.adapter.api.controller;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.workflow.adapter.api.command.ProcessDetailQuery;
import com.github.platform.core.workflow.adapter.api.command.ProcessQuery;
import com.github.platform.core.workflow.adapter.api.command.ProcessRunCmd;
import com.github.platform.core.workflow.adapter.api.convert.FormAdapterConvert;
import com.github.platform.core.workflow.adapter.api.convert.ProcessAdapterConvert;
import com.github.platform.core.workflow.adapter.api.convert.ProcessInstanceAdapterConvert;
import com.github.platform.core.workflow.application.executor.IFormInfoExecutor;
import com.github.platform.core.workflow.application.executor.IProcessDefinitionExecutor;
import com.github.platform.core.workflow.application.executor.IProcessExecutor;
import com.github.platform.core.workflow.application.executor.IProcessInstanceExecutor;
import com.github.platform.core.workflow.domain.context.ProcessDetailQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessRunContext;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import com.github.platform.core.workflow.domain.dto.ProcessDetailDto;
import com.github.platform.core.workflow.domain.dto.ProcessDto;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程处理
 * @author: yxkong
 * @date: 2023/11/14 11:01
 * @version: 1.0
 */
@RestController
@Tag(name = "process",description = "流程处理")
@RequestMapping("/api/workflow/process")
@Slf4j
public class ProcessController extends BaseController {
    @Resource
    private ProcessAdapterConvert convert;
    @Resource
    private IProcessExecutor executor;
    @Resource
    private IProcessInstanceExecutor instanceExecutor;
    @Resource
    private ProcessInstanceAdapterConvert instanceAdapterConvert;
    @Resource
    private FormAdapterConvert formAdapterConvert;
    @Resource
    private IFormInfoExecutor formInfoExecutor;

    @OptLog(module="processInstance",title="创建流程实例",optType = LogOptTypeEnum.modify)
    @Operation(summary = "创建流程实例",tags = {"processInstance"})
    @PostMapping("/create")
    public ResultBean<Void> create(@Validated @RequestBody ProcessRunCmd cmd) {
        ProcessRunContext processRunContext = instanceAdapterConvert.toProcessRunContext(cmd);
        processRunContext.setInitiator(LoginUserInfoUtil.getLoginName());
        processRunContext.setTenantId(LoginUserInfoUtil.getTenantId());
        processRunContext.setInstanceName(LoginUserInfoUtil.getLoginName()+"的"+ processRunContext.getInstanceName());
        List<FormInfoDto> formInfos = formInfoExecutor.findByFromNo(cmd.getFormNo());
        instanceExecutor.createProcessInstanceWithFormData(processRunContext,formAdapterConvert.toDatas(formInfos,cmd.getFormData()));
        return buildSucResp();
    }
    @OptLog(module="processInstance",title="创建流程查询",optType = LogOptTypeEnum.query)
    @Operation(summary = "创建流程查询",tags = {"processInstance"})
    @PostMapping("/createQuery")
    public ResultBean<List<FormInfoDto>> createQuery(@RequestBody ProcessQuery query){
        if (StringUtils.isEmpty(query.getProcessNo())){
            throw exception(ResultStatusEnum.PARAM_EMPTY.getStatus(),"processNo不能为空");
        }
        List<FormInfoDto> list = executor.createQuery(query.getProcessNo());
        return buildSucResp(list);
    }
    @OptLog(module="process",title="查询待办流程列表",persistent = false)
    @Operation(summary = "查询待办流程列表",tags = {"process"})
    @PostMapping("/queryTodo")
    public ResultBean<PageBean<ProcessDto>> queryTodo(@RequestBody ProcessQuery query){
        ProcessQueryContext context = convert.toQueryContext(query);
        PageBean<ProcessDto> pageBean = executor.queryTodo(context);
        return buildSucResp(pageBean);
    }

    @OptLog(module="process",title="查询流程详情",persistent = false)
    @Operation(summary = "查询流程详情",tags = {"process"})
    @PostMapping("/detail")
    public ResultBean<ProcessDetailDto> detail(@RequestBody ProcessDetailQuery query){
        ProcessDetailQueryContext context = convert.toQueryDetailContext(query);
        ProcessDetailDto detailDto = executor.queryDetail(context);
        detailDto.setInstanceDto(null);
        return buildSucResp(detailDto);
    }
}
