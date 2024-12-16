package com.github.platform.core.sys.adapter.api.command;

import com.github.platform.core.sys.domain.common.query.SysUserLogQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 用户日志表查询
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.892
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "用户日志表查询")
public class SysUserLogQuery extends SysUserLogQueryBase {
}