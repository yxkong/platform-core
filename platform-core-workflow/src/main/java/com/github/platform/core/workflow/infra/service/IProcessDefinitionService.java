package com.github.platform.core.workflow.infra.service;


import com.github.platform.core.workflow.domain.constant.ProcessStatusEnum;
import org.flowable.engine.repository.ProcessDefinition;

import java.io.InputStream;
import java.util.Map;

/**
 * flowable 管理服务
 */
public interface IProcessDefinitionService {

    /**
     * 流程部署
     *
     * @param processType  流程类型
     * @param processNo  流程标识
     * @param version 流程版本  V
     * @param processName 流程名称
     * @param xmlText     xml格式文本
     * @return 返回部署id
     */
    String deploy(String processType,String processNo,String version, String processName, String xmlText);
    /**
     * 导入流程文件
     * @param name
     * @param category
     * @param in
     */
    String importFile(String name, String category, InputStream in);



    /**
     * 审批
     *
     * @param processId 流程实例ID
     * @param taskNo    业务任务单号
     * @param taskId    流程节点ID
     * @param params       流程变量（包含审批结果）
     */
    void approve(String processId, String taskNo, String taskId, Map<String, Object> params);
    /**
     * 激活或挂起流程定义
     * @param deployId 流程部署ID
     * @param state    状态
     */
    void updateState(String deployId, ProcessStatusEnum state);

    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    void delete(String deployId);

    /**
     * 根据deployId 查询最新的
     * @param deployId
     * @return
     */
    ProcessDefinition queryLatest(String deployId);



}
