package com.github.platform.core.sys.domain.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发动审批流
 * @author: hdy
 * @date: 2023/7/3 3:57 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendProccessContext {

    private String formCode;

    private String processCode;

    private String ccUser;

    private List<ProcessForm> forms;

    private List<ProcessApprover> approvers;

    @Data
    @Builder
    public static class ProcessForm {
        private String name; // 和表单name一致
        private String value;
    }
    @Data
    @Builder
    public static class ProcessApprover {
        /**
         * 节点code（扩展）
         */
        private String nodeCode;
        /**
         * 审批人列表（可以是多个，只有固定的时候取这个里面的）
         */
        private String approver;
        /**
         * 类型 AND：会签 OR：或签 NONE：单人审批
         */
        private String type;
    }


}


