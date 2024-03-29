package com.github.platform.core.workflow.infra.service.impl;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.ProcessStatusEnum;
import com.github.platform.core.workflow.infra.service.IProcessDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

/**
 * flowable工作流执行器实现
 */
@Service("flowableManagerService")
@Slf4j
public class FlowableDefinitionServiceImpl implements IProcessDefinitionService {

    /**
     * 流程仓库服务类
     */
    @Autowired(required = false)
    private RepositoryService repositoryService;
    /**
     * 查询任务信息
     */
    @Autowired(required = false)
    private TaskService taskService;


    private static final String BPMN_FILE_SUFFIX = ".bpmn";
    private static final String BPMN_NAME_SUFFIX = ".bpmn20.xml";
    @Override
    public String deploy(String processType,String processNo,String version, String processName, String xmlText) {
        processName = processName + BPMN_NAME_SUFFIX;
        Deployment deployment = repositoryService.createDeployment()
                // flowable 根据key来区分流程
                .key(processNo)
                .name(processName + SymbolConstant.colon +version)
                .addString(processName, xmlText)
                // 根据流程编号分类
                .category(processNo)
                // 流程类型
                .deploymentProperty("processType",processType)
                .deploy();
        log.info("工作流发布成功 流程标识 = {} 流程ID = {}", processNo, deployment.getId());
        return deployment.getId();
    }

    @Override
    public String importFile(String name, String category, InputStream in) {
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream(name + BPMN_FILE_SUFFIX, in)
                .name(name)
                .category(category)
                .deploy();
        return deployment.getId();
    }

    @Override
    public void approve(String processId, String bizNo, String taskId, Map<String, Object> params) {
        params.put(FlwConstant.BIZ_NO, bizNo);
        taskService.complete(taskId, params);
        if (log.isDebugEnabled()){
            log.debug("工作流审批完成 流程实例ID = {} 业务单号 = {} 流程变量 = {}", processId, bizNo, JsonUtils.toJson(params));
        }

    }

    @Override
    public void updateState(String deployId, ProcessStatusEnum state) {
        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        // 激活，
        if (state.isOn()) {
            repositoryService.activateProcessDefinitionById(procDef.getId(), true, null);
        }
        // 挂起
        if (state.isOff()) {
            repositoryService.suspendProcessDefinitionById(procDef.getId(), true, null);
        }
    }

    @Override
    public void delete(String deployId) {
        // true 允许级联删除 ,不设置会导致数据库外键关联异常
        repositoryService.deleteDeployment(deployId, true);
    }

    @Override
    public ProcessDefinition queryLatest(String deployId) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(deployId)
                .latestVersion().singleResult();
    }


}
