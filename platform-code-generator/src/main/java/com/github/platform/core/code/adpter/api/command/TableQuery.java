package com.github.platform.core.code.adpter.api.command;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * 表查询
 * @author: yxkong
 * @date: 2023/4/25 3:09 PM
 * @version: 1.0
 */
@Schema(name = "TableQuery",description = "表查询")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TableQuery extends PageQueryBaseEntity {
    @Schema(description = "库名",example = "platform")
    private String dbName;
    @NotEmpty(message = "表名不能为空")
    @Schema(description = "表名",example = "t_sms_log")
    private String tableName;
}
