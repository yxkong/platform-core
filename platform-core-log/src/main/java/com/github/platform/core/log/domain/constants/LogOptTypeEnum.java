package com.github.platform.core.log.domain.constants;

import lombok.Getter;

/**
 * 日志操作类型
 * @author: yxkong
 * @date: 2023/6/8 5:07 PM
 * @version: 1.0
 */
@Getter
public enum LogOptTypeEnum {
    /**
     *  日志分级
     */
    query("query",0,"查询日志"),
    detail("detail",1,"明细日志"),
    add("add",2,"新增日志"),
    modify("modify",2,"修改日志"),
    delete("delete",2,"删除日志"),
    trigger("trigger",2,"触发日志"),
    mix("mix",2,"混合操作日志")
    ;

    private String type;
    private Integer val;
    private String desc;

    LogOptTypeEnum(String type,Integer val, String desc) {
        this.type = type;
        this.val = val;
        this.desc = desc;
    }
    public static LogOptTypeEnum of(String type){
        for (LogOptTypeEnum logType: LogOptTypeEnum.values()){
            if (logType.getType().equalsIgnoreCase(type)){
                return logType;
            }
        }
        return null;
    }
}
