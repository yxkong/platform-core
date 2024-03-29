package com.github.platform.core.code.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *字段实体
 * @Author: yxkong
 * @Date: 2023/4/25 2:00 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    /**
     * 库名
     */
    @Schema(description="库名")
    private String tableSchema;
    /**
     * 表名
     */
    @Schema(description="表名")
    private String tableName;
    /**
     * 引擎
     */
    @Schema(description="引擎")
    private String engine;
    /**
     * 表备注
     */
    @Schema(description="表备注")
    private String tableComment;
    /**
     * 创建时间
     */
    @Schema(description="创建时间")
    private LocalDateTime createTime;
}
