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
    public static VelocityEngine engine;

    private static Map<String, Long> TEMPLATE_CACHE = new HashMap<>();

    private static LocalDateTimeUtil dateTimeUtil = new LocalDateTimeUtil();

    private static BigDecimalUtil bigDecimalUtil = new BigDecimalUtil();

    public static final String REGEXP_CUSTOMER_NAME ="^([a-zA-Z0-9]|[\u4E00-\u9FA5]){1}([a-zA-Z0-9]|[\u4E00-\u9FA5]|[?·.\\s]){0,99}$";
    static {
        engine = new VelocityEngine();
        // 设置字符串资源加载器
        engine.addProperty(VelocityEngine.RESOURCE_LOADER, "string");
        engine.addProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");

        // 初始化 Velocity 引擎
        engine.init();
        //自定义宏
        String macroLibrary = "#macro(dictMapping $dict $value)#if($dict.containsKey($value))$dict.get($value)#else$value#end#end";
        // 将宏库字符串添加到 VelocityEngine 中
        engine.evaluate(null, new StringWriter(), "macroLibrary", macroLibrary);
    }

    /**
     * 缓存模板
     *
     * @param templateName
     * @param templateContent
     */
    public static void cacheStringTemplate(String templateName, String templateContent) {
        templateContent = StringUtils.isNull(templateContent) ? "" : templateContent;
        templateContent = templateContent.replace("\t", "");
        templateContent = templateContent.replace("\r\n", "");
        templateContent = templateContent.replace("\n", "");
        //  StringResourceRepository 实例
        StringResourceRepository repository = StringResourceLoader.getRepository();
        // 将字符串模板添加到 StringResourceRepository 中
        repository.putStringResource(templateName, templateContent);
        TEMPLATE_CACHE.put(templateName, System.currentTimeMillis());
    }

    /**
     * 判断模板是否存在
     *
     * @param templateName 模板名称
     * @return 存在返回true，不存在返回false
     */
    public static boolean isExist(String templateName) {
        Template template = engine.getTemplate(templateName);
        if (Objects.nonNull(template)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 根据缓存的字符串模板引擎渲染
     *
     * @param context      上下文
     * @param templateName 缓存的模板名称
     * @return
     */
    public static String stringTemplateMerge(VelocityContext context, String templateName) {
        return stringTemplateMerge(context, templateName, "");
    }

    /**
     * 根据缓存的字符串模板引擎渲染
     *
     * @param context      上下文
     * @param templateName 缓存的模板名称
     * @return
     */
    public static String stringTemplateMerge(VelocityContext context, String templateName, String templateContent) {
        Long cacheTime = TEMPLATE_CACHE.getOrDefault(templateName, 0L);
        if (System.currentTimeMillis() - cacheTime > TimeUnit.MINUTES.toMillis(1) && StringUtils.isNotNull(templateContent)) {
            cacheStringTemplate(templateName, templateContent);
        }
        context.put("dictUtil", DictUtil.getInstance());
        context.put("dateTimeUtil", dateTimeUtil);
        context.put("bigDecimalUtil", bigDecimalUtil);
        return stringTemplate(context,templateName);
    }



    /**
     * 走工具类
     *
     * @param context
     * @param templateName
     * @return
     */
    public static String stringTemplate(Context context, String templateName) {
        Template template = engine.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }


    /**
     * 使用自定义模板渲染,如果已经缓存里了
     *
     * @param context        上下文
     * @param templateName   模板名称
     * @param templateString 模板内容
     * @return
     */
    public static String evaluate(VelocityContext context, String templateName, String templateString) {
        context.put("dictUtil", DictUtil.getInstance());
        context.put("dateTimeUtil", dateTimeUtil);
        context.put("bigDecimalUtil", bigDecimalUtil);
        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, templateName, templateString);
        return writer.toString();
    }

    public static String evaluate(String templateName, String templateString) {
        VelocityContext context = new VelocityContext();
        return evaluate(context, templateName, templateString);
    }

}
