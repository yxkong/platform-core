package com.github.platform.core.gateway.admin.domain.constant;

import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.Getter;

import java.util.Objects;

/**
 * 条件枚举，有些配置没有验证过，需要后续w完善验证
 * @Author: yxkong
 * @Date: 2024/8/21
 * @version: 1.0
 */
@Getter
public enum ConditionEnum {
    // filters
    RewritePath("RewritePath","filter",2, SymbolConstant.comma,"regexp","replacement"),
    AddRequestHeader("AddRequestHeader","filter",2, SymbolConstant.comma,"name","value"),
    SetPath("SetPath","filter",1, null,"template",null),
    RemoveRequestHeader("RemoveRequestHeader","filter",1, null,"name",null),
    AddRequestParameter("AddRequestParameter","filter",2, SymbolConstant.comma,"name","value"),
    StripPrefix("StripPrefix","filter",1, null,"parts",null),
    Retry("Retry","filter",2, SymbolConstant.comma,"retries","backoff"),
    AddResponseHeader("AddResponseHeader","filter",2, SymbolConstant.comma,"name","value"),
    PrefixPath("PrefixPath","filter",1, null,"prefix",null),
    RemoveRequestParameter("RemoveRequestParameter","filter",1, null,"name",null),
    RemoveResponseHeader("RemoveResponseHeader","filter",1, null,"name",null),
    RedirectTo("RedirectTo","filter",2, SymbolConstant.comma,"status","url"),
    RewriteResponseHeader("RewriteResponseHeader","filter",2, SymbolConstant.comma,"header","regexp"),
    SetResponseStatusCode("SetResponseStatusCode","filter",1, null,"status","null"),
    CUSTOM("Custom","filter",2, SymbolConstant.comma,"name","args"),

    // predicates
    Path("Path","predicate",1, null,"pattern",""),
    Header("Header","predicate",2, SymbolConstant.comma,"header","regexp"),
    Method("Method","predicate",1, null,"method",""),
    Query("Query","predicate",2, SymbolConstant.comma,"param","regexp"),
    Host("Host","predicate",1, null,"host",""),
    Cookie("Cookie","predicate",2, SymbolConstant.comma,"name","value"),
    RemoteAddr("RemoteAddr","predicate",1, null,"remoteAddr",""),
    After("After","predicate",1, null,"datetime",""),
    Before("Before","predicate",1, null,"datetime",""),
    Between("Between","predicate",2, SymbolConstant.comma,"datetime1","datetime2"),
    Weight("Weight","predicate",2, SymbolConstant.comma,"group","weight");
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
