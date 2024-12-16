package com.github.platform.core.sys.adapter.api.command.config;

import com.github.platform.core.sys.domain.common.entity.SysConfigBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 参数配置增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:04.987
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "参数配置增加或修改")
public class SysConfigCmd extends SysConfigBase{
}
