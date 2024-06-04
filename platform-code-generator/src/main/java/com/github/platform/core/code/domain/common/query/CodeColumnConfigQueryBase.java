package com.github.platform.core.code.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 代码生成字段信息存储查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 14:20:40.462
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "代码生成字段信息存储查询")
public class CodeColumnConfigQueryBase extends PageQueryBaseEntity {
    @Schema(description = "库名")
    private String dbName;
    @Schema(description = "表名")
    private String tableName;
}