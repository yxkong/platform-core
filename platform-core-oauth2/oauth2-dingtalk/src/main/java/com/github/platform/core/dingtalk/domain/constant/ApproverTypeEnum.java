package com.github.platform.core.dingtalk.domain.constant;

import lombok.Getter;
/**
 * 表单审批人类型
 * @author: yxkong
 * @date: 2023/6/29 1:32 PM
 * @version: 1.0
 */
@Getter
public enum ApproverTypeEnum {
	optional("optional","自定义"),
	fixed("fixed","固定"),
	deptment("deptment","部门负责人")
;
    private String type;
    private String desc;
    ApproverTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ApproverTypeEnum of(String type){
        for (ApproverTypeEnum typeEnum:ApproverTypeEnum.values()){
            if (typeEnum.getType().equalsIgnoreCase(type)){
                return typeEnum;
            }
        }
        return null;
    }
}
