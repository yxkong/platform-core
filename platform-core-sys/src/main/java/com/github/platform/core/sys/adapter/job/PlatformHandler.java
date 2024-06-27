package com.github.platform.core.sys.adapter.job;

import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.schedule.infra.handler.AbstractJobMonitorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

/**
 * 项目通知处理
 * @author: yxkong
 * @date: 2024/6/8 10:51
 * @version: 1.0
 */
@Component
@Slf4j
public class PlatformHandler extends AbstractJobMonitorHandler {
    @Override
    public Pair<Boolean, String> bizHandler(SysJobDto jobDto) throws Exception {
        /**
         * 1，查找所有的配置项
         * 2，按配置的租户处理任务
         */


        return Pair.of(true,"处理完成！");
    }

}
