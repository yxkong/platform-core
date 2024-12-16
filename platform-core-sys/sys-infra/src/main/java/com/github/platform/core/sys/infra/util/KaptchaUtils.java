package com.github.platform.core.sys.infra.util;

import com.github.platform.core.sys.infra.configuration.kaptcha.BaseKaptchaBaseProperties;
import com.google.code.kaptcha.Constants;

import java.util.Properties;

/**
 * 验证码生成工具
 * @author: yxkong
 * @date: 2023/4/6 11:07 AM
 * @version: 1.0
 */
public class KaptchaUtils {
    public static Properties kaptchaPropertiesToProperties(BaseKaptchaBaseProperties props) {
        Properties properties = new Properties();

        Boolean border = props.getBorder().getEnabled();
        /** 是否有边框 默认为true, 设置为yes，no*/
        properties.setProperty(Constants.KAPTCHA_BORDER, (border == null ? "" : (Boolean.FALSE.equals(border) ? "no" : "yes")));
        /** 边框颜色 默认为Color.BLACK*/
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, nullToString(props.getBorder().getColor()));
        /**	边框厚度。合法值：>0 ,默认1*/
        properties.setProperty(Constants.KAPTCHA_BORDER_THICKNESS, nullToString(props.getBorder().getThickness()));


        properties.setProperty(Constants.KAPTCHA_NOISE_COLOR, nullToString(props.getNoise().getColor()));
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, nullToString(props.getNoise().getImpl()));
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, nullToString(props.getObscurificator().getImpl()));

        /**图片实现类*/

        properties.setProperty(Constants.KAPTCHA_PRODUCER_IMPL, nullToString(props.getProducer().getImpl()));

        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL, nullToString(props.getTextProducer().getImpl()));
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, nullToString(props.getTextProducer().getCharacter().getString()));
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, nullToString(props.getTextProducer().getCharacter().getLength()));
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, nullToString(props.getTextProducer().getCharacter().getSpace()));
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, String.join(",", props.getTextProducer().getFont().getNames()));
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, nullToString(props.getTextProducer().getFont().getColor()));
        // 验证码文本字符大小 默认为40
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, nullToString(props.getTextProducer().getFont().getSize()));



        properties.setProperty(Constants.KAPTCHA_WORDRENDERER_IMPL, nullToString(props.getWord().getImpl()));


        properties.setProperty(Constants.KAPTCHA_BACKGROUND_IMPL, nullToString(props.getBackground().getImpl()));
        properties.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_FROM, nullToString(props.getBackground().getColorFrom()));
        properties.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_TO, nullToString(props.getBackground().getColorTo()));


        // 验证码图片宽度 默认为200
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, nullToString(props.getImage().getWidth()));
        // 验证码图片高度 默认为50
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, nullToString(props.getImage().getHeight()));

        return properties;
    }


    private static String nullToString(Object value) {
        if (value != null) {
            return String.valueOf(value);
        }
        return "";
    }

    private static String emptyToDefault(String val, String defaultVal) {
        if (val == null || "".equals(val)) {
            return defaultVal;
        }
        return val;
    }
}
