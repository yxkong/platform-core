package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
* 字典类型模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.543
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysDictTypeBase extends BaseAdminEntity   {
    /** 字典名称 */
    @Schema(description = "字典名称")
    @NotEmpty(message="字典名称（name）不能为空")
    protected String name;
    /** 字典类型 */
    @Schema(description = "字典类型")
    @NotEmpty(message="字典类型（type）不能为空")
    protected String type;
    /** 字典分类，0系统，1业务 */
    @Schema(description = "字典分类，0系统，1业务")
    @NotNull(message="字典分类，0系统，1业务（classify）不能为空")
    protected Integer classify;
    /** 字符类型 */
    @Schema(description = "字符类型")
    @NotEmpty(message="字符类型（charType）不能为空")
    protected String charType;
}
