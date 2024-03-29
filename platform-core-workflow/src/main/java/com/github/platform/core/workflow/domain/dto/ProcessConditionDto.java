package com.github.platform.core.workflow.domain.dto;
import com.github.platform.core.workflow.domain.common.entity.ProcessConditionBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
* 流程条件传输实体
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
@Schema(description = "流程条件传输实体")
public class ProcessConditionDto extends ProcessConditionBase{
}