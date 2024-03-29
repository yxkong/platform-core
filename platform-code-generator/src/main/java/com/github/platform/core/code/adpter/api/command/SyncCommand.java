package com.github.platform.core.code.adpter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 同步表结构入参
 * @author: yxkong
 * @date: 2023/4/25 2:42 PM
 * @version: 1.0
 */
@Schema(name = "SyncCommand",description = "表名称")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncCommand {
    @Schema(description = "全量同步，1全量，0单表")
    private Integer all ;
    @Schema(description = "表名称，表为空，则同步全量，否则模糊匹配")
    private String tableName;

    @Schema(description = "库名称")
    private String dbName;
}
