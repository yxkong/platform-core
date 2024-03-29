package com.github.platform.core.workflow.adapter.api.command;

import com.github.platform.core.workflow.domain.common.opt.ProcessRunBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 创建流程实例实体
 * @author: yxkong
 * @date: 2023/11/1 10:28
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = " 创建流程实例实体")
public class ProcessRunCmd extends ProcessRunBase {
    /**form表单号*/
    @NotEmpty(message="form表单号（formNo）不能为空")
    private String formNo;
    /**form表单数据*/
    @NotNull(message="表单数据（formData）不能为空")
    private Map<String,String> formData;
}
