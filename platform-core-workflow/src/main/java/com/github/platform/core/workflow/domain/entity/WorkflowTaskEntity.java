package com.github.platform.core.workflow.domain.entity;

import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.flowable.bpmn.model.SequenceFlow;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author: yxkong
 * @date: 2024/2/19 11:13
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class WorkflowTaskEntity {
    /**审批任务id*/
    private String taskId;
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
    private String processNo;
    private Integer processVersion;
    /**角色*/
    private String roles;
    /**主角色*/
    private String mainRole;
    /**发送时间*/
    private LocalDateTime sendTime;
    /**事件类型 start*/
    private String eventType;
    /**当前节点id*/
    private String currentActivityId;
    /**下一节点id*/
    private String nextActivityId;
    /**当前节点表单key*/
    private String formKey;
//    /**
//     * 流程变量map
//     */
//    private Map<String,Object> variables;
    private List<SequenceFlow> outgoingFlows;
    public boolean isPm(){
        return ProcessTypeEnum.PM.getType().equals(this.processType);
    }
    public boolean isCompleted(){
        return FlwConstant.ACTIVITY_COMPLETED.equals(this.eventType) || FlwConstant.MULTI_INSTANCE_ACTIVITY_COMPLETED.equals(this.eventType);
    }
    public boolean isCreate(){
        return FlwConstant.TASK_CREATED.equals(this.eventType);
    }
}
