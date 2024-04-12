package com.github.platform.core.sms.infra.service.impl.route;

import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
import com.github.platform.core.sms.infra.service.ISmsSpRoutingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 随机策略
 * @author: yxkong
 * @date: 2024/3/27 
 * @version: 1.0
 */
@Service("roundRobinSmsSpRoutingStrategy")
@Slf4j
public class RoundRobinSmsSpRoutingStrategyImpl implements ISmsSpRoutingStrategy {
    private AtomicInteger currentIndex = new AtomicInteger(0);
    private final Integer  MAX = Integer.MAX_VALUE/2;
    @Override
    public SysSmsTemplateStatusDto route(SysSmsTemplateDto smsRoute, List<SysSmsTemplateStatusDto> list) {
        // 模运算确保索引始终在列表范围内
        int current;
        while (true) {
            current = currentIndex.get();
            if (current >= MAX.intValue()) {
                // 使用CAS操作原子性地更新currentIndex
                if (currentIndex.compareAndSet(MAX, 0)) {
                    break;
                }
            } else {
                break;
            }
        }
        int index = currentIndex.incrementAndGet() % list.size();
        return list.get(index);
    }
}
