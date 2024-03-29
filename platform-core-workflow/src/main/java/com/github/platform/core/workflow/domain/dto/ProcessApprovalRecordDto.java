package com.github.platform.core.workflow.domain.dto;
import com.github.platform.core.workflow.domain.common.entity.ProcessApprovalRecordBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
* 流程审批记录传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-09 11:30:36.194
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "流程审批记录传输实体")
public class ProcessApprovalRecordDto extends ProcessApprovalRecordBase{
    /**
     * 任务耗时
     */
    private String duration;

    /**节点类型:start,userTask,end */
    private String nodeType;

    public void setDuration(String duration) {
    }
}