package com.github.platform.core.code.domain.constant;

import lombok.Getter;

/**
 * 查询类型枚举
 * @Author: yxkong
 * @Date: 2023/4/27 3:47 PM
 * @version: 1.0
 */
@Getter
public enum QueryTypeEnum {
    eq("=","等于"),
    lt("<","小于"),
    leq("<=","小于等于"),
    gt(">","大于"),
    geq(">=","大于等于"),
    ne("!=","不等于"),
    like("like","模糊"),
    isNull("isNull","为null"),
    notNull("notnull","不为null"),
    between("between","在什么之间");
    private String type ;
    private String desc ;
    QueryTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }


}
