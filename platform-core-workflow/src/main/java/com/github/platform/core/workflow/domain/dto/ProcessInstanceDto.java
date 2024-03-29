package com.github.platform.core.workflow.domain.dto;
import com.github.platform.core.workflow.domain.common.entity.ProcessInstanceBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
* 流程实例传输实体
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
@Schema(description = "流程实例传输实体")
public class ProcessInstanceDto extends ProcessInstanceBase {
    /**
     * 任务耗时
     */
    private String duration;
}