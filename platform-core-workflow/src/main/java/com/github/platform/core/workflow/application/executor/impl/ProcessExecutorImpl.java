package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.workflow.application.constant.WorkflowApplicationEnum;
import com.github.platform.core.workflow.application.executor.IFormDataExecutor;
import com.github.platform.core.workflow.application.executor.IProcessExecutor;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.context.ProcessDetailQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessQueryContext;
import com.github.platform.core.workflow.domain.dto.*;
import com.github.platform.core.workflow.domain.gateway.IFormInfoGateway;
import com.github.platform.core.workflow.domain.gateway.IProcessApprovalRecordGateway;
import com.github.platform.core.workflow.domain.gateway.IProcessDefinitionGateway;
import com.github.platform.core.workflow.domain.gateway.IProcessInstanceGateway;
import com.github.platform.core.workflow.infra.service.IProcessInstanceService;
import com.github.platform.core.workflow.infra.service.IProcessTaskService;
import com.github.platform.core.workflow.infra.util.BpmnModelUtils;
import com.github.platform.core.workflow.infra.util.FlowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
* 流程执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Service
@Slf4j
public class ProcessExecutorImpl extends SysExecutor implements IProcessExecutor {
    @Resource(name = "flowableProcessInstanceService")
    private IProcessInstanceService processInstanceService ;

    @Resource(name = "flowableProcessTaskService")
    private IProcessTaskService processTaskService;
    @Resource
    private IFormDataExecutor formDataExecutor;

    @Resource
    private IProcessInstanceGateway instanceGateway;
    @Resource
    private IProcessDefinitionGateway definitionGateway;
    @Resource
    private IProcessApprovalRecordGateway approvalRecordGateway;
    @Resource
    private IFormInfoGateway formInfoGateway;

    @Override
    public PageBean<ProcessDto> queryTodo(ProcessQueryContext context) {
        List<ProcessDto> list = new ArrayList<>();
        context.setTenantId(getTenantId(context));
        Pair<Long,List<Task>> pair = processInstanceService.queryTodo(context);
        if (Objects.isNull(pair.getKey()) || PageBean.EMPTY_TOTAL_SIZE.equals(pair.getKey())){
            return new PageBean<>(PageBean.EMPTY_PAGE_NUM, PageBean.EMPTY_TOTAL_SIZE,PageBean.EMPTY_PAGE_SIZE,list) ;
        }
        pair.getValue().forEach(s->{
            ProcessDto dto = new ProcessDto();
            //流程实例名称+流程版本
            LocalDateTime taskCreate = LocalDateTimeUtil.dateToLocalDateTime(s.getCreateTime());
            dto.setTaskId(s.getId());
            dto.setTaskKey(s.getTaskDefinitionKey());
            dto.setCreateTime(taskCreate);
            dto.setDuration(LocalDateTimeUtil.formatCurrentDurationAsString(taskCreate));
            dto.setTaskName(s.getName());
            dto.setInstanceId(s.getProcessInstanceId());
            dto.setAssignee(s.getAssignee());
            Map<String, Object> variables = s.getProcessVariables();
            dto.setProcessNo(getValue(variables,FlwConstant.PROCESS_NO));
            dto.setProcessType(getValue(variables,FlwConstant.PROCESS_TYPE));
            dto.setBizNo(getValue(variables,FlwConstant.BIZ_NO));
            dto.setInstanceName(getValue(variables,FlwConstant.INSTANCE_NAME));
            dto.setInstanceNo(getValue(variables,FlwConstant.INSTANCE_NO));
            dto.setVersion("V"+getValue(variables,FlwConstant.PROCESS_VERSION));
            //发起人
            dto.setInitiator(getValue(variables,FlwConstant.ASSIGNEE_INITIATOR));
            if (StringUtils.isNotEmpty(s.getTenantId())){
                 dto.setTenantId(Integer.parseInt(s.getTenantId()));
            }
            list.add(dto);
        });
        return new PageBean<>(context.getPageNum(),pair.getKey(),context.getPageSize(),list) ;
    }
    private String getValue(Map<String, Object> variables,String key){
        if (CollectionUtil.isEmpty(variables)){
            return null;
        }
        Object o = variables.get(key);
        if (Objects.isNull(o)){
            return null;
        }
        return String.valueOf(o);
    }

    @Override
    public ProcessDetailDto queryDetail(String bizNo){
        ProcessInstanceDto instanceDto = instanceGateway.findByBizNoAndProcessNo(bizNo,null);
        ProcessDetailQueryContext context = new ProcessDetailQueryContext();
        context.setInstanceDto(instanceDto);
        context.setScene(1);
        return queryDetail(context);
    }
    @Override
    public ProcessTaskDto getCurrentTaskDefinitionKey(Set<String> actives, String instanceId, String loginName){
        Task task = processTaskService.getUserActiveTask(instanceId, loginName);
        ProcessTaskDto.ProcessTaskDtoBuilder builder = ProcessTaskDto.builder();
        if (Objects.nonNull(task)){
            return builder.taskId(task.getId()).taskDefinitionKey(task.getTaskDefinitionKey()).formKey(task.getFormKey()).name(task.getName()).build();
        }
        return builder.taskDefinitionKey( CollectionUtil.isEmpty(actives)? null : new ArrayList<>(actives).get(0)).build() ;
    }

    @Override
    public String getCandidate(String bizNo, String taskKey, String assigneeType){
        ProcessInstanceDto instanceDto = instanceGateway.findByBizNoAndProcessNo(bizNo,null);
        ProcessDefinitionDto processDefinitionDto = definitionGateway.findByProcessNo(instanceDto.getProcessNo(), instanceDto.getProcessVersion());
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(processDefinitionDto.getProcessFile());
        UserTask userTask = BpmnModelUtils.getUserTaskByKey(bpmnModel, taskKey);
        if (Objects.nonNull(userTask)){
            if (FlwConstant.ASSIGNEE_USERS.equals(assigneeType)){
                return String.join(SymbolConstant.comma,userTask.getCandidateUsers());
            }
            return String.join(SymbolConstant.comma,userTask.getCandidateGroups());
        }
        return null;
    }

    @Override
    public ProcessDetailDto queryDetail(ProcessDetailQueryContext context) {
        ProcessDetailDto rst = new ProcessDetailDto();
        // 1,流程实例信息
        ProcessInstanceDto instanceDto = getInstanceDto(context);
        // 2，获取指定版本的流程图
        ProcessDefinitionDto processDefinitionDto = definitionGateway.findByProcessNo(instanceDto.getProcessNo(), instanceDto.getProcessVersion());
        rst.setBpmnXml(processDefinitionDto.getProcessFile());
        rst.setInstanceDto(instanceDto);
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(processDefinitionDto.getProcessFile());
        //缓存
        rst.setBpmnModel(bpmnModel);
        // 3，如果taskId存在，需要构建任务表单信息
        rst.setTaskStatus(getTaskStatus(bpmnModel, instanceDto.getInstanceId()));
        // 查询流程的formKey
        String formKey = BpmnModelUtils.getStartEventFormKey(bpmnModel);
        // 非项目流程场景，需要查询信息,项目流程需要查询流程表单信息
        List<FormViewAssemblyDto> formInfos  =new ArrayList<>();

        /**非项目流程的获取所有表单*/
        if (context.isNotPm()){
            /**查询审批记录*/
            rst.setApprovalRecords( getApprovalRecords(instanceDto));
            formInfos.add(new FormViewAssemblyDto("流程主表单", formDataExecutor.getFormInfoWithData(instanceDto, true, formKey)));
            //当前任务节点
            Task task = processTaskService.getTask(context.getTaskId());

            /**查询所有的表单*/
            List<BpmnModelUtils.FormKey> allUserTaskFormKey = BpmnModelUtils.getAllUserTaskFormKey(bpmnModel);
            Set<String> finishedTasks = rst.getTaskStatus().getFinishedTasks();

            allUserTaskFormKey.forEach(s ->{
                boolean flag = Objects.nonNull(task) && Objects.equals(task.getTaskDefinitionKey(), s.getNodeKey());
                if (finishedTasks.contains(s.getNodeKey()) || flag){
                    if (flag){
                        rst.setTaskFormInfo(new FormViewAssemblyDto(s.getName() ,formDataExecutor.getFormInfoWithData(instanceDto, false, s.getFormKey())));
                    } else {
                        formInfos.add(new FormViewAssemblyDto(s.getName() ,formDataExecutor.getFormInfoWithData(instanceDto, false, s.getFormKey())));
                    }
                }
            });
        }
        rst.setFormInfos(formInfos);
        return rst;
    }
    private ProcessInstanceDto getInstanceDto(ProcessDetailQueryContext context) {
        ProcessInstanceDto instanceDto = context.getInstanceDto();
        if (Objects.isNull(instanceDto) || StringUtils.isBlank(instanceDto.getInstanceName())){
            if (StringUtils.isBlank(context.getInstanceId())){
                throw  exception(WorkflowApplicationEnum.INSTANCE_ID_IS_EMPTY);
            }
            instanceDto = instanceGateway.findByInstanceId(context.getInstanceId());
        }
        return instanceDto;
    }

    @Override
    public Map<String, String> getNodeExtendProperty(String bizNo, String taskKey) {
        ProcessInstanceDto instanceDto = instanceGateway.findByBizNoAndProcessNo(bizNo, null);
        // 获取指定版本的流程图
        ProcessDefinitionDto processDefinitionDto = definitionGateway.findByProcessNo(instanceDto.getProcessNo(), instanceDto.getProcessVersion());
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(processDefinitionDto.getProcessFile());
        return BpmnModelUtils.getElementAllAttribute(bpmnModel,taskKey);
    }

    @Override
    public List<FormInfoDto> createQuery(String processNo) {
        ProcessDefinitionDto definitionDto = definitionGateway.findLatestByProcessNo(processNo);
        if (Objects.isNull(definitionDto)){
            throw exception(WorkflowApplicationEnum.definition_no_found);
        }
        if (StringUtils.isBlank(definitionDto.getProcessFile())) {
            throw exception(WorkflowApplicationEnum.bpmn_is_null);
        }
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(definitionDto.getProcessFile());
        String formKey = BpmnModelUtils.getStartEventFormKey(bpmnModel);
        //查询form表单配置
        List<FormInfoDto> list = formInfoGateway.findByFromNoWithDict(BpmnModelUtils.getFormNo(formKey));
        if (CollectionUtil.isEmpty(list)){
            throw exception(WorkflowApplicationEnum.FORM_INFO__NOT_FOUND);
        }
        return list;
    }


    /**
     * 查询审批记录
     * @param instanceDto
     * @return
     */
    private List<ProcessApprovalRecordDto> getApprovalRecords(ProcessInstanceDto instanceDto) {
        List<ProcessApprovalRecordDto> rst = new ArrayList<>();
        rst.add(getStartNode(instanceDto));
        List<ProcessApprovalRecordDto> list = approvalRecordGateway.findByInstanceId(instanceDto.getInstanceId());
        rst.addAll(list);
        ProcessApprovalRecordDto endNode = getEndNode(instanceDto);
        if (Objects.nonNull(endNode)){
            rst.add(endNode);
        }
        return rst;
    }
    private ProcessApprovalRecordDto getStartNode(ProcessInstanceDto instanceDto){
        return ProcessApprovalRecordDto.builder()
                .nodeType(FlwConstant.NODE_TYPE_START)
                .taskName(FlwConstant.TASK_NAME_START)
                .candidate(instanceDto.getCreateBy())
                .enAssignee(instanceDto.getCreateBy())
                .startTime(instanceDto.getCreateTime())
                .endTime(instanceDto.getCreateTime())
                .comment(LocalDateTimeUtil.dateTime(instanceDto.getCreateTime(),LocalDateTimeUtil.DEFAULT_CN_SHORT_DATETIME_FORMATTER) +"发起了任务")
                .build();
    }
    private ProcessApprovalRecordDto getEndNode(ProcessInstanceDto instanceDto){
        if (Objects.isNull(instanceDto.getEndTime())){
            return null;
        }
        return ProcessApprovalRecordDto.builder()
                .nodeType(FlwConstant.NODE_TYPE_END)
                .taskName(FlwConstant.TASK_NAME_END)
                .startTime(instanceDto.getEndTime())
                .endTime(instanceDto.getEndTime())
                .comment(LocalDateTimeUtil.dateTime(instanceDto.getCreateTime(),LocalDateTimeUtil.DEFAULT_CN_SHORT_DATETIME_FORMATTER) +"完成了任务")
                .build();
    }

    /**
     * 获取任务状态
     * @param bpmnModel
     * @param instanceId
     * @return
     */
    private ProcessTaskStatusDto getTaskStatus(BpmnModel bpmnModel, String instanceId){
        ProcessTaskStatusDto dto = new ProcessTaskStatusDto();
        List<HistoricActivityInstance> list = processTaskService.queryHisActivity(instanceId);
        if (CollectionUtil.isEmpty(list)){
            return dto;
        }
        //已完成的
        List<HistoricActivityInstance> finishedCollect = list.stream().filter(s -> Objects.nonNull(s.getEndTime())).collect(Collectors.toList());
        // 所有已完成的连线
        Set<String> finishedSequenceFlows = new HashSet<>();
        // 所有已完成的任务节点
        Set<String> finishedTasks = new HashSet<>();
        finishedCollect.forEach(s->{
            if (BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW.equals(s.getActivityType())){
                finishedSequenceFlows.add(s.getActivityId());
            } else {
                finishedTasks.add(s.getActivityId());
            }
        });
        // 归集未结束的节点
        Set<String> activeTasks = list.stream().filter(s -> Objects.isNull(s.getEndTime())).map(HistoricActivityInstance::getActivityId).collect(Collectors.toSet());
        Set<String> rejectedTasks = FlowableUtil.dfsFindRejects(bpmnModel, activeTasks, finishedSequenceFlows, finishedTasks);
        return new ProcessTaskStatusDto(finishedTasks,finishedSequenceFlows,activeTasks,rejectedTasks);
    }
}
