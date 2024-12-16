package com.github.platform.core.sys.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 系统菜单查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.830
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "系统菜单查询")
public class SysMenuQueryBase extends PageQueryBaseEntity {
    /**
     * 菜单名称
     */
    @Schema(description ="菜单名称")
    private String name;
}