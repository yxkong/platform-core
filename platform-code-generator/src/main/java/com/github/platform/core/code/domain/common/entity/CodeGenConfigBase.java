package com.github.platform.core.code.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
* 代码生成器配置模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 14:20:40.789
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class CodeGenConfigBase extends BaseAdminEntity   {
    @NotEmpty(message="表名（tableName）不能为空")
    /** 表名 */
    @Schema(description = "表名")
    protected String tableName;
    @NotEmpty(message="表描述（tableComment）不能为空")
    /** 表描述 */
    @Schema(description = "表描述")
    protected String tableComment;
    @NotEmpty(message="作者（author）不能为空")
    /** 作者 */
    @Schema(description = "作者")
    protected String author;
    @NotNull(message="是否覆盖（cover）不能为空")
    /** 是否覆盖 */
    @Schema(description = "是否覆盖")
    protected Integer cover;
    /** url前缀 */
    @Schema(description = "url前缀")
    protected String urlPrefix;
    /** 实体名称 */
    @Schema(description = "实体名称")
    protected String entityName;
    /** 模块名称 */
    @Schema(description = "模块名称")
    protected String moduleName;
    @NotEmpty(message="生成包路径（packageName）不能为空")
    /** 生成包路径 */
    @Schema(description = "生成包路径")
    protected String packageName;
    /** 前端代码生成的路径 */
    @Schema(description = "前端代码生成的路径")
    protected String path;
    /** 前端Api文件路径 */
    @Schema(description = "前端Api文件路径")
    protected String apiPath;
    /** 表前缀 */
    @Schema(description = "表前缀")
    protected String prefix;
    /** 接口名称 */
    @Schema(description = "接口名称")
    protected String apiAlias;
}
