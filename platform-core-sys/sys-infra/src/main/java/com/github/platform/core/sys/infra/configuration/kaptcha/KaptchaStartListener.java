package com.github.platform.core.sys.infra.configuration.kaptcha;

import com.github.platform.core.sys.domain.constant.CaptchaTypeEnum;
import com.github.platform.core.sys.domain.constant.Constants;
import com.github.platform.core.sys.infra.service.ICaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * 加速验证码加载
 * @author: yxkong
 * @date: 2023/4/7 10:49 下午
 * @version: 1.0
 */
@Slf4j
public class KaptchaStartListener implements ApplicationListener<ApplicationReadyEvent> {
    private ICaptchaService captchaService;

    public KaptchaStartListener(ICaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        captchaService.createImage(Constants.INIT, CaptchaTypeEnum.DEFAULT);
        captchaService.createImage(Constants.INIT, CaptchaTypeEnum.MATH);
    }
}
