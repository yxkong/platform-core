package com.github.platform.core.code.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 代码生成器配置查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 14:20:40.789
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "代码生成器配置查询")
public class CodeGenConfigQueryBase extends PageQueryBaseEntity {
    /** 主键id */
    @Schema(description = "主键id")
    private Long id;
}