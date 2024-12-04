package com.github.platform.core.workflow.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 表单配置模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class FormBase extends BaseAdminEntity   {
    /** 表单编号 */
    @Schema(description = "表单编号")
    protected String formNo;
    /** 表单名称 */
    @Schema(description = "表单名称")
    @NotEmpty(message="表单名称（formName）不能为空")
    protected String formName;
    /** 表单描述 */
    @Schema(description = "表单描述")
    @NotEmpty(message="表单描述（formDesc）不能为空")
    protected String formDesc;
    /** 渠道 */
    @Schema(description = "渠道")
    @NotEmpty(message="渠道（formChannel）不能为空")
    protected String formChannel;
}
