package com.github.platform.core.code.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 代码生成字段信息存储模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 14:20:40.462
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class CodeColumnConfigBase extends BaseAdminEntity   {
    /** 字段排序 */
    @Schema(description = "字段排序")
    protected Integer sort;
    @NotEmpty(message="表名（tableName）不能为空")
    /** 表名 */
    @Schema(description = "表名")
    protected String tableName;
    @NotEmpty(message="数据库字段名称（columnName）不能为空")
    /** 数据库字段名称 */
    @Schema(description = "数据库字段名称")
    protected String columnName;
    @NotEmpty(message="数据库字段类型（columnType）不能为空")
    /** 数据库字段类型 */
    @Schema(description = "数据库字段类型")
    protected String columnType;
    /** 字典名称 */
    @Schema(description = "字典名称")
    protected String dictName;
    /** 字段额外的参数 */
    @Schema(description = "字段额外的参数")
    protected String extra;
    /** 是否作为查询条件，0否，1是，默认null */
    @Schema(description = "是否作为查询条件，0否，1是，默认null")
    protected Integer queryShow;
    /** 是否表单显示 */
    @Schema(description = "是否表单显示")
    protected Integer formShow;
    /** 表单类型 */
    @Schema(description = "表单类型")
    protected String formType;
    @NotEmpty(message="数据库字段键类型（columnKey）不能为空")
    /** 数据库字段键类型 */
    @Schema(description = "数据库字段键类型")
    protected String columnKey;
    /** 是否在列表显示，1显示 */
    @Schema(description = "是否在列表显示，1显示")
    protected Integer listShow;
    /** 是否必填1，必填 */
    @Schema(description = "是否必填1，必填")
    protected Integer notNull;
    /** 查询方式，=,>=,>,<,<=,!=,like,isNull,notNull,between,!= */
    @Schema(description = "查询方式，=,>=,>,<,<=,!=,like,isNull,notNull,between,!=")
    protected String queryType;
}
