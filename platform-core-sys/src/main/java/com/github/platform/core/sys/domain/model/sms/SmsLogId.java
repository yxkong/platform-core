package com.github.platform.core.sys.domain.model.sms;

import com.github.platform.core.standard.annotation.DomainEntity;
import lombok.Getter;

/**
 * 日志实体
 * @author: yxkong
 * @date: 2022/11/24 10:54 AM
 * @version: 1.0
 */
@DomainEntity
@Getter
public class SmsLogId {
    private Long id;
    private Integer tenantId;
    public SmsLogId(Long id, Integer tenantId) {
        this.id = id;
        this.tenantId = tenantId;
    }
}
