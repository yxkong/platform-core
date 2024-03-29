package com.github.platform.core.workflow.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
* 流程条件模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-30 18:17:25.794
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class ProcessConditionBase extends BaseAdminEntity   {
    /** 条件名称 */
    @Schema(description = "条件名称")
    @NotEmpty(message="条件名称（name）不能为空")
    protected String name;
    /** 条件表达式 */
    @Schema(description = "条件表达式")
    @NotEmpty(message="条件表达式（expression）不能为空")
    protected String expression;
    /** 流程类型,pm,oa */
    @Schema(description = "流程类型,pm,oa")
    @NotEmpty(message="流程类型,pm,oa（processType）不能为空")
    protected String processType;
}
