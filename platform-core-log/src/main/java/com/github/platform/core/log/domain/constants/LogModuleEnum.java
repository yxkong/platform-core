package com.github.platform.core.log.domain.constants;

import lombok.Getter;

/**
 * 日志模块类型
 * @author: yxkong
 * @date: 2023/5/2 12:08 下午
 * @version: 1.0
 */
@Getter
public enum LogModuleEnum {

    gen("generator","代码生成"),
    auth("auth","用户认证"),
    cache("cache","缓存管理"),
    config("config","配置管理"),
    dept("dept","部门管理"),
    dict("dict","字典管理"),
    menu("menu","菜单管理"),
    role("role","角色管理"),
    user("user","用户管理"),
    lb("loadbalancer","灰度管理"),
    file("file","文件管理"),
    sms("sms","短信管理"),
    flowable("flowable","工作流");

    private String module;
    private String desc;

    LogModuleEnum(String module, String desc) {
        this.module = module;
        this.desc = desc;
    }
    public static LogModuleEnum of(String module){
        for (LogModuleEnum logModule:LogModuleEnum.values()){
            if (logModule.getModule().equalsIgnoreCase(module)){
                return logModule;
            }
        }
        return null;
    }
}
