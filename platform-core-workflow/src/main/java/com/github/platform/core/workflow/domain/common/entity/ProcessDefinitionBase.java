package com.github.platform.core.workflow.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 流程定义模型实体
 * 流程状态，0草稿，1激活，2挂起或禁用
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class ProcessDefinitionBase extends BaseAdminEntity   {
    /** 流程定义编号 */
    @Schema(description = "流程定义编号")
    protected String processNo;
    /** 流程定义名称 */
    @Schema(description = "流程定义名称")
    @NotEmpty(message="流程定义名称（processName）不能为空")
    protected String processName;
    /** 流程类型 */
    @Schema(description = "流程类型")
    @NotEmpty(message="流程类型（processType）不能为空")
    protected String processType;
    /** 流程定义描述 */
    @Schema(description = "流程定义描述")
    protected String processDesc;
    /** 流程部署id */
    @Schema(description = "流程部署id")
    protected String deployId;
    /** 审批流版本号 */
    @Schema(description = "审批流版本号")
    protected Integer processVersion;
    /** 审批流文件 */
    @Schema(description = "审批流文件")
    protected String processFile;
    /** 归属部门 */
    @Schema(description = "归属部门")
    @NotNull(message="归属部门（deptId）不能为空")
    protected Long deptId;
    /** 是否删除, 1删除/0否 */
    @Schema(description = "是否删除, 1删除/0否")
    protected Integer deleted;
    public String getVersion(){
        return "V"+ this.processVersion;
    }
}
