package com.github.platform.core.code.infra.utils;

import com.github.platform.core.common.utils.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

public class FreemarkerUtils {
	// 模版配置对象
	private Configuration cfg;
	private String basePackagePath;
	public FreemarkerUtils(String basePackagePath) throws Exception {
		this.basePackagePath = basePackagePath;
		this.init();
	}
	private void init() throws Exception {
		// 初始化FreeMarker配置
		// 创建一个Configuration实例
		cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

		// 设置FreeMarker的模版文件夹位置
		if (StringUtils.isEmpty(basePackagePath)){
			basePackagePath = "/";
		}
		cfg.setClassForTemplateLoading(this.getClass(),basePackagePath);

		cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
	}


	/**
	 * 根据模板生成到指定文件中
	 * @param map
	 * @param file
	 * @param templateName
	 * @throws Exception
	 */
	public void process(Map<String, Object> map, File file, String templateName) throws Exception {
		// 创建模版对象
		Template t = cfg.getTemplate(templateName);
		// 在模版上执行插值操作，并输出到制定的输出流中
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		t.process(map, writer);
	}
	/**
     * 根据模板生成内容
     * @param map
     * @param templateName
	 */
	public String process(Map<String, Object> map, String templateName) throws Exception {
		// 创建模版对象
		Template t = cfg.getTemplate(templateName);
		// 在模版上执行插值操作，并输出到制定的输出流中
		StringWriter writer = new StringWriter();
		t.process(map, writer);
		return writer.toString();
	}

}
