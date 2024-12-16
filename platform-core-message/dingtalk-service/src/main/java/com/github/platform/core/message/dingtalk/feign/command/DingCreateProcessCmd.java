package com.github.platform.core.message.dingtalk.feign.command;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 钉钉创建审批入参
 * @author
 */
@Data
@Builder
public class DingCreateProcessCmd implements Serializable {

    /** 审批流的唯一码*/
	private String processCode;
    /** 审批发起人的userId */
	private String originatorUserId;
    /** 部门id*/
	private Long deptId;
    /**使用审批流模板时，流程预测结果中节点规则上必填的自选操作人列表，最大列表长度：20。*/
    private List<FormComponentValues> formComponentValues;
    /** 不用了，用下面的 表单数据内容，控件列表，最大列表长度：150。*/
    private List<TargetSelectActioners> targetSelectActioners;
    /**审批人*/
    private List<Approver> approvers;
    /**抄送人*/
    private List<String> ccList;
    /** 抄送时间点，取值：
     *  START：开始时抄送
     *  FINISH：结束时抄送
     *  START_FINISH：开始和结束时都抄送
     */
    @Builder.Default
    private String ccPosition = "START_FINISH";
    
    @Data
    @Builder
    public static class FormComponentValues{
        /** 和表单name一致*/
    	private String name;
        private String value;
    }
    
    @Data
    @Builder
    public static class TargetSelectActioners {
        /**自选节点的规则key */
        private String actionerKey;
        /** 操作人 userId */
        private List<String> actionerUserIds;
    }
    @Data
    @Builder
    public static class Approver{
        /** AND：会签 OR：或签 NONE：单人审批 */
    	private String actionType;
        private List<String> userIds;
    }
}
