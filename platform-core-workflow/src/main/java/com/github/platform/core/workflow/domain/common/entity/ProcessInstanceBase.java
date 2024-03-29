package com.github.platform.core.workflow.domain.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.entity.BaseAdminEntity;
import com.github.platform.core.workflow.domain.constant.InstanceStatusEnum;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
* 流程实例模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class ProcessInstanceBase extends BaseAdminEntity   {
    /** 业务编号 */
    @Schema(description = "业务编号")
    @NotEmpty(message="业务编号（bizNo）不能为空")
    protected String bizNo;
    /** 业务编号 */
    @Schema(description = "表单实例编号")
    @NotEmpty(message="表单实例编号（formInstNo）不能为空")
    protected String formInstNo;

    /** 流程类型 */
    @Schema(description = "流程类型")
    @NotNull(message="流程类型（processType）不能为空")
    protected String processType;
    /** 实例编号 */
    @Schema(description = "实例编号")
    protected String instanceNo;
    /** 实例名称 */
    @Schema(description = "实例名称")
    @NotEmpty(message="实例名称（instanceName）不能为空")
    protected String instanceName;
    /** 审批流实例ID */
    @Schema(description = "审批流实例ID")
    protected String instanceId;

    /** 流程定义编号 */
    @Schema(description = "流程定义编号")
    @NotEmpty(message="流程定义编号（processNo）不能为空")
    protected String processNo;
    /** 流程定义版本 */
    @Schema(description = "流程定义版本")
    @NotEmpty(message="审批流实例ID（instanceId）不能为空")
    protected Integer processVersion;
    /** 流程结束时间*/
    @Schema(description = "流程结束时间")
    protected LocalDateTime endTime;
    /** 归属部门 */
    @Schema(description = "归属部门")
    protected Long deptId;

    public String getVersion(){
        return "V"+ this.processVersion;
    }

    /**
     * 是否正常
     * @return
     */
    @JsonIgnore
    public boolean isNormal(){
        return !InstanceStatusEnum.ERROR.getStatus().equals(this.status);
    }

    @JsonIgnore
    public boolean isPm(){
        return ProcessTypeEnum.PM.getType().equals(this.processType);
    }
}