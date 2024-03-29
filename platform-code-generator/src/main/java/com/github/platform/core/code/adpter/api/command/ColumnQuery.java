package com.github.platform.core.code.adpter.api.command;

import com.github.platform.core.code.domain.common.query.CodeColumnConfigQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author: yxkong
 * @date: 2023/4/25 3:55 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "代码生成字段信息存储查询")
public class ColumnQuery extends CodeColumnConfigQueryBase {
}
