package com.github.platform.core.code.adpter.api.command;

import com.github.platform.core.code.domain.common.entity.CodeGenConfigBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * 代码生成配置
 * @author: yxkong
 * @date: 2023/4/27 6:37 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "代码生成器配置增加或修改")
public class GenConfigCmd extends CodeGenConfigBase {

}
