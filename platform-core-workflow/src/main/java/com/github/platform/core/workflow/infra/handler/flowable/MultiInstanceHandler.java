package com.github.platform.core.workflow.infra.handler.flowable;

import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.application.executor.strategy.UserStrategy;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.context.UserQueryContext;
import com.github.platform.core.workflow.infra.configuration.WorkflowProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 多用户任务处理,用于在流程中获取用户
 * <bpmn2:multiInstanceLoopCharacteristics flowable:collection="${multiInstanceHandler.getUsers(execution)}" flowable:elementVariable="assignee">
 *    <bpmn2:completionCondition xsi:type="bpmn2:tFormalExpression">${nrOfCompletedInstances &gt;= nrOfInstances}</bpmn2:completionCondition>
 * </bpmn2:multiInstanceLoopCharacteristics>
 * @author: yxkong
 * @date: 2023/11/8 13:45
 * @version: 1.0
 */
@AllArgsConstructor
@Component("multiInstanceHandler")
@Slf4j
public class MultiInstanceHandler{
    @Resource
    private WorkflowProperties workflowProperties;

    /**
     * 该方法用于在bpmn的xml中
     *
     * @param delegate
     * @return
     */
    public Set<String> getUsers(DelegateExecution delegate) {
        Set<String> candidateUsers = new LinkedHashSet<>();
        FlowElement flowElement = delegate.getCurrentFlowElement();
        String instanceId = delegate.getProcessInstanceId();
        String instanceNo = (String) delegate.getVariable(FlwConstant.INSTANCE_NO);
        String bizNo = (String) delegate.getVariable(FlwConstant.BIZ_NO);
        String processType = (String) delegate.getVariable(FlwConstant.PROCESS_TYPE);
        if (log.isDebugEnabled()){
            log.debug("MultiInstanceHandler instanceId:{} instanceNo:{} bizNo:{} flowElement:{}",instanceId,instanceNo,bizNo, JsonUtils.toJson(flowElement));
        }
        if (Objects.isNull(flowElement) || !(flowElement instanceof UserTask)){
            return candidateUsers;
        }
        UserTask userTask = (UserTask)flowElement;

        getUsers(userTask, instanceNo, candidateUsers, processType, bizNo, instanceId);

        if (CollectionUtil.isEmpty(candidateUsers)){
            List<String> fallbackUsers = workflowProperties.getFallbackUsers();
            log.warn("bizNo:{} 节点：{}获取兜底用户：{}",bizNo,userTask.getName(),JsonUtils.toJson(fallbackUsers));
            if (CollectionUtil.isNotEmpty(fallbackUsers)){
                fallbackUsers.forEach(s->{
                    candidateUsers.add(s);
                });
            } else {
                candidateUsers.add("admin");
            }

        }
        if (log.isDebugEnabled()){
            log.debug("当前ROLE节点:{} 对应用户：{}",userTask.getCandidateGroups(),JsonUtils.toJson(candidateUsers));
        }
        delegate.setVariableLocal(FlwConstant.CANDIDATE,candidateUsers);
        return candidateUsers;
    }

    private void getUsers(UserTask userTask, String instanceNo, Set<String> candidateUsers, String processType, String bizNo, String instanceId) {
        String assigneeType = userTask.getAttributeValue(BpmnXMLConstants.FLOWABLE_EXTENSIONS_NAMESPACE, FlwConstant.ASSIGNEE_TYPE);
        if (FlwConstant.ASSIGNEE_USERS.equals(assigneeType) && CollectionUtil.isNotEmpty(userTask.getCandidateUsers())) {
            // 添加候选用户id
            if (log.isInfoEnabled()){
                log.info("instanceNo:{} 当前节点：{} USERS节点用户：{}", instanceNo,userTask.getName(),userTask.getCandidateUsers());
            }
            candidateUsers.addAll(userTask.getCandidateUsers());
            return;
        }
        if (CollectionUtil.isEmpty(userTask.getCandidateGroups())) {
            return;
        }
        // 获取角色或部门的
        if (FlwConstant.ASSIGNEE_ROLES.equals(assigneeType)) {
            // 通过角色名称，获取所有用户名
            List<String> candidateGroups = userTask.getCandidateGroups();
            if (log.isDebugEnabled()){
                log.debug("instanceNo:{} 当前节点：{} 角色：{}", instanceNo,userTask.getName(),JsonUtils.toJson(candidateGroups));
            }
            if (CollectionUtil.isEmpty(candidateGroups)){
                return;
            }
            ProcessTypeEnum typeEnum = ProcessTypeEnum.get(processType);
            UserStrategy userStrategy = ApplicationContextHolder.getBean(typeEnum.getUserBean(), UserStrategy.class);
            UserQueryContext userQueryContext = UserQueryContext.builder()
                    .roles(candidateGroups)
                    .nodeKey(userTask.getId())
                    .nodeName(userTask.getName())
                    .bizNo(bizNo)
                    .processType(processType)
                    .instanceId(instanceId)
                    .build();
            List<OptionsDto> users = userStrategy.users(userQueryContext);
            if (CollectionUtil.isNotEmpty(users)){
                users.forEach(s->{
                    candidateUsers.add(String.valueOf(s.getValue()));
                });
            }
        } else if (FlwConstant.ASSIGNEE_DEPT.equals(assigneeType)) {
            // 通过部门id，获取所有用户id集合
        }
    }
}
