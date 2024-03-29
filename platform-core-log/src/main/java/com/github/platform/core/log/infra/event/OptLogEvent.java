package com.github.platform.core.log.infra.event;

import com.github.platform.core.log.domain.entity.OptLogEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 操作日志事件
 * @author: yxkong
 * @date: 2023/5/4 11:04 AM
 * @version: 1.0
 */
public class OptLogEvent extends ApplicationEvent {

    private OptLogEntity optLog;
    public OptLogEvent(OptLogEntity optLog) {
        super(optLog);
        this.optLog = optLog;
    }

    public OptLogEntity getOptLog() {
        return optLog;
    }
}
