package com.github.platform.core.sys.adapter.api.constant;


import com.github.platform.core.standard.exception.BaseResult;

/**
 * 适配层业务异常枚举
 * @author: yxkong
 * @date: 2022/11/17 3:28 PM
 * @version: 1.0
 */
public enum SysAdapterResultEnum implements BaseResult {
    /**
     * 业务
     */
    dont_allow_opt("2101", "内置超级管理员不能修改！"),
    token_is_empty("2102", "用户token不能为空！"),

    ;

    SysAdapterResultEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private String status;
    private String message;

    @Override
    public String getStatus() {
        return status;
    }
    @Override
    public String getMessage() {
        return message;
    }
}