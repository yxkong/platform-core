package com.github.platform.core.sys.domain.constant;


/**
 * @author yxkong
 */
public enum SysStatusEnum {
    /**************sys系统对应的状态定义，原则上，非通用的不能以1开头***************/
    /**
     * 验证码校验失败
     */
    SUCCESS("2000", "验证码校验失败！"),
    /**
     * 灰度
     */
    GRAY("2999", "xxxx");

    private String status;

    private String message;

    SysStatusEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
