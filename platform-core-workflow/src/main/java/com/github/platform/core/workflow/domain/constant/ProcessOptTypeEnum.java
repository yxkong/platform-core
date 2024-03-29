package com.github.platform.core.workflow.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 审批备注信息
 * @author: yxkong
 * @date: 2023/11/7 18:27
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public enum ProcessOptTypeEnum {
    NORMAL("1", "completeApprovalGateway","通过"),
    REBACK("2", "","退回"),
    REJECT("3", "rejectApprovalGateway","驳回"),
    DELEGATE("4","delegateApprovalGateway", "委派"),
    TRANSFER("5","transferApprovalGateway", "转办"),
    STOP("6","", "终止"),
    REVOKE("7","", "撤回")
    ;
    private String type;
    private String bean;
    private String desc;

    public static ProcessOptTypeEnum of(String optType) {
        return Arrays.stream(ProcessOptTypeEnum.values()).filter(s-> s.type.equalsIgnoreCase(optType)).findFirst().orElse(null);
    }
}
