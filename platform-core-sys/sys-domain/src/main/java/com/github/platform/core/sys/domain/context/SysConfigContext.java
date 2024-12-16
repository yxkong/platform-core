package com.github.platform.core.sys.domain.context;

import com.github.platform.core.sys.domain.common.entity.SysConfigBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 参数配置增加或修改上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:04.987
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysConfigContext extends SysConfigBase {
}
