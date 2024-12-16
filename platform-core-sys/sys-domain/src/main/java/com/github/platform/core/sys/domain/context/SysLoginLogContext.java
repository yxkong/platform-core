package com.github.platform.core.sys.domain.context;

import com.github.platform.core.sys.domain.common.entity.SysLoginLogBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 登录日志增加或修改上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.685
* @version 1.0
*/
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class SysLoginLogContext extends SysLoginLogBase {
}
