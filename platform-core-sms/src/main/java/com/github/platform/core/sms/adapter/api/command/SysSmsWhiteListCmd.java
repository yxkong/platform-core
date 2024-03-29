package com.github.platform.core.sms.adapter.api.command;

import com.github.platform.core.sms.domain.common.entity.SysSmsWhiteListBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 短信白名单增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "短信白名单增加或修改")
public class SysSmsWhiteListCmd extends SysSmsWhiteListBase {
}
