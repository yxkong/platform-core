package com.github.platform.core.workflow.domain.context;
import com.github.platform.core.workflow.domain.common.entity.ProcessInstanceBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
* 流程实例增加或修改上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessInstanceContext extends ProcessInstanceBase {
}
