package com.github.platform.core.log.infra.event;

import com.github.platform.core.log.domain.entity.CacheLogEntity;
import org.springframework.context.ApplicationEvent;

/**
 * cachelog 事件
 * @author: yxkong
 * @date: 2023/5/5 2:59 PM
 * @version: 1.0
 */
public class CacheLogEvent extends ApplicationEvent {
    private CacheLogEntity cacheLog;
    public CacheLogEvent(CacheLogEntity cacheLog) {
        super(cacheLog);
        this.cacheLog = cacheLog;
    }

    public CacheLogEntity getCacheLog() {
        return cacheLog;
    }
}
