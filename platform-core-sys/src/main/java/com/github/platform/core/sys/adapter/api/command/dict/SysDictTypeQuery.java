package com.github.platform.core.sys.adapter.api.command.dict;

import com.github.platform.core.sys.domain.common.query.SysDictTypeQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 字典类型查询
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.543
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "字典类型查询")
public class SysDictTypeQuery extends SysDictTypeQueryBase {
}