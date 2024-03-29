package com.github.platform.core.workflow.domain.entity;

import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.flowable.bpmn.constants.BpmnXMLConstants;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 任务实体
 * @Author: yxkong
 * @Date: 2023/12/6 17:12
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class WorkflowActivityEntity {
    /**执行id*/
    private String executionId;
    /**节点名称*/
    private String taskName;
    /**节点key 也是节点id*/
    private String taskKey ;
    /**实例id*/
    private String instanceId;
    /**实例id*/
    private String instanceNo;
    /**业务编号*/
    private String bizNo;
    /**流程类型*/
    private String processType;
    /**流程类型名称*/
    private String processTypeName;
    /**角色*/
    private String roles;
    /**主角色*/
    private String mainRole;
    /**发送时间*/
    private LocalDateTime sendTime;
    /**事件类型 start*/
    private String eventType;
    /**活动节点类型*/
    private String activityType;
    /**当前节点id*/
    private String currentActivityId;
    /**下一节点id*/
    private String nextActivityId;

    public boolean isPm(){
        return ProcessTypeEnum.PM.getType().equals(this.processType);
    }
    public boolean isStarted(){
        return FlwConstant.ACTIVITY_STARTED.equals(this.eventType);
    }
    public boolean isCompleted(){
        return FlwConstant.ACTIVITY_COMPLETED.equals(this.eventType) || FlwConstant.MULTI_INSTANCE_ACTIVITY_COMPLETED.equals(this.eventType);
    }

    /**
     * 是否并行网关
     * @return
     */
    public boolean isParallelGateway(){
        return BpmnXMLConstants.ELEMENT_GATEWAY_PARALLEL.equals(this.activityType);
    }
    /**
     * 是否用户任务
     * @return
     */
    public boolean isUserTask(){
        return BpmnXMLConstants.ELEMENT_TASK_USER.equals(activityType);
    }

}
