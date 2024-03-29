package com.github.platform.core.workflow.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
* 表单数据模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:23.341
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class FormDataBase extends BaseAdminEntity   {

    /** 表单编号 */
    @Schema(description = "表单编号")
    @NotEmpty(message="表单编号（formNo）不能为空")
    protected String formNo;
    /** 实例编号 */
    @Schema(description = "实例编号")
    @NotEmpty(message="实例编号（instanceNo）不能为空")
    protected String instanceNo;
    /** 输入框类型 input、textarea、select、radio、upload */
    @Schema(description = "输入框类型 input、textarea、select、radio、upload")
    @NotEmpty(message="输入框类型 input、textarea、select、radio、upload（type）不能为空")
    protected String type;
    /** 中文显示 */
    @Schema(description = "中文显示")
    @NotEmpty(message="中文显示（label）不能为空")
    protected String label;
    /** 字段名称 */
    @Schema(description = "字段名称")
    @NotEmpty(message="字段名称（name）不能为空")
    protected String name;
    /** 数据值 */
    @Schema(description = "数据值")
    @NotEmpty(message="数据值（value）不能为空")
    protected String value;
    /** 排序 */
    @Schema(description = "排序")
    protected Integer sort;
}
