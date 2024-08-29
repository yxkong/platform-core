package com.github.platform.core.workflow.domain.constant;

import com.github.platform.core.cache.infra.constant.BaseSequence;
import lombok.Getter;

/**
 * 项目序列号配置
 * @author yxkong
 * @create 2023/2/16 上午10:32
 * @desc SequenceEnum
 */
@Getter
public enum WorkFlowSequenceEnum implements BaseSequence {
    //流程
    FLW("P", "yyMMdd", "0000", DB_INIT),
    //审批流
    FLW_INSTANCE("INST","yyMMdd","00000", DB_INIT),
    FORM("FNO","yyMMdd","00000", DB_INIT),
    FORM_INSTANCE("FINST","yyMMdd","00000", DB_INIT),
    ;


    private String prefix;
    private String format;
    private String cacheInit;
    private Long dbInit;

    WorkFlowSequenceEnum(String prefix, String format, String cacheInit, Long dbInit) {
        this.prefix = prefix;
        this.format = format;
        this.cacheInit = cacheInit;
        this.dbInit = dbInit;
    }
}
