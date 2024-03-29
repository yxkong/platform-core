package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.query.ProcessConditionQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程条件查询上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-30 18:17:25.794
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessConditionQueryContext extends ProcessConditionQueryBase {
}
