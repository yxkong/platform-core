package com.github.platform.core.standard.constant;

/**
 * 统一状态码
 * @author: yxkong
 * @date: 2022/6/25 12:11 PM
 * @version: 1.0
 */
public enum DeleteEnum {
    YES(1,"删除"),
    NO(0,"未删除");

    DeleteEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private Integer status;
    private String desc;
    public static DeleteEnum of(Integer status) {
        for (DeleteEnum value : DeleteEnum.values()) {
            if (value.getStatus().equals(status) ) {
                return value;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
