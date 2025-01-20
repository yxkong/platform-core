package com.github.platform.core.workflow.domain.constant;

import org.flowable.bpmn.constants.BpmnXMLConstants;

/**
 * 审批与审批流常量类
 * @author yxkong
 */
public interface FlwConstant {

    /**活动启动*/
    String ACTIVITY_STARTED = "started";
    String TASK_CREATED = "created";
    /**活动完成*/
    String ACTIVITY_COMPLETED = "completed";
    String TASK_COMPLETED = "completed";
    String MULTI_INSTANCE_ACTIVITY_COMPLETED = "multi_instance_completed";
    /**节点类型：start*/
    String NODE_TYPE_START = "start";
    /**项目表单*/
    String PM_FORM_KEY = "FNO00000000001";


    /**节点类型：end*/
    String NODE_TYPE_END = "end";
    /**节点类型：userTask ,见 BpmnXMLConstants.ELEMENT_TASK_USER */

    String NODE_TYPE_USER_TASK = "userTask";
    /**节点类型：parallelGateway ，见 BpmnXMLConstants.ELEMENT_GATEWAY_PARALLEL */
    String NODE_TYPE_PARALLEL_GATEWAY = "parallelGateway";

    /**
     * 是否需要处理
     * @param activityType
     * @return
     */
    static  boolean isNeedHandler(String activityType){
        return  BpmnXMLConstants.ELEMENT_TASK_USER.equals(activityType) || BpmnXMLConstants.ELEMENT_GATEWAY_PARALLEL.equals(activityType);
    }

    /**
     * 是否并行网关
     * @param activityType
     * @return
     */
    static boolean isParallelGateway(String activityType){
        return BpmnXMLConstants.ELEMENT_GATEWAY_PARALLEL.equals(activityType);
    }
    /**
     * 是否用户任务
     * @param activityType
     * @return
     */
    static boolean isUserTask(String activityType){
        return BpmnXMLConstants.ELEMENT_TASK_USER.equals(activityType);
    }

    String TASK_NAME_START = "流程启动";
    String TASK_NAME_END = "流程完成";
    /**
     * 流程实例标识
     */
    String INSTANCE_NO = "instanceNo";
    /**
     * 流程实例名称
     */
    String INSTANCE_NAME = "instanceName";
    /**
     * 任务属性-计划排期,全部转小写
     */
    String TASK_PROPERTY_PLAN_SCHEDULE = "planschedule";

    /**
     *任务属性- 计划排期节点属性值
     */
    String TASK_PROPERTY_PLAN_SCHEDULE_VALUE = "main";
    /**
     * 任务属性，主角色
     */
    String TASK_PROPERTY_MAIN_ROLE = "mainrole";
    /**
     * 业务单号标识
     */
    String BIZ_NO = "bizNo";
    /**建群标识*/
    String CREATE_GROUP = "createGroup";
    /**流程用户*/
    String PROCESS_USERS = "processUsers";
    /**创建用户,存储的是手机号码*/
    String CREATE_USER = "createUser";
    /**
     * 流程类型,pm/oa
     */
    String PROCESS_TYPE = "processType";
    /**
     * 流程编号
     */
    String PROCESS_NO = "processNo";
    /**租户id*/
    String TENANT_ID = "tenantId";
    /**
     * 流程版本
     */
    String PROCESS_VERSION = "version";

    /**
     * 用户任务（多人或签标识）
     */
    String USER_TASK_ASSIGNEE = "${assignee}";
    /**
     * 审批人类型
     */
    String ASSIGNEE_TYPE = "assigneeType";
    /**
     * 审批人角色
     */
    String ASSIGNEE_CANDIDATE_GROUPS = "candidateGroups";

    /**
     * 审批人为发起人
     */
    String ASSIGNEE_INITIATOR = "INITIATOR";
    /**
     * 审批角色，只能选一个
     */
    String ASSIGNEE_ROLES = "ROLES";
    /**
     * 审批用户
     */
    String ASSIGNEE_USERS = "USERS";
    /**
     * 审批部门负责人
     */
    String ASSIGNEE_DEPT = "DEPT";
    /**
     * 用户任务多人或签长度限制
     */
    int USER_TASK_CANDIDATEUSERS_LENGTH = 50;

    /**
     * 候选人
     */
    String CANDIDATE = "candidate";
}
