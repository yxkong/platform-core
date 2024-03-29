package com.github.platform.core.workflow.adapter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 表单添加保证
 *
 * @author: yxkong
 * @date: 2023/11/27 17:31
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Schema(description = "表单配置增加或修改")
public class FormInfoWrapCmd {
    private FormCmd basic;
    private List<FormInfoCmd> infos;
}
