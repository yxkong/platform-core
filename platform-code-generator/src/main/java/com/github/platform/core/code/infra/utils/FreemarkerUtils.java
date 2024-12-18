package com.github.platform.core.code.infra.utils;

import com.github.platform.core.common.utils.StringUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * freemarker 模板工具类
 * @author yxkong
 */
public class FreemarkerUtils {
	// 模版配置对象
	private Configuration cfg;

	/**
	 * 每次实例化都会重置
	 * @param basePackagePath
	 */
	public FreemarkerUtils(String basePackagePath)  {
		// 创建一个Configuration实例
		cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

		// 设置FreeMarker的模版文件夹位置
		if (StringUtils.isEmpty(basePackagePath)) {
			basePackagePath = "/";
		}
		cfg.setClassForTemplateLoading(this.getClass(), basePackagePath);

		cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
	}

	/**
	 * 根据模板生成到指定文件中
	 * @param map 数据模型
	 * @param file 目标文件
	 * @param templateName 模板名称
	 * @throws Exception
	 */
	public void process(Map<String, Object> map, File file, String templateName) throws Exception {
		// 创建模版对象
		Template t = cfg.getTemplate(templateName);
		// 在模版上执行插值操作，并输出到指定的输出流中
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
			t.process(map, writer);
		}
	}

	/**
	 * 根据模板生成内容
	 * @param map 数据模型
	 * @param templateName 模板名称
	 * @return 生成的字符串
	 * @throws Exception
	 */
	public String process(Map<String, Object> map, String templateName) throws Exception {
		// 创建模版对象
		Template t = cfg.getTemplate(templateName);
		// 在模版上执行插值操作，并输出到字符串中
		try (StringWriter writer = new StringWriter()) {
			t.process(map, writer);
			return writer.toString();
		}
	}

	/**
	 * 根据字符串模板生成内容
	 * @param map 数据模型
	 * @param templateContent 模板内容字符串
	 * @param templateName 模板名称
	 * @return 生成的字符串
	 * @throws Exception
	 */
	public String processStringTemplate(Map<String, Object> map, String templateContent, String templateName) throws Exception {
		// 创建StringTemplateLoader，用于加载字符串模板
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate(templateName, templateContent);
		cfg.setTemplateLoader(stringLoader);

		// 创建模版对象
		Template t = cfg.getTemplate(templateName);
		// 在模版上执行插值操作，并输出到字符串中
		try (StringWriter writer = new StringWriter()) {
			t.process(map, writer);
			return writer.toString();
		}
	}
}