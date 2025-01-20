package com.github.platform.core.common.utils;

import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * velocity 工具类
 *
 * @author: yxkong
 * @date: 2023/8/2 9:38 AM
 * @version: 1.0
 */
@Slf4j
public class VelocityUtil {
    private static final VelocityEngine ENGINE = new VelocityEngine();
    private static final Map<String, Long> TEMPLATE_CACHE = new HashMap<>();
    private static final LocalDateTimeUtil DATE_TIME_UTIL = new LocalDateTimeUtil();
    private static final BigDecimalUtil BIG_DECIMAL_UTIL = new BigDecimalUtil();

    public static final String REGEXP_CUSTOMER_NAME = "^([a-zA-Z0-9]|[\u4E00-\u9FA5]){1}([a-zA-Z0-9]|[\u4E00-\u9FA5]|[?·.\\s]){0,99}$";

    static {
        initEngine();
    }

    /**
     * 初始化 Velocity 引擎
     */
    private static void initEngine() {
        // 使用推荐的配置键
        ENGINE.setProperty("resource.loaders", "string");
        ENGINE.setProperty("resource.loader.string.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        // 开启缓存以优化性能
        ENGINE.setProperty("resource.loader.string.cache", "true");
        // 每 60 秒检查修改
        ENGINE.setProperty("resource.loader.string.modification_check_interval", "60");

        // 初始化 Velocity 引擎
        ENGINE.init();

        // 自定义宏
        String macroLibrary = "#macro(dictMapping $dict $value)#if($dict.containsKey($value))$dict.get($value)#else$value#end#end";
        ENGINE.evaluate(null, new StringWriter(), "macroLibrary", macroLibrary);
    }

    /**
     * 缓存模板
     *
     * @param templateName    模板名称
     * @param templateContent 模板内容
     */
    public static void cacheStringTemplate(String templateName, String templateContent) {
        String sanitizedContent = sanitizeTemplateContent(templateContent);
        StringResourceRepository repository = StringResourceLoader.getRepository();
        repository.putStringResource(templateName, sanitizedContent);
        TEMPLATE_CACHE.put(templateName, System.currentTimeMillis());
    }

    /**
     * 清理模板内容
     *
     * @param templateContent 模板内容
     * @return 清理后的内容
     */
    private static String sanitizeTemplateContent(String templateContent) {
        if (StringUtils.isNull(templateContent)) {
            return "";
        }
        return templateContent.replace("\t", "")
                .replace("\r\n", "")
                .replace("\n", "");
    }

    /**
     * 判断模板是否存在
     *
     * @param templateName 模板名称
     * @return 存在返回 true，不存在返回 false
     */
    public static boolean isExist(String templateName) {
        try {
            Template template = ENGINE.getTemplate(templateName);
            return Objects.nonNull(template);
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 根据缓存的字符串模板引擎渲染
     *
     * @param context         上下文
     * @param templateName    缓存的模板名称
     * @param templateContent 模板内容
     * @return 渲染结果
     */
    public static String stringTemplateMerge(VelocityContext context, String templateName, String templateContent) {
        Long cacheTime = TEMPLATE_CACHE.getOrDefault(templateName, 0L);
        if (System.currentTimeMillis() - cacheTime > TimeUnit.MINUTES.toMillis(1) && StringUtils.isNotNull(templateContent)) {
            cacheStringTemplate(templateName, templateContent);
        }
        enrichContext(context);
        return stringTemplate(context, templateName);
    }

    /**
     * 工具类渲染
     *
     * @param context      上下文
     * @param templateName 模板名称
     * @return 渲染结果
     */
    public static String stringTemplate(Context context, String templateName) {
        Template template = ENGINE.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    /**
     * 使用自定义模板渲染
     *
     * @param context        上下文
     * @param templateName   模板名称
     * @param templateString 模板内容
     * @return 渲染结果
     */
    public static String evaluate(VelocityContext context, String templateName, String templateString) {
        enrichContext(context);
        StringWriter writer = new StringWriter();
        ENGINE.evaluate(context, writer, templateName, templateString);
        return writer.toString();
    }

    /**
     * 使用自定义模板渲染
     *
     * @param templateName   模板名称
     * @param templateString 模板内容
     * @return 渲染结果
     */
    public static String evaluate(String templateName, String templateString) {
        VelocityContext context = new VelocityContext();
        return evaluate(context, templateName, templateString);
    }

    /**
     * 增强上下文
     *
     * @param context 上下文
     */
    private static void enrichContext(VelocityContext context) {
        context.put("dictUtil", DictUtil.getInstance());
        context.put("dateTimeUtil", DATE_TIME_UTIL);
        context.put("bigDecimalUtil", BIG_DECIMAL_UTIL);
    }


}
