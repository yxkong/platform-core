package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 参数配置模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:04.987
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysConfigBase extends BaseAdminEntity   {
    /** 参数名称 */
    @Schema(description = "参数名称")
    @NotEmpty(message="参数名称（name）不能为空")
    protected String name;
    /** 参数键名 */
    @Schema(description = "参数键名")
    @NotEmpty(message="参数键名（key）不能为空")
    protected String key;
    /** 参数键值 */
    @Schema(description = "参数键值")
    @NotEmpty(message="参数键值（value）不能为空")
    protected String value;
    /** 业务模块 */
    @Schema(description = "业务模块")
    @NotEmpty(message="业务模块（module）不能为空")
    protected String module;
}
