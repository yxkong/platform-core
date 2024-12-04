package com.github.platform.core.sys.adapter.api.command.loginlog;

import com.github.platform.core.sys.domain.common.entity.SysLoginLogBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 登录日志增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.685
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "登录日志增加或修改")
public class SysLoginLogCmd extends SysLoginLogBase{
}
