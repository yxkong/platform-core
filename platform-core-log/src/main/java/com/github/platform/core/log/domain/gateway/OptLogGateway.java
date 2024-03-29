package com.github.platform.core.log.domain.gateway;

import com.github.platform.core.log.domain.entity.OptLogEntity;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 操作日志网关
 *
 * @author: yxkong
 * @date: 2023/5/4 3:08 PM
 * @version: 1.0
 */
public interface OptLogGateway {
    /**
     * 插入日志
     * @param log
     * @return
     */
    Pair<Boolean,String> insert(OptLogEntity log);
}
