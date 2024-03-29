package com.github.platform.core.sys.domain.constant;

import lombok.Getter;

/**
 * 登录方式枚举
 * @author: yxkong
 * @date: 2022/4/29 3:03 PM
 * @version: 1.0
 */
@Getter
public enum LoginWayEnum {
    normal("normal","normalLoginGateway","普通登录"),
    ldap("ldap","ldapLoginGateway","ldap登录"),
    sms("sms","smsLoginGateway","短信登录"),
    thirdWx("wx","wxLoginGateway","三方微信登录"),
    thirdAlipay("alipay","alipayLoginGateway","三方支付宝登录"),
    thirdQQ("qq","","三方qq登录"),
    ;
    private String type;
    private String bean;
    private String desc;

    LoginWayEnum(String type,String bean, String desc) {
        this.type = type;
        this.bean = bean;
        this.desc = desc;
    }

    public static LoginWayEnum getDefault(){
        return LoginWayEnum.normal;
    }
    public static LoginWayEnum of(String type){
        for (LoginWayEnum wayEnum:LoginWayEnum.values()){
            if (wayEnum.getType().equalsIgnoreCase(type)){
                return wayEnum;
            }
        }
        return null;
    }
    public static Boolean isThird(LoginWayEnum loginWay){
        if (loginWay == LoginWayEnum.thirdWx || loginWay == LoginWayEnum.thirdAlipay || loginWay == LoginWayEnum.thirdQQ){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
