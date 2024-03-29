package com.github.platform.core.code.domain.constant;

import lombok.Getter;

/**
 * 代码类型枚举
 * @Author: yxkong
 * @Date: 2023/8/3 4:22 PM
 * @version: 1.0
 */
@Getter
public enum CodeTypeEnum {
    API(1, "api"),
    SYS(0, "sys");
    private Integer type ;
    private String name ;

    CodeTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
    public static String getName(Integer type){
        for (CodeTypeEnum x:CodeTypeEnum.values()){
            if (x.getType().equals(type)){
                return x.getName();
            }
        }
        return CodeTypeEnum.SYS.getName();
    }
    public static boolean isSys(Integer type){
        return CodeTypeEnum.SYS.getType().equals(type);
    }
    public static boolean isApi(Integer type){
        return CodeTypeEnum.API.getType().equals(type);
    }
}
