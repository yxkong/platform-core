package com.github.platform.core.workflow.domain.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程审批记录查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-09 11:30:36.194
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "流程审批记录查询")
public class ProcessApprovalRecordQueryBase extends PageQueryBaseEntity {
    /** 实例名称 */
    @Schema(description = "实例名称")
    private String instanceName;
    /** 当前审批流节点名称 */
    @Schema(description = "当前审批流节点名称")
    private String taskName;
    /** 实际审批人登录名 */
    @Schema(description = "实际审批人登录名")
    private String taskEnAssignee;
}