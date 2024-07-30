package com.github.platform.core.workflow.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流程任务Dto
 * @Author: yxkong
 * @Date: 2024/7/12
 * @version: 1.0
 */
@Schema(description = "流程任务信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessTaskDto {
    private String taskId;
    private String taskDefinitionKey;
    private String formKey;
    private String name;
}
