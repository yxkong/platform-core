package com.github.platform.core.sys.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.sys.domain.common.entity.SysMenuBase;
import com.github.platform.core.sys.domain.constant.MenuConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
* 系统菜单传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.830
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "系统菜单传输实体")
public class SysMenuDto extends SysMenuBase{
    /** 父菜单名称 */
    @Schema(description = "父菜单名称")
    private String parentName;
    /** 子菜单 */
    @Schema(description = "子菜单")
    @Builder.Default
    private List<SysMenuDto> children = new ArrayList<>();

}