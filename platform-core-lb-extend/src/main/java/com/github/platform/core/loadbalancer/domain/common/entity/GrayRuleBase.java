package com.github.platform.core.loadbalancer.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 灰度规则表模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class GrayRuleBase extends BaseAdminEntity {
    /** 规则 */
    @Schema(description = "规则")
    @NotEmpty(message="规则（rule）不能为空")
    protected String rule;
    /** 规则描述 */
    @Schema(description = "规则描述")
    @NotEmpty(message="规则描述（name）不能为空")
    protected String name;
    /** 标签 */
    @Schema(description = "标签")
    @NotEmpty(message="标签（label）不能为空")
    protected String label;
    /** 版本 */
    @Schema(description = "版本")
    protected String version;
}
