package com.github.platform.core.workflow.adapter.api.command;
import com.github.platform.core.workflow.domain.common.query.ProcessApprovalRecordQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程审批记录查询
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
public class ProcessApprovalRecordQuery extends ProcessApprovalRecordQueryBase {
}