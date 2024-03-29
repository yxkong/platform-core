package com.github.platform.core.workflow.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 流程信息
 * @author: yxkong
 * @date: 2023/11/13 12:40
 * @version: 1.0
 */
@Schema(description = "流程用户")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDto {

    /** 表单实例编号 */
    @Schema(description = "表单实例编号")
    protected String formInstNo;
    /** 实例名称 */
    @Schema(description = "实例名称")
    protected String instanceName;
    /** 实例编号 */
    @Schema(description = "实例编号")
    protected String instanceNo;
    @Schema(description = "流程定义编号")
    protected String processNo;
    @Schema(description = "业务编号")
    protected String bizNo;
    /** 实例ID */
    @Schema(description = "实例ID")
    protected String instanceId;
    /**流程类型*/
    @Schema(description = "流程类型")
    protected String processType;
    /** 流程版本 */
    @Schema(description = "流程版本")
    protected String version;
    /** 当前节点ID */
    @Schema(description = "当前节点ID")
    protected String taskId;
    /** 节点标识 */
    @Schema(description = "节点标识")
    protected String taskKey;
    /** 当前审批流节点名称 */
    @Schema(description = "当前审批流节点名称")
    protected String taskName;
    /** 审批人登录名 */
    @Schema(description = "审批人登录名")
    protected String assignee;
    /** 实际审批人中文名 */
    @Schema(description = "审批人中文名")
    protected String cnAssignee;
    /** 流程发起人 */
    @Schema(description = "流程发起人")
    protected String initiator;
    /**
     * 任务创建时间
     */
    @Schema(description = "任务创建时间")
    private LocalDateTime createTime;
    /**耗时*/
    @Schema(description = "耗时")
    private String duration;
    /**
     * 流程启动时间
     */
    @Schema(description = "流程启动时间")
    private LocalDateTime startTime;
    /**
     * 流程变量
     */
    @Schema(description = "流程变量")
    private Map<String,Object> variables;

}
