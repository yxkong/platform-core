package com.github.platform.core.workflow.adapter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * 改变状态实体
 * @author: yxkong
 * @date: 2024/1/25 18:39
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@Schema(description = " 强制执行实体")
public class ChangeStateCmd {
    @NotEmpty(message="业务编号（bizNo）不能为空")
    private String bizNo;
    @NotEmpty(message="活动id（currentActivityId）不能为空")
    private String currentActivityId;
    @NotEmpty(message="目标id（targetActivityId）不能为空")
    private String targetActivityId;
}
