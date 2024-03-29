package com.github.platform.core.workflow.domain.context;
import com.github.platform.core.workflow.domain.common.entity.ProcessApprovalRecordBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
* 流程审批记录增加或修改上下文
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
public class ProcessApprovalRecordContext extends ProcessApprovalRecordBase {
}
