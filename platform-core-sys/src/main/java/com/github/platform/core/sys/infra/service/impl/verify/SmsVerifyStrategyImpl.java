package com.github.platform.core.sys.infra.service.impl.verify;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.service.BaseServiceImpl;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.application.dto.VerifyCodeResult;
import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import com.github.platform.core.sys.domain.model.sms.VerifyEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.domain.service.VerifyStrategy;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 短信验证策略
 * @author: yxkong
 * @date: 2023/2/7 19:57 PM
 * @version: 1.0
 */
@Slf4j
@Service("smsVerifyStrategy")
public class SmsVerifyStrategyImpl extends BaseServiceImpl implements VerifyStrategy {
    //sys:login:sms:send:minute
    private static final String MINUTE_SEND_KEY = "s:l:s:s:m:%s";
    //sys:login:sms:send:hour
    private static final String HOUR_SEND_KEY = "s:l:s:s:h:%s";
    //sys:login:sms:code
    private static final String CODE_KEY = "s:l:s:c:%s";

    @Autowired
    private AuthProperties authProperties;

    @Resource
    private ICacheService cacheService;

    @Override
    public VerifyCodeResult getCode(UserEntity user) {
//        String mobile = user.getMobile();
//        /**
//         * 修改成zset 有效期1h，每次操作续期
//         * 两个zset，
//         * 一个手机号码 为key
//         * 一个ip地址
//         */
//        String sendKeyByMinute = String.format(MINUTE_SEND_KEY, mobile);
//        String mark = (String)cacheService.get(sendKeyByMinute);
//        if (StringUtils.isNotBlank(mark)) {
//            log.info("验证码获取1分钟限制:{}", mobile);
//            return VerifyCodeResult.builder().verifyType(VerifyTypeEnum.sms).verifySeq().build(); ResultBeanUtil.result(SysResultEnum.verify_code_minute);
//        }
//        String sendKeyByHour = String.format(HOUR_SEND_KEY, mobile);
//        String sendCount = CacheUtils.get(sendKeyByHour);
//        if (Objects.nonNull(sendCount) && Long.valueOf(sendCount) >= authRedisProperties.getSms().getMinutesMaxCount()) {
//            log.info("验证码获取单位时间频次限制:{},{},{}", mobile, authRedisProperties.getSms().getMinutesMaxCount());
//            return ResultBeanUtil.result(SysResultEnum.verify_code_hour);
//        }
//        //TODO 检查发送次数 30分钟10次
//        String seq = StringUtils.randomNumber(32);
//        String code = StringUtils.randomNumber(6);
//
//        //TODO 发送短信
//
//        //验证码记录
//        CacheUtils.set(String.format(CODE_KEY, seq), code, TimeoutUtils.toSeconds(5, TimeUnit.MINUTES));
//        cacheGateway.insertCacheLog(String.format(CODE_KEY, seq),TimeoutUtils.toSeconds(5, TimeUnit.MINUTES));
//
//        //分钟发送标记
//        CacheUtils.set(sendKeyByMinute, code, TimeoutUtils.toSeconds(1, TimeUnit.MINUTES));
//        cacheGateway.insertCacheLog(sendKeyByMinute,TimeoutUtils.toSeconds(1, TimeUnit.MINUTES));
//
//        //小时发送标记
//        if (Objects.isNull(sendCount)) {
//            CacheUtils.set(sendKeyByHour, "1", TimeoutUtils.toSeconds(authRedisProperties.getSms().getMinutes(), TimeUnit.MINUTES));
//            cacheGateway.insertCacheLog(sendKeyByHour,TimeoutUtils.toSeconds(authRedisProperties.getSms().getMinutes(), TimeUnit.MINUTES));
//        } else {
//            CacheUtils.incr(sendKeyByHour);
//        }

        return VerifyCodeResult.builder().verifyType(VerifyTypeEnum.SMS).verifySeq("").build();
    }

    @Override
    public void verify(VerifyEntity verifyEntity)throws InfrastructureException {
        String cacheKey = String.format(CODE_KEY, verifyEntity.getVerifySeq());
        String code = (String) cacheService.get(cacheKey);
        if (log.isDebugEnabled()){
            log.debug("验证码记录:{},{}", cacheKey, code);
        }

        if (StringUtils.isBlank(code)) {
            exception(SysInfraResultEnum.VERIFY_CODE_EXPIRE);
        }
        if (code.equals(verifyEntity.getVerifyCode())) {
            return;
        }
        exception(SysInfraResultEnum.VERIFY_ERROR);
    }

    /**
     * 验证收尾
     *
     * @param verifyEntity
     */
    @Override
    public void finish(VerifyEntity verifyEntity) {
        //暂不支持有效期内重复验证
        cacheService.del(String.format(CODE_KEY, verifyEntity.getVerifySeq()));
    }

}
