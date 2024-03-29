package com.github.platform.core.sys.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 字典类型查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.543
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "字典类型查询")
public class SysDictTypeQueryBase extends PageQueryBaseEntity {
    /**
     * 字典名称
     */
    @Schema(description ="字典名称")
    private String name;

    /**
     * 字典类型
     */
    @Schema(description ="字典类型")
    private String type;
    @Schema(description = "字典分类")
    private Integer classify;
}