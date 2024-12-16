package com.github.platform.core.message.domain.constant;

import com.github.platform.core.cache.infra.constant.BaseSequence;
import lombok.Getter;

/**
 * 消息通知序列号配置
 * @author yxkong
 * @create 2023/2/16 上午10:32
 * @desc SequenceEnum
 */
@Getter
public enum NoticeSequenceEnum implements BaseSequence {
    NOTICE_TEMPLATE("NT", "yyMMdd", "00000", DB_INIT),
    ;


    private String prefix;
    private String format;
    private String cacheInit;
    private Long dbInit;

    NoticeSequenceEnum(String prefix, String format, String cacheInit, Long dbInit) {
        this.prefix = prefix;
        this.format = format;
        this.cacheInit = cacheInit;
        this.dbInit = dbInit;
    }
}
