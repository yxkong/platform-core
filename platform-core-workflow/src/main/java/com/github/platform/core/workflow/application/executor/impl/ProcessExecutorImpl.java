package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.gateway.ISysCommonGateway;
import com.github.platform.core.workflow.application.constant.WorkflowApplicationEnum;
import com.github.platform.core.workflow.application.executor.IProcessExecutor;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.context.ProcessDetailQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessQueryContext;
import com.github.platform.core.workflow.domain.dto.*;
import com.github.platform.core.workflow.domain.gateway.*;
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
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProcessExecutorImpl extends BaseExecutor implements IProcessExecutor {
    @Resource(name = "flowableProcessInstanceService")
    private IProcessInstanceService processInstanceService ;

    @Resource(name = "flowableProcessTaskService")
    private IProcessTaskService processTaskService;

    @Resource
    private IProcessInstanceGateway instanceGateway;
    @Resource
    private IProcessDefinitionGateway definitionGateway;
    @Resource
    private IProcessApprovalRecordGateway approvalRecordGateway;
    @Resource
    private IFormInfoGateway formInfoGateway;
    @Resource
    private ISysCommonGateway sysCommonGateway;
    @Autowired
    private  Map<String,ICustomFormDataGateway> formDataGatewayMap;

    @Override
    public PageBean<ProcessDto> queryTodo(ProcessQueryContext context) {
        List<ProcessDto> list = new ArrayList<>();
        if (!AuthUtil.isSuperAdmin()){
            context.setAssignee(LoginUserInfoUtil.getLoginName());
        }
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
    public String getCurrentTaskDefinitionKey(Set<String> actives, String instanceId, String loginName){
        Task activeTask = processTaskService.getUserActiveTask(instanceId, loginName);
        if (Objects.nonNull(activeTask)){
            return activeTask.getTaskDefinitionKey();
        }
        if (CollectionUtil.isEmpty(actives)){
            return null;
        }
        return  new ArrayList<>(actives).get(0);
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
        ProcessInstanceDto instanceDto = context.getInstanceDto();
        if (Objects.isNull(instanceDto) || StringUtils.isEmpty(instanceDto.getInstanceName())){
            if (StringUtils.isEmpty(context.getInstanceId())){
                exception(WorkflowApplicationEnum.INSTANCE_ID_IS_EMPTY);
            }
            instanceDto = instanceGateway.findByInstanceId(context.getInstanceId());
        }
        // 2，获取指定版本的流程图
        ProcessDefinitionDto processDefinitionDto = definitionGateway.findByProcessNo(instanceDto.getProcessNo(), instanceDto.getProcessVersion());
        rst.setBpmnXml(processDefinitionDto.getProcessFile());
        rst.setInstanceDto(instanceDto);
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(processDefinitionDto.getProcessFile());
        // 3，如果taskId存在，需要构建任务表单信息
        rst.setTaskStatus(getTaskStatus(bpmnModel, instanceDto.getInstanceId()));
        // 非项目流程场景，需要查询信息
        if (context.isNotPm()){
            /**查询审批记录*/
            rst.setApprovalRecords( getApprovalRecords(instanceDto));

            String formKey = BpmnModelUtils.getStartEventFormKey(bpmnModel);
            List<FormDataViewDto> formInfos  =new ArrayList<>();
            formInfos.add(new FormDataViewDto("流程表单信息", getFormData(instanceDto,true,formKey)));
            if (StringUtils.isNotEmpty(context.getTaskId())){
                // 获取当前任务的节点
                HistoricTaskInstance hisTask = processTaskService.getHisTask(context.getTaskId());
                formInfos.add(new FormDataViewDto("审批任务表单信息", getFormData(instanceDto,false,formKey)));
            }
            rst.setFormInfos(formInfos);

        }
        return rst;
    }
    @Override
    public Map<String, String> getTaskExtendProperty(String bizNo, String taskKey) {
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
            exception(WorkflowApplicationEnum.definition_no_found);
        }
        if (StringUtils.isBlank(definitionDto.getProcessFile())) {
            exception(WorkflowApplicationEnum.bpmn_is_null);
        }
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(definitionDto.getProcessFile());
        String formKey = BpmnModelUtils.getStartEventFormKey(bpmnModel);
        //查询form表单配置
        List<FormInfoDto> list = formInfoGateway.findByFromNo(getFormNo(formKey));
        if (CollectionUtil.isEmpty(list)){
            exception(WorkflowApplicationEnum.FORM_INFO__NOT_FOUND);
        }
        list.forEach(s->{
            if (s.isOption()){
                s.setOptions(sysCommonGateway.getOptionsByType(s.getOptionName()));
            }
        });
        return list;
    }

    /**
     * 组装表单数据
     * @param instanceDto
     * @param formKey
     * @return
     */
    private List<FormDataDto> getFormData(ProcessInstanceDto instanceDto, boolean isProcess, String formKey){
        List<FormDataDto> rst = new ArrayList<>();
        if (instanceDto.isPm() && !isProcess){
            return rst;
        }
        ProcessTypeEnum processTypeEnum = ProcessTypeEnum.get(instanceDto.getProcessType());
        if (instanceDto.isPm()){
            formKey = FlwConstant.PM_FORM_KEY ;
        } else {
            formKey = getFormNo(formKey);
        }
        ICustomFormDataGateway formDataGateway = formDataGatewayMap.get(processTypeEnum.getFormBean());

        return formDataGateway.findFormData(instanceDto.getBizNo(),instanceDto.getInstanceNo(),formKey);
    }
    private String getFormNo(String formKey){
        if (StringUtils.isNotEmpty(formKey)){
            return formKey.replace("key_","");
        }
        return null;
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

    private Map<String,List<FormInfoDto>> getFormData(BpmnModel bpmnModel){
        Map<String,List<FormInfoDto>> rst = new HashMap<>();
        Collection<UserTask> userTasks = BpmnModelUtils.getAllUserTaskEvent(bpmnModel);
        if (CollectionUtil.isNotEmpty(userTasks)){
            userTasks.forEach(s->{
                String name = StringUtils.isNotEmpty(s.getName()) ? s.getName(): s.getId();
                rst.put(name,formInfoGateway.findByFromNo(s.getFormKey()));
            });
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
