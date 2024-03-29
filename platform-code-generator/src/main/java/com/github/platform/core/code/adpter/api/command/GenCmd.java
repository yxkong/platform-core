package com.github.platform.core.code.adpter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author: yxkong
 * @date: 2023/4/28 3:29 PM
 * @version: 1.0
 */
@Schema(name = "GenCmd",description = "代码生成")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenCmd {
    @NotNull(message = "代码生成配置不能为空")
    @Schema(name = "genConfig",description = "代码生成配置")
    private GenConfigCmd genConfig;
    @NotNull(message = "代码生成字段不能为空")
    @Schema(name = "columns",description = "代码生成字段")
    private List<ColumnCmd> columns;
}
