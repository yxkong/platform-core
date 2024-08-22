package com.github.platform.core.gateway.admin.domain.constant;

import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.Getter;

import java.util.Objects;

/**
 * 条件枚举
 * @Author: yxkong
 * @Date: 2024/8/21
 * @version: 1.0
 */
@Getter
public enum ConditionEnum {
    RewritePath("RewritePath","filter",2, SymbolConstant.comma,"regexp","replacement"),
    AddRequestHeader("AddRequestHeader","filter",2, SymbolConstant.comma,"name","value"),
    SetPath("SetPath","filter",1, null,"template",null),
    RemoveRequestHeader("RemoveRequestHeader","filter",1, null,"name",null),
    AddRequestParameter("AddRequestParameter","filter",2, SymbolConstant.comma,"name","value"),
    StripPrefix("StripPrefix","filter",1, null,"prefixes",null),
    Retry("Retry","filter",2, SymbolConstant.comma,"retries","backoff"),
    Path("Path","predicate",1, null,"pattern",""),
    Header("Header","predicate",2, SymbolConstant.comma,"header","regexp"),
    Method("Method","predicate",1, null,"method",""),
    Query("Query","predicate",2, SymbolConstant.comma,"param","regexp"),
    Host("Host","predicate",1, null,"host",""),
    Cookie("Cookie","predicate",2, SymbolConstant.comma,"name","value"),
    RemoteAddr("RemoteAddr","predicate",1, null,"remoteAddr",""),
    ;

    ConditionEnum(String name,String type, Integer length, String split, String split0, String split1) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.split = split;
        this.split0 = split0;
        this.split1 = split1;
    }

    private final String name;
    private final String type;
    private final Integer length;
    private final String split;
    private final String split0;
    private final String split1;

    public static ConditionEnum getByName(String name) {
        for (ConditionEnum conditionEnum : ConditionEnum.values()) {
            if (conditionEnum.name.equals(name)) {
                return conditionEnum;
            }
        }
        return null;
    }
    public boolean isFilter() {
        return Objects.equals("filter",this.type) ;
    }
}
