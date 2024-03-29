package com.github.platform.core.loadbalancer.holder;

/**
 * 灰度本地变量操作
 * @author: yxkong
 * @date: 2023/2/23 6:54 PM
 * @version: 1.0
 */
public class RequestHeaderHolder {
    public static final String LABEL_KEY = "label";
    public static final String LABEL_VAL = "gray";
    public static final String VERSION_KEY = "version";
    public static final String WEIGHT_KEY = "weight";
    private static final ThreadLocal<GrayLabel> GRAY;

    static {
        GRAY = new InheritableThreadLocal();
    }

    public static String getLabel() {
        GrayLabel grayLabel = GRAY.get();
        if (grayLabel != null){
            return grayLabel.getLabel();
        }
        return "";
    }
    public static String getVersion() {
        GrayLabel grayLabel = GRAY.get();
        if (grayLabel != null){
            return grayLabel.getVersion();
        }
        return "";
    }
    public static String getWeight() {
        GrayLabel grayLabel = GRAY.get();
        if (grayLabel != null){
            return grayLabel.getWeight();
        }
        return "";
    }


    public static void set(GrayLabel value) {
        GRAY.set(value);
    }

    public static void remove() {
        GRAY.remove();
    }
}
