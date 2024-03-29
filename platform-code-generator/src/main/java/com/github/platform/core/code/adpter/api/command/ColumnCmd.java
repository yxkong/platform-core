package com.github.platform.core.code.adpter.api.command;

import com.github.platform.core.code.domain.common.entity.CodeColumnConfigBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 字段管理
 * @author: yxkong
 * @date: 2023/4/28 3:30 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "代码生成字段信息存储增加或修改")
public class ColumnCmd  extends CodeColumnConfigBase {
}
