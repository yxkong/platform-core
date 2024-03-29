package com.github.platform.core.workflow.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
* 流程实例查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "流程实例查询")
public class ProcessInstanceQueryBase extends PageQueryBaseEntity {
    /** 业务编号 */
    @Schema(description = "业务编号")
    private String bizNo;
    /** 实例编号 */
    @Schema(description = "实例编号")
    private String instanceNo;
    /** 实例名称 */
    @Schema(description = "实例名称")
    private String instanceName;
    @Schema(description = "业务类型")
    private String processType;
    /** 实例ID */
    @Schema(description = "实例ID")
    private String instanceId;


}