package com.github.platform.core.sys.domain.constant;

import lombok.Getter;
/**
 * 表单审批类型
 * @author: hdy
 * @date: 2023/6/29 1:32 PM
 * @version: 1.0
 */
@Getter
public enum AuditTypeEnum {
	AND("AND","会签"),
	OR("OR","或签"),
	NONE("NONE","单人审批")
;
    private String type;
    private String desc;
    AuditTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static AuditTypeEnum of(String type){
        for (AuditTypeEnum typeEnum:AuditTypeEnum.values()){
            if (typeEnum.getType().equalsIgnoreCase(type)){
                return typeEnum;
            }
        }
        return null;
    }
}
