package com.github.platform.core.sys.adapter.api.command.account;

import com.github.platform.core.sys.domain.common.entity.SysUserBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 用户信息增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.651
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "用户信息增加或修改")
public class SysUserCmd extends SysUserBase{
}
