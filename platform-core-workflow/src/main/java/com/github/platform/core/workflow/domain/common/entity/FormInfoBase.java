package com.github.platform.core.workflow.domain.common.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 表单信息模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:21.269
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class FormInfoBase extends BaseAdminEntity   {
    /** 表单编号 */
    @Schema(description = "表单编号")
    @NotEmpty(message="表单编号（formNo）不能为空")
    protected String formNo;
    /** 输入框类型 */
    @Schema(description = "输入框类型")
    @NotEmpty(message="输入框类型（type）不能为空")
    protected String type;
    /** 中文名 */
    @Schema(description = "中文名")
    @NotEmpty(message="中文名（label）不能为空")
    protected String label;
    /** 字段名 */
    @Schema(description = "字段名")
    @NotEmpty(message="字段名（name）不能为空")
    protected String name;
    /** 分组名称 */
    @Schema(description = "分组名称")
    protected String groupName;
    /** 数据长度，最大300 */
    @Schema(description = "数据长度，最大300")
    @NotNull(message="数据长度，最大300（len）不能为空")
    protected Integer len;
    /** 是否必填 */
    @Schema(description = "是否必填")
    protected Integer required;
    /** 排序 */
    @Schema(description = "排序")
    protected Integer sort;
    /** 配置项名称 */
    @Schema(description = "配置项名称")
    protected String optionName;
    /** 默认值 */
    @Schema(description = "默认值")
    protected String defaultVal;
    @JsonIgnore
    public boolean isOption(){
        return "radio".equals(this.type) || "select".equals(this.type);
    }
}
