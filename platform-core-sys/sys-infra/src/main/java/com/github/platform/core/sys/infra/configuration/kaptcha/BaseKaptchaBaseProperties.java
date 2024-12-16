package com.github.platform.core.sys.infra.configuration.kaptcha;

import lombok.Data;

/**
 *
 * @author: yxkong
 * @date: 2023/4/6 10:42 AM
 * @version: 1.0
 */
@Data
public abstract class BaseKaptchaBaseProperties {
    /**边框设置*/
    private Border border = new Border() ;
    /**干扰实现*/
    private Noise noise = new Noise();
    /**图片样式*/
    private Obscurificator obscurificator = new Obscurificator();
    /**图片实现*/
    private Producer producer = new Producer();
    /**文本实现*/
    private TextProducer textProducer = new TextProducer() ;
    /**背景实现*/
    private Background background = new Background();
    /**文字渲染器*/
    private Word word = new Word();
    private Image image = new Image();

    /**边框设置*/
    @Data
    public static class Border {
        /** 是否有边框 默认为true 转换为yes，no*/
        private Boolean enabled = true;
        /**边框颜色 默认为Color.BLACK*/
        private String color;
        /**	边框厚度。合法值：>0 ,默认1*/
        private Integer thickness;

    }
    @Data
    public static class Noise {
        /**干扰颜色。合法值： r,g,b 或者 white,black,blue.*/
        private String color;
        /**干扰实现类,默认 com.google.code.kaptcha.impl.DefaultNoise*/
        private String impl;
    }
    @Data
    public static class Obscurificator {
        /**
         * 图片样式：
         * 水纹com.google.code.kaptcha.impl.WaterRipple
         * 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
         * 阴影com.google.code.kaptcha.impl.ShadowGimpy
         * 默认 WaterRipple
         */
        private String impl;
    }
    @Data
    public static class Producer {
        /**图片实现类*/
        private String impl;
    }

    @Data
    public static class TextProducer {
        /**文本实现类*/
        private String impl;
        /**验证码配置*/
        private Char character = new Char();
        private Font font = new Font();
        @Data
        public static class Char {
            /**文本集合，验证码值从此集合中获取*/
            private String string;
            /**验证码长度，默认5*/
            private Integer length;
            /**文字间隔,默认2*/
            private Integer space;
        }
        @Data
        public static class Font {
            /**字体*/
            private String[] names = new String[]{};
            /**字体颜色，合法值： r,g,b  或者 white,black,blue.，默认black*/
            private String color;
            /**验证码文本字符大小 默认为40*/
            private Integer size;
        }
    }
    @Data
    public static class Word {
        /**默认com.google.code.kaptcha.text.impl.DefaultWordRenderer*/
        private String impl;
    }
    @Data
    public static class Background {
        /**背景实现类,默认com.google.code.kaptcha.impl.DefaultBackground*/
        private String impl;
        /**背景颜色渐变，开始颜色*/
        private String colorFrom;
        /**背景颜色渐变。结束颜色*/
        private String colorTo;
    }
    @Data
    public static class Image {
        // 验证码图片宽度 默认为200
        private Integer width;
        // 验证码图片高度 默认为50
        private Integer height;
    }
}
