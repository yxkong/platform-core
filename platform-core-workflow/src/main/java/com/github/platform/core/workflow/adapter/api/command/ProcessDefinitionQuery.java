package com.github.platform.core.workflow.adapter.api.command;
import com.github.platform.core.workflow.domain.common.query.ProcessDefinitionQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程管理查询
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
@Schema(description = "流程管理查询")
public class ProcessDefinitionQuery extends ProcessDefinitionQueryBase {
}