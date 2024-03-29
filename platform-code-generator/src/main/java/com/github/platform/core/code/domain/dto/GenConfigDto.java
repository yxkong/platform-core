package com.github.platform.core.code.domain.dto;

import com.github.platform.core.code.domain.common.entity.CodeGenConfigBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 代码生成器配置传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 14:20:40.789
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "代码生成器配置传输实体")
public class GenConfigDto extends CodeGenConfigBase{
}