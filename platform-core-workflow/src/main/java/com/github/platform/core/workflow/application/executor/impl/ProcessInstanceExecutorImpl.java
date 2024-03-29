package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.cache.infra.constant.SequenceEnum;
import com.github.platform.core.cache.infra.utils.SequenceUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.application.constant.WorkflowApplicationEnum;
import com.github.platform.core.workflow.application.executor.IProcessInstanceExecutor;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.InstanceStatusEnum;
import com.github.platform.core.workflow.domain.constant.ProcessStatusEnum;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.ProcessInstanceContext;
import com.github.platform.core.workflow.domain.context.ProcessInstanceQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessRunContext;
import com.github.platform.core.workflow.domain.dto.ProcessDefinitionDto;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import com.github.platform.core.workflow.domain.gateway.IFormDataGateway;
import com.github.platform.core.workflow.domain.gateway.IProcessDefinitionGateway;
import com.github.platform.core.workflow.domain.gateway.IProcessInstanceGateway;
import com.github.platform.core.workflow.infra.convert.ProcessInstanceInfraConvert;
import com.github.platform.core.workflow.infra.service.IProcessInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
* 流程实例执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Service
@Slf4j
public class ProcessInstanceExecutorImpl extends BaseExecutor implements IProcessInstanceExecutor {
    @Resource
    private IProcessInstanceGateway gateway;
    @Resource
    private IProcessDefinitionGateway processManageGateway;
    @Resource(name = "flowableProcessInstanceService")
    private IProcessInstanceService processInstanceService;
    @Resource
    private IFormDataGateway formDataGateway;

    @Resource
    private ProcessInstanceInfraConvert flwProcessInstanceInfraConvert;
    @Override
    public PageBean<ProcessInstanceDto> query(ProcessInstanceQueryContext context){
        return gateway.query(context);
    };
    @Override
    public void insert(ProcessInstanceContext context){
        ProcessInstanceDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public ProcessInstanceDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void updateStatus(Long id, String reason) {
        ProcessInstanceDto dto = getProcessInstanceDto(id);
        InstanceStatusEnum statusEnum = processInstanceService.updateStatus(dto.getInstanceId(), reason);
        ProcessInstanceContext context = ProcessInstanceContext.builder().id(id).status(statusEnum.getStatus()).remark(reason).build();
        gateway.update(context);
    }

    @Override
    public void delete(Long id) {
        ProcessInstanceDto dto = getProcessInstanceDto(id);
        int d = gateway.delete(id);
        processInstanceService.delete(dto.getInstanceId(), LoginUserInfoUtil.getLoginName()+"删除流程实例！");
        if (d <=0 ){
            exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }

    @Override
    public void createProcessInstanceWithFormData(ProcessRunContext context, List<FormDataContext> formdataList) {
        // 生成实例业务号
        String instanceNo = SequenceUtil.nextSequenceNum(SequenceEnum.FLW_INSTANCE);
        context.setInstanceNo(instanceNo);
        context.setBizNo(instanceNo);
        this.createProcessInstance(context);
        formDataGateway.insertList(formdataList,instanceNo);
    }

    @Override
    public void createProcessInstance(ProcessRunContext context){
        if (log.isDebugEnabled()){
            log.debug("流程启动参数：{}", JsonUtils.toJson(context));
        }
        // 获取流程信息
        ProcessDefinitionDto definitionDto = processManageGateway.findByProcessNo(context.getProcessNo(), ProcessStatusEnum.ON.getStatus(),null);
        if (Objects.isNull(definitionDto)){
            log.warn("processNo:{} 流程定义为空",context.getProcessNo());
            exception(WorkflowApplicationEnum.PROCESS_DEFINITION_EMPTY);
        }
        if (!definitionDto.isOn()){
            log.warn("processNo:{} 流程定义状态未启用",context.getProcessNo());
            exception(WorkflowApplicationEnum.PROCESS_DEFINITION_EMPTY);
        }
        if (StringUtils.isEmpty(context.getInstanceNo())){
            context.setInstanceNo(SequenceUtil.nextSequenceNum(SequenceEnum.FLW_INSTANCE));
        }
        // 查询对应的业务编号是否有流程实例
        ProcessInstanceDto dto = gateway.findByBizNoAndProcessNo(context.getBizNo(),context.getProcessNo());
        if (Objects.nonNull(dto) && dto.isNormal()){
            log.warn("processNo:{} 流程实例已存在，轻核查",context.getProcessNo());
            exception(WorkflowApplicationEnum.PROCESS_INSTANCE_EXIST);
        }

        String instanceId = null;

        try {
            ProcessInstanceContext insertContext = flwProcessInstanceInfraConvert.toContext(context, null,context.getInstanceNo(),definitionDto.getProcessNo(), definitionDto.getProcessVersion());
            insertContext.setProcessType(definitionDto.getProcessType());
            //保存实例信息，因为在创建实例的时候会启动任务，任务里的监听有通过instanceNo 查询
            insertContext.setStatus(InstanceStatusEnum.INIT.getStatus());
            dto = gateway.insert(insertContext);

            context.putParam(FlwConstant.INSTANCE_NO,context.getInstanceNo());
            context.putParam(FlwConstant.INSTANCE_NAME,dto.getInstanceName());
            context.putParam(FlwConstant.BIZ_NO,dto.getBizNo());
            context.putParam(FlwConstant.PROCESS_TYPE,dto.getProcessType());
            context.putParam(FlwConstant.PROCESS_VERSION,dto.getProcessVersion());
            // 部署实例
            instanceId = processInstanceService.startProcessInstance(context);
            updateInstance(dto.getId(), instanceId, context.getBizNo(), InstanceStatusEnum.ACTIVE);
        } catch (Exception e) {
            log.error("instanceNo：{} 创建异常！",context.getInstanceNo(),e);
            if (StringUtils.isNotEmpty(instanceId)){
                processInstanceService.delete(instanceId,"创建流程实例失败删除流程！");
            }
            updateInstance(dto.getId(), null,context.getBizNo(),InstanceStatusEnum.ERROR);
            exception(WorkflowApplicationEnum.PROCESS_INSTANCE_ADD_FAIL,e);
        }
    }

    @Override
    public void suspend(Long id,String reason) {
        ProcessInstanceDto dto = getProcessInstanceDto(id);
        suspendProcess( reason, dto);
    }
    @Override
    public void suspend(String bizNo, String reason) {
        ProcessInstanceDto dto = getProcessInstanceDto(bizNo);
        suspendProcess(reason, dto);
    }
    private void suspendProcess( String reason, ProcessInstanceDto dto) {
        processInstanceService.suspend(dto.getInstanceId(), reason);
        ProcessInstanceContext context = ProcessInstanceContext.builder().id(dto.getId()).status(InstanceStatusEnum.SUSPEND.getStatus()).remark(reason).build();
        gateway.update(context);
    }
    @Override
    public void resume(Long id,String reason) {
        ProcessInstanceDto dto = getProcessInstanceDto(id);
        resumeProcess(reason, dto);
    }
    @Override
    public void resume(String bizNo, String reason) {
        ProcessInstanceDto dto = getProcessInstanceDto(bizNo);
        resumeProcess( reason, dto);
    }
    private void resumeProcess( String reason, ProcessInstanceDto dto) {
        processInstanceService.resume(dto.getInstanceId(), reason);
        ProcessInstanceContext context = ProcessInstanceContext.builder().id(dto.getId()).status(InstanceStatusEnum.ACTIVE.getStatus()).remark(reason).build();
        gateway.update(context);
    }
    @Override
    public void stop(Long id,String reason) {
        ProcessInstanceDto dto = getProcessInstanceDto(id);
        stopProcess(reason, dto);
    }

    @Override
    public void stop(String bizNo, String reason) {
        ProcessInstanceDto dto = getProcessInstanceDto(bizNo);
        stopProcess(reason, dto);
    }
    private void stopProcess(String reason, ProcessInstanceDto dto) {
        processInstanceService.stopInstance(dto.getInstanceId(), reason);
        ProcessInstanceContext context = ProcessInstanceContext.builder().id(dto.getId()).status(InstanceStatusEnum.CANCELLED.getStatus()).build();
        gateway.update(context);
    }
    private ProcessInstanceDto getProcessInstanceDto(String bizNo) {
        ProcessInstanceDto dto = gateway.findByBizNoAndProcessNo(bizNo,null);
        if (Objects.isNull(dto)){
            exception(WorkflowApplicationEnum.PROCESS_INSTANCE_NO_FOUND);
        }
        return dto;
    }
    private ProcessInstanceDto getProcessInstanceDto(Long id) {
        ProcessInstanceDto dto = gateway.findById(id);
        if (Objects.isNull(dto)){
            exception(WorkflowApplicationEnum.PROCESS_INSTANCE_NO_FOUND);
        }
        return dto;
    }

    private void updateInstance(Long id, String instanceId,String bizNo, InstanceStatusEnum statusEnum) {
        ProcessInstanceContext updateContext = ProcessInstanceContext.builder()
                .id(id)
                .instanceId(instanceId)
                .bizNo(bizNo)
                .status(statusEnum.getStatus())
                .build();
        gateway.update(updateContext);
    }
}
