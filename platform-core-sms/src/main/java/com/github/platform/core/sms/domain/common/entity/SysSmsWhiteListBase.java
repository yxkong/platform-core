package com.github.platform.core.sms.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 短信白名单模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysSmsWhiteListBase extends BaseAdminEntity   {
    /** 渠道 */
    @Schema(description = "渠道")
    @NotEmpty(message="渠道（channel）不能为空")
    protected String channel;
    /** 姓名 */
    @Schema(description = "姓名")
    @NotEmpty(message="姓名（name）不能为空")
    protected String name;
    /** 手机号 */
    @Schema(description = "手机号")
    @NotEmpty(message="手机号（mobile）不能为空")
    protected String mobile;
}
