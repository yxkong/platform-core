package com.github.platform.core.workflow.adapter.api.command;

import com.github.platform.core.common.utils.SignUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 流程设计
 * @author: yxkong
 * @date: 2023/9/26 5:45 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("流程设计")
public class ProcessDefinitionDesignCmd {
    @ApiModelProperty(value = "流程id",required=true)
    @NotNull(message = "流程编号为空")
    private String id;
    /**
     * 审批流编号
     */
    @ApiModelProperty(value = "流程编号",required=true)
    private String processNo;

    /**
     * 审批流文件
     */
    @ApiModelProperty(value = "流程文件",required=true)
    @NotEmpty(message = "流程文件为空")
    private String processFile;

    public Long getId() {
        return SignUtil.getLongId(this.id);
    }
}
