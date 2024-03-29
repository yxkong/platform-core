package com.github.platform.core.workflow.domain.context;
import com.github.platform.core.workflow.domain.common.entity.ProcessConditionBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
* 流程条件增加或修改上下文
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
public class ProcessConditionContext extends ProcessConditionBase {
}
