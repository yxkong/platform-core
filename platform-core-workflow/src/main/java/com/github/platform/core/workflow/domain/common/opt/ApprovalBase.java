package com.github.platform.core.workflow.domain.common.opt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Map;

/**
 * 审批
 * @author: yxkong
 * @date: 2023/11/10 11:35
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalBase implements Serializable {

    /**
     * 实例编号
     */
    @Schema(description = "实例编号")
    protected String instanceNo;
    /**
     * 实例id
     */
    @NotEmpty(message="实例编号（instanceId）不能为空")
    @Schema(description = "实例Id")
    protected String instanceId;
    /**
     * 任务id
     */
    @Schema(description = "任务id")
    protected String taskId;
    /**
     * 任务意见
     */
    @Schema(description = "任务意见")
    protected String comment;
    /**
     * 流程变量信息
     */
    @Schema(description = "流程变量信息")
    private Map<String, Object> variables;
    /**
     * 抄送用户
     */
    @Schema(description = "抄送用户")
    private String carbonCopyUsers;
    /**
     * 目标节点
     */
    @Schema(description = "目标节点")
    private String targetKey;

    /**
     * 审批人
     */
    @Schema(description = "审批人")
    private String assignee;

}
