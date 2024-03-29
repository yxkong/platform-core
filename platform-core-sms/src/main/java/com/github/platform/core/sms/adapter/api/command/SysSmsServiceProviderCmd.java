package com.github.platform.core.sms.adapter.api.command;

import com.github.platform.core.sms.domain.common.entity.SysSmsServiceProviderBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 服务商增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "服务商增加或修改")
public class SysSmsServiceProviderCmd extends SysSmsServiceProviderBase {
    /** 原始密码 */
    @Schema(description = "原始密码")
    protected String pwd;
}
