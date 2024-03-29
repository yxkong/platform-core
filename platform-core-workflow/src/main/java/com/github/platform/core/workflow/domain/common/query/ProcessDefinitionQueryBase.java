package com.github.platform.core.workflow.domain.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 流程定义查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "流程管理查询")
public class ProcessDefinitionQueryBase extends PageQueryBaseEntity {
    /** 流程编号 */
    @Schema(description = "流程编号")
    private String processNo;
    /** 审批流名称 */
    @Schema(description = "流程名称")
    private String processName;
    /** 删除标识 */
    @Schema(description = "删除标识")
    protected Integer deleted;
}