package com.github.platform.core.sys.adapter.api.command;

import com.github.platform.core.sys.domain.constant.ApproverTypeEnum;
import com.github.platform.core.sys.domain.constant.AuditTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
* 发起审批流
* @author hdy
* @time 2023-06-29 14:37:49.624
* @version 1.0
*/
@Data
@Schema(description = "发起审批流")
public class FormSendProCmd {

	/**
     * 表单code（自定义）
     */
	
    @Schema(description ="表单code（非必填，更新必填）")
    private String formCode;
    
	/**
     * 流程code（钉钉返回的）
     */
    @Schema(description ="非必填，抄送人")
    private String ccUser;
    
	/**
     * 表单名称
     */
    @NotNull(message = "表单信息不能为空")
    @Schema(description ="表单信息")
    private Map formInfoMap;

    @NotNull(message = "审批人不能为空")
    @Schema(description ="审批人")
    private List<FormApprover> approvers;
    /**
     * 表单审核人信息
     * @author Administrator
     *
     */
    @Data
    public static class FormApprover {
        /**
         * 审批人列表（只有固定的时候取这个里面的）
         */
    	@Schema(description ="审批人列表")
        private List<String> approver;
        /**
         * 类型 AND：会签 OR：或签 NONE：单人审批
         */
    	@Schema(description ="类型 AND：会签 OR：或签 NONE：单人审批")
        private AuditTypeEnum type;
    }
	
}
