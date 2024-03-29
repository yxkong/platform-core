package com.github.platform.core.workflow.adapter.api.command;
import com.github.platform.core.workflow.domain.common.query.ProcessConditionQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程条件查询
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
@Schema(description = "流程条件查询")
public class ProcessConditionQuery extends ProcessConditionQueryBase {
}