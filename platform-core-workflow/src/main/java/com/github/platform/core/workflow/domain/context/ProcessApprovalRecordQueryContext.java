package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.query.ProcessApprovalRecordQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程审批记录查询上下文
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
public class ProcessApprovalRecordQueryContext extends ProcessApprovalRecordQueryBase {
}
