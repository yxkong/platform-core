package com.github.platform.core.sys.infra.configuration.kaptcha;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * kaptcha 验证码配置
 * @author: yxkong
 * @date: 2023/4/6 10:07 AM
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.DATA_KAPTCHA)
public class KaptchaProperties extends BaseKaptchaBaseProperties {
    public static final long DEFAULT_CAPTCHA_TIMEOUT = 5 * 60;
    private String type;
    private Cache cache;
    public String getKey(String verifySeq){
        return  getKey(null,verifySeq);
    }
    public String getKey(String bizNo,String verifySeq){
        StringBuffer sb = new StringBuffer(this.cache.getPrefix());
        if (StringUtils.isNotEmpty(bizNo)){
            sb.append(bizNo).append(SymbolConstant.colon);
        }
        return  sb.append(verifySeq).toString();
    }
    /**
     * 数学验证
     * @return
     */
    public boolean isMath(){
        return KaptchaType.math.getType().equals(this.type);
    }

    /**
     * 图形验证
     * @return
     */
    public boolean isText(){
        return KaptchaType.text.getType().equals(this.type);
    }
    @Data
    public static class Cache {
        /**验证码缓存前缀*/
        private String prefix = CacheConstant.captcha;
        /**验证码缓存时间，单位秒，默认3min*/
        private Long timeout = DEFAULT_CAPTCHA_TIMEOUT;
    }


    @Getter
    public enum KaptchaType{
        math("math","数学验证"),
        text("text","文本验证")
        ;

        KaptchaType(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        private String type;
        private String desc;



    }


}
