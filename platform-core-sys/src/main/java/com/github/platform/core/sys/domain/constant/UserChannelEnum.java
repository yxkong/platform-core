package com.github.platform.core.sys.domain.constant;

import lombok.Getter;

/**
 * 用户来源渠道枚举
 * @author: yxkong
 * @date: 2023/5/26 2:41 PM
 * @version: 1.0
 */
@Getter
public enum UserChannelEnum {
    reg("reg","正常注册"),
    add("add","后台添加"),
    ldap("ldap","ldap"),
    thirdWx("wx","三方微信"),
    thirdDing("dingTalk","三方钉钉"),
    thirdQQ("qq","三方qq"),
    ;
    private String type;
    private String desc;

    UserChannelEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static UserChannelEnum getDefault(){
        return UserChannelEnum.add;
    }
    public static UserChannelEnum of(String type){
        for (UserChannelEnum wayEnum: UserChannelEnum.values()){
            if (wayEnum.getType().equalsIgnoreCase(type)){
                return wayEnum;
            }
        }
        return null;
    }

}
