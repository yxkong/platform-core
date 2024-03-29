package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.cache.infra.constant.SequenceEnum;
import com.github.platform.core.cache.infra.utils.SequenceUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.application.constant.WorkflowApplicationEnum;
import com.github.platform.core.workflow.application.executor.IProcessDefinitionExecutor;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.ProcessListenerEnum;
import com.github.platform.core.workflow.domain.constant.ProcessStatusEnum;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionContext;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessDefinitionDto;
import com.github.platform.core.workflow.domain.gateway.IProcessDefinitionGateway;
import com.github.platform.core.workflow.infra.convert.ProcessManageInfraConvert;
import com.github.platform.core.workflow.infra.service.IProcessDefinitionService;
import com.github.platform.core.workflow.infra.util.BpmnModelUtils;
import com.github.platform.core.standard.constant.DeleteEnum;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* 流程管理执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@Service
@Slf4j
public class ProcessDefinitionExecutorImpl extends BaseExecutor implements IProcessDefinitionExecutor {
    @Resource
    private IProcessDefinitionGateway gateway;
    @Resource
    private ProcessManageInfraConvert convert;
    @Resource(name = "flowableManagerService")
    private IProcessDefinitionService processManagerService;
    @Override
    public PageBean<ProcessDefinitionDto> query(ProcessDefinitionQueryContext context){
        return gateway.query(context);
    };
    @Override
    public PageBean<ProcessDefinitionDto> queryHistory(ProcessDefinitionQueryContext context){
        return gateway.queryHistory(context);
    };
    @Override
    public void insert(ProcessDefinitionContext context){
        context.setProcessNo(SequenceUtil.nextSequenceNum(SequenceEnum.FLW));
        //同一个租户下只允许一个类型的流程
        ProcessDefinitionDto processManageDto = gateway.findByProcessNo(context.getProcessNo(), context.getTenantId(),null);
        if (Objects.nonNull(processManageDto)){
            exception(WorkflowApplicationEnum.PROCESS_DEFINITION_EXIST_PROCESS_TYPE);
        }
        ProcessDefinitionDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public ProcessDefinitionDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void designAndUpdate(ProcessDefinitionContext context){

        if (!context.validateUpdateProcessFile()){
            exception(WorkflowApplicationEnum.update_process_file_validate);
        }
        // 1.判断是否可编辑（根据审批流编号processNo，归属的租户，查询未删除且草稿的审批流）
        ProcessDefinitionDto processManageDto = gateway.findById(context.getId());
        if (processManageDto == null) {
            exception(WorkflowApplicationEnum.bpmn_is_null);
        }
        if(!processManageDto.isDraft()){
            exception(WorkflowApplicationEnum.cannot_modify);
        }

            // 2.判断文件是否合法
        checkProcessFile(context.getProcessFile());
        // 3.更新数据
        ProcessDefinitionContext updateContext = ProcessDefinitionContext.builder().id(context.getId()).processFile(context.getProcessFile()).build();
        gateway.update(updateContext);
    }
    @Override
    public void update(ProcessDefinitionContext context) {
        context.setProcessFile(null);
        context.setTenantId(LoginUserInfoUtil.getTenantId());
        Pair<Boolean, ProcessDefinitionDto> update = gateway.update(context);
        if (!update.getKey()){
            exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    @Override
    public void delete(Long id) {
        ProcessDefinitionDto dto = gateway.findById(id);
        if (!dto.isDraft()){
            exception(WorkflowApplicationEnum.delete_not_draft);
        }
        ProcessDefinitionContext context = ProcessDefinitionContext.builder().id(id).tenantId(LoginUserInfoUtil.getTenantId()).deleted(DeleteEnum.YES.getStatus()).build();
        gateway.update(context);
    }

    @Override
    public void startProcessById(Long id) {
        ProcessDefinitionDto dto = gateway.findById(id);
        if (dto.isOn()){
            exception(WorkflowApplicationEnum.start_is_on);
        }
        String xmlFile = processXmlFile(dto);
        if (StringUtils.isEmpty(xmlFile)){
            exception(WorkflowApplicationEnum.start_file_illegality);
        }
        ProcessDefinitionContext context = ProcessDefinitionContext.builder().id(id)
                .status(ProcessStatusEnum.ON.getStatus())
                .processFile(xmlFile)
                .build();
        //审批流部署 ， 部署时processName需要加 bpmn 才能正常启动流程（ 因为部署使用addString的第一个参数必须有bpmn）
        String deployId = processManagerService.deploy(dto.getProcessType(),dto.getProcessNo(),dto.getVersion(), dto.getProcessName(), xmlFile);
        context.setDeployId(deployId);
        gateway.update(context);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopProcessById(Long id) {
        ProcessDefinitionDto dto = gateway.findById(id);
        //更新流程为禁用,并设置为不是最新的工作流
        ProcessDefinitionContext context = convert.toContext(dto);
        context.setStatus(ProcessStatusEnum.OFF.getStatus());
        gateway.update(context);
        //本地流程的停止，不能调用流程引擎，否则会将正在跑的任务停掉
//        processManagerService.updateState(dto.getDeployId(), ProcessStatusEnum.OFF);

        //创建一个新的同样processNo的工作流的草稿，并且设置版本为+1
        context.setIdNull();
        context.setDeployId(null);
        context.setProcessVersion(Objects.isNull(context.getProcessVersion())?1:context.getProcessVersion()+1 );
        context.setStatus(ProcessStatusEnum.DRAFT.getStatus());
        gateway.insert(context);
    }

    @Override
    public void rest(Long id) {
        ProcessDefinitionDto dto = gateway.findById(id);
        //查询最新，更改为挂起
        ProcessDefinitionDto lastDto =  gateway.findLatestByProcessNo(dto.getProcessNo());
        //更新流程为禁用,并设置为不是最新的工作流
        ProcessDefinitionContext context = convert.toContext(lastDto);
        context.setStatus(ProcessStatusEnum.OFF.getStatus());
        gateway.update(context);
        //将指定流程更新为最新
        context = convert.toContext(dto);
        context.setIdNull();
        context.setDeployId(null);
        context.setProcessVersion(Objects.isNull(lastDto.getProcessVersion())?1:lastDto.getProcessVersion()+1 );
        context.setStatus(ProcessStatusEnum.DRAFT.getStatus());
        context.setRemark("从"+dto.getVersion()+"版本恢复");
        gateway.insert(context);
    }
    private BpmnModel getBpmnModel(String processFile) {
        if (StringUtils.isBlank(processFile)) {
            exception(WorkflowApplicationEnum.bpmn_is_null);
        }
        BpmnModel  bpmnModel = null;
        try {
            bpmnModel = BpmnModelUtils.getBpmnModel(processFile);
        } catch (Exception e) {
            exception(WorkflowApplicationEnum.bpmn_parser_error,e);
        }
        return bpmnModel;
    }
    /**
     * 审批流文件是否合法（是否成功解析出 BpmnModel）
     *
     * @param processFile 审批流文件
     * @return
     */
    private void checkProcessFile(String processFile) {
        // 1. 是否成功转成bpmnModel
        BpmnModel bpmnModel = getBpmnModel(processFile);
        // 2. process仅支持一个
        List<Process> processes = bpmnModel.getProcesses();
        if (CollectionUtil.isEmpty(processes)) {
            exception(WorkflowApplicationEnum.bpmn_size_error);
        }
        // 开始节点相关校验
        validateStartEvent(bpmnModel);
        validateUserTasks(bpmnModel);
        validateGateways(bpmnModel);
        // 结束节点相关校验
        validateEndEvent(bpmnModel);
    }
    private void validateStartEvent(BpmnModel bpmnModel) {
        Collection<StartEvent> startEvents = BpmnModelUtils.getAllStartEvent(bpmnModel);
        if (CollectionUtil.isEmpty(startEvents)) {
            exception(WorkflowApplicationEnum.require_start);
        }
        if (startEvents.size() > 1) {
            exception(WorkflowApplicationEnum.require_start_only_one);
        }
        // 开始节点只有一条流出线
        for (StartEvent startEvent : startEvents) {
            List<SequenceFlow> outgoingFlows = BpmnModelUtils.getElementOutgoingFlows(startEvent);
            if (outgoingFlows.size() != 1) {
                exception(WorkflowApplicationEnum.require_start_only_out_one);
            }
        }
    }
    private void validateEndEvent(BpmnModel bpmnModel) {
        Collection<EndEvent> endEvents = BpmnModelUtils.getAllEndEvent(bpmnModel);
        if (CollectionUtil.isEmpty(endEvents)) {
            exception(WorkflowApplicationEnum.require_end_only_one);
        }
        // 结束节点必须有流入线
        for (EndEvent endEvent : endEvents) {
            List<SequenceFlow> incomingFlows = BpmnModelUtils.getElementIncomingFlows(endEvent);
            if (incomingFlows.isEmpty()) {
                exception(WorkflowApplicationEnum.require_end_only_in_one);
            }
        }
    }
    /**
     * Usertask 校验
     * @param bpmnModel
     */
    private void validateUserTasks(BpmnModel bpmnModel) {
        Collection<UserTask> userTasks = BpmnModelUtils.getAllUserTaskEvent(bpmnModel);
        if (CollectionUtil.isEmpty(userTasks)) {
            exception(WorkflowApplicationEnum.bpmn_user_task_error);
        }
        for (UserTask userTask : userTasks) {
            if (userTask.getAttributes().get(FlwConstant.ASSIGNEE_TYPE) == null){
                exception(WorkflowApplicationEnum.bpmn_assignee_error1).format(userTask.getName());
            }

            ExtensionAttribute assigneeType = userTask.getAttributes().get(FlwConstant.ASSIGNEE_TYPE).get(0);
            if (FlwConstant.ASSIGNEE_USERS.equals(assigneeType.getValue())){
                //approverType="USERS" flowable:candidateUsers="test01,xxx" flowable:text="测试,xxx"
                // 当为多用户时 assignee 值是 ${assignee} 且 candidateUsers至少有数据。否则，assignee应为具体用户
                if (StringUtils.isBlank(userTask.getAssignee()) ||(FlwConstant.USER_TASK_ASSIGNEE.equals(userTask.getAssignee())&& userTask.getCandidateUsers().size() == 0)) {
                    exception(WorkflowApplicationEnum.bpmn_assignee_error1).format(userTask.getName());
                }
            } else if (FlwConstant.ASSIGNEE_ROLES.equals(assigneeType.getValue())){
                //approverType="ROLES" flowable:assignee="${assignee}" flowable:candidateGroups="ROLE8,ROLE5">
                if (FlwConstant.USER_TASK_ASSIGNEE.equals(userTask.getAssignee()) && userTask.getCandidateGroups().size() == 0){
                    exception(WorkflowApplicationEnum.bpmn_assignee_error2).format(userTask.getName());
                }
            } else if (FlwConstant.ASSIGNEE_DEPT.equals(assigneeType.getValue())){
                if (FlwConstant.USER_TASK_ASSIGNEE.equals(userTask.getAssignee()) && userTask.getCandidateGroups().size() == 0){
                    exception(WorkflowApplicationEnum.bpmn_assignee_error3);
                }
            } else if (FlwConstant.ASSIGNEE_INITIATOR.equals(assigneeType.getValue())){
                if (FlwConstant.USER_TASK_ASSIGNEE.equals(userTask.getAssignee()) && userTask.getCandidateGroups().size() == 0){
                    exception(WorkflowApplicationEnum.bpmn_assignee_error4).format(userTask.getName());
                }
            }

            // 流入线检查
            List<SequenceFlow> incomingFlows = BpmnModelUtils.getElementIncomingFlows(userTask);
            if (CollectionUtil.isEmpty(incomingFlows)) {
                exception(WorkflowApplicationEnum.incoming_flows).format(userTask.getId());
            }
            // 流出线检查
            List<SequenceFlow> outgoingFlows = BpmnModelUtils.getElementOutgoingFlows(userTask);
            if (outgoingFlows.size() <= 0 ) {
                exception(WorkflowApplicationEnum.outgoing_flows).format(userTask.getId());
            }
        }
    }


    /**
     * 条件网关校验
     * @param bpmnModel
     */
    private void validateGateways(BpmnModel bpmnModel) {
        // 4. 条件网关必须配置
        List<ExclusiveGateway> gateways = BpmnModelUtils.getAllExclusiveGateways(bpmnModel);
        for (ExclusiveGateway gateway : gateways) {
            Collection<SequenceFlow> outgoings = BpmnModelUtils.getElementOutgoingFlows(gateway);
            if (outgoings.size() < 2) {
                exception(WorkflowApplicationEnum.require_exclusive_gateway);
            }
            for (SequenceFlow sequenceFlow : outgoings) {
                if (StringUtils.isEmpty(sequenceFlow.getConditionExpression())) {
                    exception(WorkflowApplicationEnum.sequence_not_null);
                }
            }
        }
    }




    /**
     * 处理审批流文件
     * 1，校验xml
     * 2，添加监听，如果前端业务已经添加了，不再添加
     * @param dto
     * @return
     */
    private String processXmlFile(ProcessDefinitionDto dto) {
        //1. xml字符串转BpmnModel
        BpmnModel bpmnModel = getBpmnModel(dto.getProcessFile());
        if (Objects.isNull(bpmnModel)) {
            exception(WorkflowApplicationEnum.start_file_illegality1);
        }
        //2. 审批流更改id及name
        List<Process> processes = bpmnModel.getProcesses();
        if (CollectionUtils.isEmpty(processes) || processes.size() > 1) {
            exception(WorkflowApplicationEnum.start_file_illegality2);
        }
        Process process = processes.get(0);
        process.setId(dto.getProcessNo());
        process.setName(dto.getProcessName());
        //3. UserTask新增taskListener
        processUserTasks(dto, bpmnModel);
        // 4. EndEvent 结束事件新增executionListener
        Collection<EndEvent> endEvents = BpmnModelUtils.getAllEndEvent(bpmnModel);
        if (null == endEvents) {
            exception(WorkflowApplicationEnum.start_file_illegality6);
        }
        endEvents.forEach(endEvent -> {
            //给结束节点添加监听
            endEvent.setExecutionListeners(addFlowableListener(endEvent.getExecutionListeners(), ProcessListenerEnum.END_TASK_START));
        });
        //5. bpmnModel 转 str
        String xmlfile = BpmnModelUtils.getBpmnXmlStr(bpmnModel);
        //6. 替换<![CDATA[  及  ]]>
        xmlfile = xmlfile.replace("<![CDATA[", "").replace("]]>", "");
        //解决xml.sax 报Message: 在实体引用中, 实体名称必须紧跟在 '&' 后面。
        xmlfile = xmlfile.replaceAll("&&","&amp;&amp;");
        return xmlfile;
    }
    private void processUserTasks(ProcessDefinitionDto dto, BpmnModel bpmnModel) {
        Collection<UserTask> userTasks = BpmnModelUtils.getAllUserTaskEvent(bpmnModel);
        for (UserTask userTask : userTasks) {
            ExtensionAttribute assigneeType = userTask.getAttributes().get(FlwConstant.ASSIGNEE_TYPE).get(0);
            if (FlwConstant.ASSIGNEE_USERS.equals(assigneeType.getValue())){
                // 当为多用户时 assignee 值是 ${assignee} 且 candidateUsers至少有数据。否则，assignee应为具体用户
                if (StringUtils.isEmpty(userTask.getAssignee())) {
                    exception(WorkflowApplicationEnum.start_file_illegality3).format(userTask.getName());
                }
                //approverType="USERS" flowable:candidateUsers="test01,xxx" flowable:text="测试,xxx"
                // 当为多用户时 assignee 值是 ${assignee} 且 candidateUsers至少有数据。否则，assignee应为具体用户
                int length = BpmnModelUtils.getCandidateUsersLength(userTask);
                if (userTask.getAssignee().equals(FlwConstant.USER_TASK_ASSIGNEE) && (length == 0 || length > FlwConstant.USER_TASK_CANDIDATEUSERS_LENGTH)){
                    //多用户时，如果length 为0 ，直接拦截
                    if (length > FlwConstant.USER_TASK_CANDIDATEUSERS_LENGTH){
                        log.error("流程：{} 名称:{} 多用户设置过多，导致长度过长,直接拦截", dto.getProcessNo(), dto.getProcessName());
                    }
                    exception(WorkflowApplicationEnum.start_file_illegality4).format(userTask.getName());
                }
                //给用户任务添加监听
//                userTask.setTaskListeners(addFlowableListener(userTask.getTaskListeners(),  ProcessListenerEnum.USER_TASK_CREATE));
            } else if (FlwConstant.ASSIGNEE_ROLES.equals(assigneeType.getValue())){
                //dataType="ROLES" flowable:assignee="${assignee}" flowable:candidateGroups="ROLE8,ROLE5">
                if (userTask.getCandidateGroups().size() == 0){
                    exception(WorkflowApplicationEnum.start_file_illegality5).format(userTask.getName());
                }
            }
            //添加监听
//            userTask.setExecutionListeners(addFlowableListener(userTask.getExecutionListeners(),ProcessListenerEnum.USER_TASK_START));
//            userTask.setExecutionListeners(addFlowableListener(userTask.getExecutionListeners(),ProcessListenerEnum.USER_TASK_COMPLETE));
        }
    }

    /**
     * 新增flowable监听器
     *
     * @param taskListeners
     * @param listener 监听器
     * @return
     */
    private List<FlowableListener> addFlowableListener(List<FlowableListener> taskListeners,  ProcessListenerEnum listener) {
        if (null == taskListeners) {
            taskListeners = Lists.newArrayList();
        }
        //如果有同类型的监听器则不用添加了
        for (FlowableListener taskListener : taskListeners) {
            if (taskListener.getEvent().equals(listener.getEvent()) && taskListener.getImplementation().equals(listener.getPath())) {
                return taskListeners;
            }
        }
        FlowableListener taskListener = new FlowableListener();
        // 事件类型,
        taskListener.setEvent(listener.getEvent());
        // 监听器类型
        taskListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        // 设置实现
        taskListener.setImplementation(listener.getPath());
        taskListeners.add(taskListener);
        return taskListeners;
    }

    @Override
    public List<OptionsDto> getProcessOptions(ProcessTypeEnum processTypeEnum) {
        List<ProcessDefinitionDto> list = gateway.findByType(LoginUserInfoUtil.getTenantId(),processTypeEnum.getType());
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        return list.stream()
                .map(s -> new OptionsDto(s.getProcessNo(), s.getProcessName()))
                .collect(Collectors.toList());
    }
}
