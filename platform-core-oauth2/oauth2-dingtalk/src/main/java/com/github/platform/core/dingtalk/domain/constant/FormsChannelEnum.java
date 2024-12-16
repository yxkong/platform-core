package com.github.platform.core.dingtalk.domain.constant;

import lombok.Getter;
/**
 * 表单渠道
 * @author: hdy
 * @date: 2023/6/29 1:32 PM
 * @version: 1.0
 */
@Getter
public enum FormsChannelEnum {
	
    dingding("dingding","钉钉");
	
    private String type;
    private String desc;
    FormsChannelEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static FormsChannelEnum of(String type){
        for (FormsChannelEnum typeEnum:FormsChannelEnum.values()){
            if (typeEnum.getType().equalsIgnoreCase(type)){
                return typeEnum;
            }
        }
        return null;
    }
}
