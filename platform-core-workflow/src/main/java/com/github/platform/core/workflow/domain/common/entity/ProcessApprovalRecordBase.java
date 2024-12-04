package com.github.platform.core.workflow.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 流程审批记录模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @datetime 2023-11-16 13:52:29.227
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ProcessApprovalRecordBase extends BaseAdminEntity   {
    /** 实例名称 */
    @Schema(description = "实例名称")
    @NotEmpty(message="实例名称（instanceName）不能为空")
    protected String instanceName;
    /** 实例编号 */
    @Schema(description = "实例编号")
    protected String instanceNo;
    /** 实例ID */
    @Schema(description = "实例ID")
    @NotEmpty(message="实例ID（instanceId）不能为空")
    protected String instanceId;
    /** 当前节点ID */
    @Schema(description = "当前节点ID")
    @NotEmpty(message="当前节点ID（taskId）不能为空")
    protected String taskId;
    /** 节点标识 */
    @Schema(description = "节点标识")
    @NotEmpty(message="节点标识（taskKey）不能为空")
    protected String taskKey;
    /** 当前审批流节点名称 */
    @Schema(description = "当前审批流节点名称")
    @NotEmpty(message="当前审批流节点名称（taskName）不能为空")
    protected String taskName;
    /** 表单实例编号 */
    @Schema(description = "表单实例编号")
    protected String formInstNo;
    /** 实际审批人登录名 */
    @Schema(description = "实际审批人登录名")
    protected String enAssignee;
    /** 实际审批人中文名 */
    @Schema(description = "实际审批人中文名")
    protected String cnAssignee;
    /** 操作类型，1通过，2，退回，3，驳回，5，委派，5，转办，6终止，7撤回 */
    @Schema(description = "操作类型，1通过，2，退回，3，驳回，5，委派，5，转办，6终止，7撤回")
    protected String optType;
    /** 审批意见 */
    @Schema(description = "审批意见")
    protected String comment;
    /** 候选人 */
    @Schema(description = "候选人")
    protected String candidate;
    /** 节点启动时间 */
    @Schema(description = "节点启动时间")
    protected LocalDateTime startTime;
    /** 节点启动时间 */
    @Schema(description = "节点完成时间")
    protected LocalDateTime endTime;
}
