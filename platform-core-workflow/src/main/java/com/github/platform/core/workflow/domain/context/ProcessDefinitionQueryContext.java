package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.query.ProcessDefinitionQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程管理查询上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessDefinitionQueryContext extends ProcessDefinitionQueryBase {
}
