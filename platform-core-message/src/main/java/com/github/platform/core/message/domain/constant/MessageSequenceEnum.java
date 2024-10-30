package com.github.platform.core.message.domain.constant;

import com.github.platform.core.cache.infra.constant.BaseSequence;
import lombok.Getter;

/**
 * 消息序号枚举
 * @Author: yxkong
 * @Date: 2024/10/11
 * @version: 1.0
 */
@Getter
public enum MessageSequenceEnum implements BaseSequence {
    NT("NT", "yyMMdd", "00000", DB_INIT);

    private String prefix;
    private String format;
    private String cacheInit;
    private Long dbInit;

    MessageSequenceEnum(String prefix, String format, String cacheInit, Long dbInit) {
        this.prefix = prefix;
        this.format = format;
        this.cacheInit = cacheInit;
        this.dbInit = dbInit;
    }
}
