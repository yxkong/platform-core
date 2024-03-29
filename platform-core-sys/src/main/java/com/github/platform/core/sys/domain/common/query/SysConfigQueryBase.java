package com.github.platform.core.sys.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import com.github.platform.core.standard.validate.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 参数配置查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:04.987
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "参数配置查询")
public class SysConfigQueryBase extends PageQueryBaseEntity {
    /**
     * 字典名称
     */
    @Schema(description ="字典名称")
    private String name;
    @NotEmpty(groups = {Query.class},message = "参数key不能为空")
    @Schema(description ="参数键名")
    private String key;
    /**
     * 参数键值
     */
    @Schema(description ="参数键值")
    private String value;
    /**
     * 业务模块
     */
    @Schema(description ="业务模块")
    private String module;
}