package com.github.platform.core.sys.adapter.api.command;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.github.platform.core.sys.domain.constant.ApproverTypeEnum;
import com.github.platform.core.sys.domain.constant.AuditTypeEnum;
import com.github.platform.core.sys.domain.constant.FormsChannelEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
* 流程创建
* @author hdy
* @time 2023-06-29 14:37:49.624
* @version 1.0
*/
@Data
@Schema(description = "流程创建或者更新")
public class FormAddOrUpdateCmd {
	
	/**
     * 表单code（自定义）
     */
	
    @Schema(description ="表单code（非必填，更新必填）")
    private String formCode;
    
	/**
     * 流程code（钉钉返回的）
     */
    @Schema(description ="流程三方code，非必填")
    private String processCode;
    
	/**
     * 表单名称
     */
    @NotNull(message = "表单名称不能为空")
    @Schema(description ="表单名称")
    private String formName;
    /**
     * 表单描述
     */
    @NotNull(message = "表单描述不能为空")
    @Schema(description ="表单描述")
    private String formDescribe;
    
    /**
     * 抄送人
     */
    @Schema(description ="非必填，抄送人")
    private List<String> ccUserList;
    
    /**
     * 渠道 dingding
     */
    @NotNull(message = "表单渠道不能为空")
    @Schema(description ="表单渠道 dingding")
    private FormsChannelEnum formsChannel;
    
    @Schema(description ="审核人列表")
    private List<FormApprover> formApprovers;
    
    @Schema(description ="表单详情list")
    private List<FormInfo> formInfoList;
    
    /**
     * 表单详情
     * @author Administrator
     *
     */
    @Data
    public static class FormInfo {
    	
    	@Schema(description ="info表id，更新的时候必填")
        private Long id;
    	
    	/**
         * 输入框类型 TextareaField 默认为多行文本框
         */
    	@Schema(description ="输入框类型 TextareaField 默认为多行文本框")
        private String type = "TextareaField";
        /**
         * 输入框名称
         */
    	@Schema(description ="输入框名称")
        private String name;
    }
    
    /**
     * 表单审核人信息
     * @author Administrator
     *
     */
    @Data
    public static class FormApprover {
    	@Schema(description ="info表id，更新的时候必填")
        private Long id;
    	
    	/**
         * 审批人类型 optional（自定义） fixed（固定） deptment（部门负责人）
         */
    	@Schema(description ="审批人类型 optional（自定义） fixed（固定） deptment（部门负责人）")
        private ApproverTypeEnum approverType;
        /**
         * 审批人列表（只有固定的时候取这个里面的）
         */
    	@Schema(description ="审批人列表（可以是多个，只有固定的时候取这个里面的）")
        private List<String> approver;
        /**
         * 类型 AND：会签 OR：或签 NONE：单人审批
         */
    	@Schema(description ="类型 AND：会签 OR：或签 NONE：单人审批")
        private AuditTypeEnum type;
        
        /**
         * 排序
         */
    	@Schema(description ="排序")
        private Integer sort;
    	
    }
	
}
