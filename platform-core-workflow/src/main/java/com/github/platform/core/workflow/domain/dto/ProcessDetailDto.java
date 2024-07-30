package com.github.platform.core.workflow.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.utils.CollectionUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowable.bpmn.model.BpmnModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程详情
 * @author: yxkong
 * @date: 2023/11/13 12:40
 * @version: 1.0
 */
@Schema(description = "流程用户")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDetailDto {
    /**
     * 审批记录
     */
    @Schema(description = "审批记录，已办任务")
    private List<ProcessApprovalRecordDto> approvalRecords;
    /**
     * 流程xml
     */
    @Schema(description = "流程xml")
    private String bpmnXml;
    /**应用内流转使用，不返回前端*/
    @JsonIgnore
    private BpmnModel bpmnModel;
    /**
     * 流程实例信息,内部流转processDetailDto
     */
    @Schema(description = "流程实例信息")
    @JsonIgnore
    private ProcessInstanceDto instanceDto;
    /**
     * 流程任务状态，用于回显
     */
    @Schema(description = " 流程任务状态，用于回显")
    private ProcessTaskStatusDto taskStatus;
    @Schema(description = "表单信息")
    private List<FormViewAssemblyDto> formInfos;
    @Schema(description = "当前任务表单信息")
    private FormViewAssemblyDto taskFormInfo;

    public void addFormInfos(String title, List<FormInfoDto> list) {
        if (CollectionUtil.isEmpty(this.formInfos) ) {
            formInfos = new ArrayList<>();
        }
        this.formInfos.add(new FormViewAssemblyDto(title,list));
    }
}
