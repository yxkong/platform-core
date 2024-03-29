package com.github.platform.core.sms.domain.context;

import com.github.platform.core.sms.domain.common.entity.SysSmsWhiteListBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 短信白名单增加或修改上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SysSmsWhiteListContext extends SysSmsWhiteListBase {
}
