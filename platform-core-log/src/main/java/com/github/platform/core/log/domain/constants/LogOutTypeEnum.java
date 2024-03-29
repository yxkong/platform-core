package com.github.platform.core.log.domain.constants;

import lombok.Getter;

/**
 * 记录日志输出类型
 * @author: yxkong
 * @date: 2023/5/2 12:00 下午
 * @version: 1.0
 */
@Getter
public enum LogOutTypeEnum {
    local("local","记录日志到本地"),
    db("db","记录日志到数据库"),
    kafka("kafka","记录日志到kafka"),
    mixDb("mixDb","混记录到本地和数据库里"),
    mixKafka("mixKafka","混记录到本地和kafka里")
    ;

    private String type;
    private String desc;

    LogOutTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static LogOutTypeEnum of(String type){
        for (LogOutTypeEnum logType: LogOutTypeEnum.values()){
            if (logType.getType().equalsIgnoreCase(type)){
                return logType;
            }
        }
        return null;
    }
}
