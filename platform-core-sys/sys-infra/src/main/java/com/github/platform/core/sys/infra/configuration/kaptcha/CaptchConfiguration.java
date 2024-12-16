package com.github.platform.core.sys.infra.configuration.kaptcha;

import com.github.platform.core.sys.infra.service.ICaptchaService;
import com.github.platform.core.sys.infra.util.KaptchaUtils;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码生成
 * @author: yxkong
 * @date: 2022/4/26 7:57 PM
 * @version: 1.0
 */
@Configuration
@ConditionalOnClass({ KaptchaServlet.class })
//@EnableConfigurationProperties(KaptchaProperties.class)
public class CaptchConfiguration {

    @Bean
    @ConditionalOnMissingBean(Producer.class)
    public Producer defaultKaptcha(KaptchaProperties kaptchaProperties) {
        Properties properties = KaptchaUtils.kaptchaPropertiesToProperties(kaptchaProperties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean
    public KaptchaStartListener kaptchaStartListener(ICaptchaService captchaService){
        return new KaptchaStartListener(captchaService);
    }
}
