package com.github.platform.core.schedule.infra.handler;

import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 可监控job抽象
 * @author: yxkong
 * @date: 2023/9/12 10:45 AM
 * @version: 1.0
 */
@Slf4j
public abstract class AbstractJobMonitorHandler implements IJobMonitorHandler{
    /**
     * redis key
     * @return
     */
    private String getLockKey(SysJobDto jobDto){
        return "p:u:l:"+jobDto.getId();
    }
    public String getLockId(){
        return StringUtils.uuidRmLine();
    }
    public abstract Pair<Boolean, String> bizHandler(SysJobDto jobDto) throws Exception;
    @Override
    public Pair<Boolean, String> execute(SysJobDto jobDto) throws Exception {
        String lockId = getLockId();
        String lockKey = getLockKey(jobDto);
        ICacheService cacheService = ApplicationContextHolder.getBean(ICacheService.class);
        boolean lock = cacheService.getLock(lockKey,lockId , lockTime);
        if (!lock){
            return Pair.of(true,"其他节点执行");
        }
        try{
            return bizHandler(jobDto);
        } finally {
            //释放锁，自己加的锁，只能自己释放
            cacheService.releaseLock(lockKey,lockId);
        }
    }

}
