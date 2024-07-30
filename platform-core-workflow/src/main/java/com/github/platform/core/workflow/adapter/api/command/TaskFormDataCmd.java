package com.github.platform.core.workflow.adapter.api.command;

import com.github.platform.core.standard.entity.vue.OptionsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 任务提交的时候携带的表单数据
 * @Author: yxkong
 * @Date: 2024/7/17
 * @version: 1.0
 */
@Data
public class TaskFormDataCmd {
    private Long id;
    @Schema(description = "字段名")
    @NotEmpty(message="字段名（name）不能为空")
    protected String name;

    /** 表单编号 */
    @Schema(description = "表单编号")
    @NotEmpty(message="表单编号（formNo）不能为空")
    protected String formNo;
    /** 实例编号 */
    @Schema(description = "实例编号")
    @NotEmpty(message="实例编号（instanceNo）不能为空")
    protected String instanceNo;
    /** 输入框类型 input、textarea、select、radio、upload */
    @Schema(description = "输入框类型 input、textarea、select、radio、upload")
    @NotEmpty(message="输入框类型 input、textarea、select、radio、upload（type）不能为空")
    protected String type;
    /** 中文显示 */
    @Schema(description = "中文显示")
    @NotEmpty(message="中文显示（label）不能为空")
    protected String label;
    /** 数据值 */
    @Schema(description = "数据值")
    @NotEmpty(message="数据值（value）不能为空")
    protected String value;
    /** 排序 */
    @Schema(description = "排序")
    protected Integer sort;
    private List<OptionsDto> options;
}
