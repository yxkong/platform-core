package com.github.platform.core.schedule.infra.handler;

import com.github.platform.core.schedule.domain.dto.SysJobDto;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 任务处理器
 * @author: yxkong
 * @date: 2023/8/21 7:14 PM
 * @version: 1.0
 */
public interface IJobMonitorHandler {
    /**
     * 加锁时间
     */
    final Long lockTime = 60L;
    /**
     * 任务执行，每个任务都需要考虑分布式的问题
     * 需要使用分布式锁，有正在执行的任务，就不要再执行了
     * @param jobDto
     * @param jobDto json参数
     * @return
     * @throws Exception
     */
    Pair<Boolean,String> execute(SysJobDto jobDto) throws Exception;
}
