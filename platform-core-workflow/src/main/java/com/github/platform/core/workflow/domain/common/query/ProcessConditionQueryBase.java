package com.github.platform.core.workflow.domain.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程条件查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-30 18:17:25.794
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "流程条件查询")
public class ProcessConditionQueryBase extends PageQueryBaseEntity {
    /** 条件名称 */
    @Schema(description = "条件名称")
    private String name;
    /** 流程类型,pm,oa */
    @Schema(description = "流程类型,pm,oa")
    private String processType;
}