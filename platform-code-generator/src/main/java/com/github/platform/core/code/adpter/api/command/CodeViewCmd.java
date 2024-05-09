package com.github.platform.core.code.adpter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码视图
 * @author: yxkong
 * @date: 2023/5/8 12:12 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeViewdCmd {
    @Schema(description = "库名称")
    private String dbName;
    @Schema(description = "表名称")
    private String tableName;
    @Schema(description = "多表名称")
    private List<String> tableNames;
    @Schema(description = "代码类型",example = "0，后端管理，1，api项目")
    private Integer codeType;

}
