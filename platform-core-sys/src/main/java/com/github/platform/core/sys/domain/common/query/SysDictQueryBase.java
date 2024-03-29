package com.github.platform.core.sys.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 字典数据查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.400
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "字典数据查询")
public class SysDictQueryBase extends PageQueryBaseEntity {
    /**
     * 字典类型
     */
    @Schema(description ="字典类型")
    private String dictType;

    /**
     * 字典名称
     */
    @Schema(description ="字典名称")
    private String label;

    /**
     * 字典键值
     */
    @Schema(description ="字典键值")
    private String key;
}