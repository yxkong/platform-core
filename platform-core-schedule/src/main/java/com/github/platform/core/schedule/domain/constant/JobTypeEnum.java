package com.github.platform.core.schedule.domain.constant;

import lombok.Getter;

/**
 * jobType 枚举
 *
 * @author: yxkong
 * @date: 2023/9/19 9:59 AM
 * @version: 1.0
 */
@Getter
public enum JobTypeEnum {
    LOCAL(1,"本地运行"),
    CALLBACK(0,"回调类型")
    ;

    private Integer type;
    private String desc;

    JobTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
