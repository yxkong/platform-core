package com.github.platform.core.sms.domain.constant;

import com.github.platform.core.cache.infra.constant.BaseSequence;
import lombok.Getter;

/**
 * 短信序号生成枚举
 * @author yxkong
 * @create 2023/2/16 上午10:32
 * @desc SequenceEnum
 */
@Getter
public enum SmsSequenceEnum implements BaseSequence {
    // 短信模板
    MSS_SMS_TEMPLATE("M", "yyMMdd", "000", DB_INIT),
    // 短信供应商
    MSS_SMS_SP("SP", "yyMMdd", "000", DB_INIT),
    ;

    private String prefix;
    private String format;
    private String cacheInit;
    private Long dbInit;

    SmsSequenceEnum(String prefix, String format, String cacheInit, Long dbInit) {
        this.prefix = prefix;
        this.format = format;
        this.cacheInit = cacheInit;
        this.dbInit = dbInit;
    }
}
