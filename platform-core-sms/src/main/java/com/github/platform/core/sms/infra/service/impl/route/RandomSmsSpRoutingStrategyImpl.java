package com.github.platform.core.sms.infra.service.impl.route;

import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
import com.github.platform.core.sms.infra.service.ISmsSpRoutingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机策略
 * @author: yxkong
 * @date: 2024/3/27 14:15
 * @version: 1.0
 */
@Service("randomSmsSpRoutingStrategy")
@Slf4j
public class RandomSmsSpRoutingStrategyImpl implements ISmsSpRoutingStrategy {
    @Override
    public SysSmsTemplateStatusDto route(SysSmsTemplateDto smsRoute, List<SysSmsTemplateStatusDto> list) {
        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }
}
