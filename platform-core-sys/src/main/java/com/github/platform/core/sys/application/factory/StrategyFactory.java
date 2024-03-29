package com.github.platform.core.sys.application.factory;

import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.sys.infra.service.impl.verify.CaptchaVerifyStrategyImpl;
import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import com.github.platform.core.sys.domain.service.VerifyStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * 策略工厂
 * @author: yxkong
 * @date: 2023/1/4 2:21 PM
 * @version: 1.0
 */
@Slf4j
public class StrategyFactory {
    /**
     * 获取验证策略
     * @param maps
     * @param verifyType
     * @return
     */
    public static VerifyStrategy getOrDefault(Map<String,VerifyStrategy> maps, VerifyTypeEnum verifyType){
        CaptchaVerifyStrategyImpl defaultVerifyStrategy = ApplicationContextHolder.getBean(CaptchaVerifyStrategyImpl.class);
        String strategyName = verifyType.getType()+"VerifyStrategy";
        VerifyStrategy verifyStrategy = maps.get(strategyName);
        if (Objects.isNull(verifyStrategy)){
            log.error("未找到对应{}类型的验证码验证器",strategyName);
            return defaultVerifyStrategy;
        }
        return verifyStrategy;
    }
}
