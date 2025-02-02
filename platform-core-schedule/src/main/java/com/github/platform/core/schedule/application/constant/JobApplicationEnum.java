package com.github.platform.core.schedule.application.constant;

import com.github.platform.core.standard.exception.BaseResult;

/**
 * 任务管理异常
 * @author: yxkong
 * @date: 2023/9/5 4:44 PM
 * @version: 1.0
 */
public enum JobApplicationEnum implements BaseResult {
    CRON_VALIDATE("2001","cron 表达式校验异常"),
    CRON_UNIQUE("2002", "已存在对应beanName的job，请核验！"),
    ADD_ERROR("2003","添加job任务异常！" ),
    UPDATE_ERROR("2004","更新job任务异常！" ),
    QUARTZ_IS_DISABLED("2005","quartz已禁用，禁止操作！" ),
    JOB_NOT_EXIST("2006", "任务不存在"),
    JOB_DELETE_ERROR("2007", "job删除异常！"),
    JOB_BEAN_NAME_ERROR("2008", "job的BeanName异常，远程和多实例必须带:号！"),
    JOB_BEAN_NAME_DOT_EXIST("2009", "job的BeanName并未在系统中实现！"),
    ;


    JobApplicationEnum(String status, String message) {
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
