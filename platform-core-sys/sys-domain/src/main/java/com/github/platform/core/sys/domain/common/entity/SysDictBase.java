package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
* 字典数据模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.400
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysDictBase extends BaseAdminEntity   {
    /** 字典键值 */
    @Schema(description = "字典键值")
    @NotBlank(message = "字典key不能为空")
    protected String key;
    /** 字典标签 */
    @Schema(description = "字典标签")
    @NotBlank(message = "字典label不能为空")
    protected String label;
    /** 字典类型 */
    @Schema(description = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    protected String dictType;
    /** 样式属性 */
    @Schema(description = "样式属性")
    protected String cssClass;
    /** 列表回显样式 */
    @Schema(description = "列表回显样式")
    @NotEmpty(message="列表回显样式（listClass）不能为空")
    protected String listClass;
    /** 显示顺序 */
    @Schema(description = "显示顺序")
    protected Integer sort;
}
