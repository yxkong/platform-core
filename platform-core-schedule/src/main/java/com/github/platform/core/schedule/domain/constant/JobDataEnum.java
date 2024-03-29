package com.github.platform.core.schedule.domain.constant;

import lombok.Getter;

/**
 * quartz job的枚举
 * @author: yxkong
 * @date: 2023/9/5 11:00 AM
 * @version: 1.0
 */
@Getter
public enum JobDataEnum {
    ID("id","任务id"),
    HANDLER_NAME("handler_name","任务名称"),
    HANDLER_PARAM("handler_param","任务固定参数"),
    RETRY_COUNT("retry_count","重试次数，最多重试的次数"),
    RETRY_INTERVAL("retry_interval","重试间隔"),
    EXECUTE_USER("executeUser","执行用户"),
    EXECUTE_ID("executeId","执行id"),
    ;
    private String key;
    private String desc;

    JobDataEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
