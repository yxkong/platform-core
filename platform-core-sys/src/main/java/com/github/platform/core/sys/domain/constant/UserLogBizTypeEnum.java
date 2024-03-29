package com.github.platform.core.sys.domain.constant;

import lombok.Getter;

/**
 * 用户日志业务类型
 * @author: yxkong
 * @date: 2023/1/5 10:53 AM
 * @version: 1.0
 */
@Getter
public enum UserLogBizTypeEnum {
    register(0,"注册"),
    login(1,"登录"),
    modify_pwd(2,"修改密码"),
    bind(4,"绑定账户"),
    third(5,"三方登录")
    ;

    private Integer type;
    private String desc;

    UserLogBizTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static UserLogBizTypeEnum getDefault(){
        return UserLogBizTypeEnum.register;
    }
    public static UserLogBizTypeEnum of(Integer type){
        for (UserLogBizTypeEnum typeEnum:UserLogBizTypeEnum.values()){
            if (typeEnum.getType().intValue() == type.intValue()){
                return typeEnum;
            }
        }
        return null;
    }
}
