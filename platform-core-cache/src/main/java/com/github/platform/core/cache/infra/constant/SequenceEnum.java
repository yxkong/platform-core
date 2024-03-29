package com.github.platform.core.cache.infra.constant;

import lombok.Getter;

/**
 * 序号生成枚举
 * @author wangxiaozhou
 * @create 2023/2/16 上午10:32
 * @desc SequenceEnum
 */
@Getter
public enum SequenceEnum {
    // 短信模板
    MSS_SMS_TEMPLATE("M", "yyMMdd", "00", "100000"),
    // 短信供应商
    MSS_SMS_SP("SP", "yyMMdd", "00", "100000"),
    //流程
    FLW("P", "yyMMdd", "0000", "100000"),
    BIZ("BIZ", "yyMMdd", "0000", "100000"),
    //审批流
    FLW_INSTANCE("INST","yyMMdd","00000", "100000"),
    FORM("FNO","yyMMdd","00000", "100000"),
    FORM_INSTANCE("FINST","yyMMdd","00000", "100000"),
    ;


    private String prefix;
    private String format;
    private String cacheInit;
    private String dbInit;

    SequenceEnum(String prefix, String format, String cacheInit, String dbInit) {
        this.prefix = prefix;
        this.format = format;
        this.cacheInit = cacheInit;
        this.dbInit = dbInit;
    }
}
