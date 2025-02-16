package com.github.platform.core.code.infra.utils;

import com.github.platform.core.code.domain.constant.CodeTypeEnum;
import com.github.platform.core.code.domain.dto.ColumnDto;
import com.github.platform.core.code.domain.dto.GenConfigDto;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.util.Lists;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成工具类
 */
@Slf4j
public class GenUtil {

    private static final String LOCAL_DATE = "LocalDate";
    private static final String LOCAL_DATE_TIME = "LocalDateTime";

    private static final String BIGDECIMAL = "BigDecimal";

    public static final String PK = "PRI";

    public static final String EXTRA = "auto_increment";
    /**后台管理代码生成排除字段*/
    private static List<String> sysExcludeColumn = Lists.newArrayList("id","status","tenantId","createBy","updateBy","createTime","updateTime","remark");
    /**api项目代码生成排除字段*/
    private static List<String> apiExcludeColumn = Lists.newArrayList("id","tenantId","createTime","updateTime","remark");
    /**
     * 获取代码模板名称
     *
     * @return List
     */
    private static List<String> getTemplateNames(Integer codeType) {
        List<String> templateNames = new ArrayList<>();
        if (CodeTypeEnum.isSys(codeType)){
            templateNames.add("api.js");
            templateNames.add("vue.vue");
            templateNames.add("Cmd.java");
            templateNames.add("Controller.java");
            templateNames.add("AdapterConvert.java");
            templateNames.add("Query.java");
            templateNames.add("QueryBase.java");
            templateNames.add("QueryContext.java");
        }
        templateNames.add("Executor.java");
        templateNames.add("ExecutorImpl.java");
        templateNames.add("Context.java");
        templateNames.add("Dto.java");
        templateNames.add("Base.java");
        templateNames.add("Gateway.java");
        templateNames.add("GatewayImpl.java");
        templateNames.add("InfraConvert.java");
        templateNames.add("Mapper.java");
        templateNames.add("Mapper.xml");
        //去除service层，业务根据自己的需求封装
        return templateNames;
    }


    public static List<Map<String, Object>> preview(List<ColumnDto> columns, GenConfigDto genConfig, Integer codeType) throws Exception {
        Map<String, Object> genMap = getGenMap(columns, genConfig,codeType);
        List<Map<String, Object>> genList = new ArrayList<>();
        // 获取后端模版
        List<String> templates = getTemplateNames(codeType);
        FreemarkerUtils f = new FreemarkerUtils("/template/generator/" + CodeTypeEnum.getName(codeType));
        for (String templateName : templates) {
            Map<String, Object> map = new HashMap<>(4);
            map.put("content", f.process(genMap,templateName+".ftl"));
            map.put("name", templateName);
            genList.add(map);

        }
        return genList;
    }

    public static void download(List<ColumnDto> columns, GenConfigDto genConfig,Integer codeType, ZipOutputStream zip) throws Exception {
        Map<String, Object> genMap = getGenMap(columns, genConfig,codeType);
        FreemarkerUtils f = new FreemarkerUtils("/template/generator/" + CodeTypeEnum.getName(codeType));
        // 获取后端模版
        List<String> templates = getTemplateNames(codeType);
        for (String templateName : templates) {
            try {
                String context = f.process(genMap, templateName + ".ftl");
                String fileName = getFileName(templateName, genConfig, genMap.get("entityName").toString(), null,genMap.get("lowerEntityName").toString());
                zip.putNextEntry(new ZipEntry(fileName));
                write(zip,context);
                zip.flush();
                zip.closeEntry();
            } catch (Exception e) {
                log.error("渲染模板失败，表名:{}",genConfig.getTableName(),e);
            }
        }
    }
    private static void write(OutputStream out,String context) throws IOException {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            osw.write(context);
            osw.flush();
        } catch (IOException e) {
            throw e;
        }
    }

    public static void generatorCode(List<ColumnDto> columnInfos, GenConfigDto genConfig,Integer codeType) throws Exception {
        String rootPath =  System.getProperty("user.dir") + File.separator;

        Map<String, Object> genMap = getGenMap(columnInfos, genConfig,codeType);
        // 生成后端代码
        List<String> templates = getTemplateNames(codeType);
        FreemarkerUtils f = new FreemarkerUtils("/template/generator/" + CodeTypeEnum.getName(codeType));
        for (String templateName : templates) {
            String filePath = getFileName(templateName, genConfig, genMap.get("entityName").toString(), rootPath ,genMap.get("lowerEntityName").toString());
            log.info("filePath:"+filePath);
            assert filePath != null;
            File file = new File(filePath);
            // 如果非覆盖生成，且文件已存在，则跳过
            if (file.exists() &&(genConfig.getCover()!= null &&  genConfig.getCover() != 1)) {
                continue;
            }

            createDirectoryAndFile(file);
            // 生成代码文件
            f.process(genMap,file,templateName+".ftl");
        }

    }

    private static void createDirectoryAndFile(File file) throws IOException {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        file.createNewFile();
    }

    private static String getUrl(String prefix){
        if (prefix.startsWith(SymbolConstant.divide)){
            return prefix.substring(1);
        }
        return prefix;
    }

    private static Pair<String,String> getEntityName(String tableName,GenConfigDto genConfig){
        String entityName = genConfig.getEntityName();
        String lowerEntityName = null;
        if (StringUtils.isEmpty(genConfig.getEntityName())){
            // 大写开头的类名
            entityName = StringUtils.upperFirstChar(tableName);
            // 小写开头的类名
            lowerEntityName = StringUtils.toCamelCase(tableName);

            // 判断是否去除表前缀
            if (StringUtils.isNotEmpty(genConfig.getPrefix())) {
                entityName = StringUtils.toCapitalizeCamelCase(StringUtils.removePrefix(tableName, genConfig.getPrefix()));
                lowerEntityName = StringUtils.toCamelCase(StringUtils.removePrefix(tableName, genConfig.getPrefix()));
                lowerEntityName = StringUtils.uncapitalize(lowerEntityName);
            }
        } else {
            lowerEntityName = StringUtils.uncapitalize(entityName);
        }
        return Pair.of(entityName,lowerEntityName);
    }

    /**
     * 获取模版数据
     * @param columnInfos
     * @param genConfig
     * @return
     */
    private static Map<String, Object> getGenMap(List<ColumnDto> columnInfos, GenConfigDto genConfig,Integer codeType) {
        if (log.isDebugEnabled()){
            log.debug("columnInfos:{}", JsonUtils.toJson(columnInfos));
        }
        String tableName = genConfig.getTableName();
        Map<String, Object> genMap = setTemplateData(genConfig, tableName);
        // 保存字段信息
        List<Map<String, Object>> columns = new ArrayList<>();
        // 保存查询字段的信息
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        // 存储字典信息
        List<String> dicts = new ArrayList<>();
        // 存储不为空的字段信息
        List<Map<String, Object>> isNotNullColumns = new ArrayList<>();
        // 列表字段信息
        List<Map<String, Object>> listColumns = new ArrayList<>();
        //表单字段信息
        List<Map<String, Object>> formColumns = new ArrayList<>();
        //表单字段信息
        List<Map<String, Object>> entityColumns = new ArrayList<>();

        String pkColumnName = null;
        String pkLowerColumnName = null;
        Map<String, Object> pkMap = new HashMap<>(16);

        for (ColumnDto column : columnInfos) {
            Map<String, Object> listMap = new HashMap<>(16);
            // 字段描述
            listMap.put("remark", column.getRemark());
            // 字段类型
            listMap.put("columnKey", column.getColumnKey());
            // 字段类型
            String colType = TypeUtil.mysqlTypeToJavaType(column.getColumnType());
            // 小写开头的字段名
            String lowerColumnName = StringUtils.toCamelCase(column.getColumnName());
            // 大写开头的字段名
            String upperColumnName = StringUtils.toCapitalizeCamelCase(column.getColumnName());
            if (PK.equals(column.getColumnKey())) {
                // 存储主键类型
                genMap.put("pkColumnType", colType);
                // 存储小写开头的字段名
                pkLowerColumnName = lowerColumnName;
                genMap.put("pkLowerColumnName", pkLowerColumnName);
                // 存储大写开头的字段名
                genMap.put("pkUpperColumnName", upperColumnName);
                // 主键名称
                pkColumnName = column.getColumnName();
                genMap.put("pkColumnName",pkColumnName);
                pkMap = listMap;
            }
            if (LOCAL_DATE.equals(colType)) {
                genMap.put("hasLocalDate", true);
            }
            // 是否存在 Timestamp 类型的字段
            if (LOCAL_DATE_TIME.equals(colType)) {
                genMap.put("hasLocalDateTime", true);
            }
            // 是否存在 BigDecimal 类型的字段
            if (BIGDECIMAL.equals(colType)) {
                genMap.put("hasBigDecimal", true);
            }
            // 主键是否自增
            if (EXTRA.equals(column.getExtra())) {
                genMap.put("auto", true);
            }
            // 列存在字典
            if (StringUtils.isNotBlank(column.getDictName())) {
                genMap.put("hasDict", true);
                if(!dicts.contains(column.getDictName())){
                    dicts.add(column.getDictName());
                }
            }
            // 存储字段类型
            listMap.put("columnType", colType);
            // 存储字原始段名称
            listMap.put("columnName", column.getColumnName());
            // 不为空
            listMap.put("istNotNull", column.getNotNull());

            // 表单显示
            listMap.put("formShow", column.getFormShow());
            // 表单组件类型
            String formType = StringUtils.isNotBlank(column.getFormType()) ? column.getFormType() : "input";
            listMap.put("formType", formType);
            listMap.put("placeholder", "input".equals(formType)?"请输入":"请选择");
            // 小写开头的字段名称
            listMap.put("lowerColumnName", lowerColumnName);
            //大写开头的字段名称
            listMap.put("upperColumnName", upperColumnName);
            // 字典名称
            listMap.put("dictName", column.getDictName());

            isNullDeal(isNotNullColumns, column, listMap, colType, lowerColumnName);
            // 根据查询类型生成xml
            listMap.put("queryType", column.getQueryType());
            // 字段列表显示
            listMap.put("listShow", column.getListShow());

            columnDeal(genMap, queryColumns, listColumns, formColumns,entityColumns, column, listMap,lowerColumnName, colType,codeType);
            // 添加到字段列表中
            columns.add(listMap);
        }
        Map<String, Object> strId = getStrId(pkLowerColumnName);
        if (Objects.nonNull(strId)){
            formColumns.add(strId);
        }

        //查询字段为空，把主键填充进去
        if (CollectionUtil.isEmpty(queryColumns)){
            queryColumns.add(pkMap);
        }
        // 保存字段列表
        genMap.put("columns", columns);
        genMap.put("listColumns",listColumns);
        // 保存查询列表
        genMap.put("queryColumns", queryColumns);
        genMap.put("formColumns", formColumns);
        genMap.put("entityColumns", entityColumns);
        // 保存字段列表
        genMap.put("dicts", dicts);
        // 保存非空字段信息
        genMap.put("isNotNullColumns", isNotNullColumns);
        genMap.put("adapterPackage",getPackage(genConfig.getPackageName(), genConfig.getModuleName(), "adapter"));
        genMap.put("domainPackage", getPackage(genConfig.getPackageName(), genConfig.getModuleName(),"domain"));
        genMap.put("infraPackage", getPackage(genConfig.getPackageName(), genConfig.getModuleName(),"infra"));
        genMap.put("applicationPackage", getPackage(genConfig.getPackageName(), genConfig.getModuleName(),"application"));
        genMap.put("mapperPackage", mapperPackage(genConfig.getPackageName(), genConfig.getModuleName()));
        genMap.put("resultMap", SqlGenUtil.createResultStr(columns));
        genMap.put("baseColumnList",SqlGenUtil.createBaseColumnListStr(columns));
        genMap.put("insertSql", SqlGenUtil.buildInsertSql(columns,tableName));
        genMap.put("updateSql",SqlGenUtil.buildUpdateSql(columns,tableName,pkColumnName,pkLowerColumnName));

        genMap.put("listSql",SqlGenUtil.buildListSql(queryColumns,tableName,codeType));
        genMap.put("listCountSql",SqlGenUtil.buildListCountSql(queryColumns,tableName,pkColumnName,codeType));
        return genMap;
    }
    private static   Map<String, Object> getStrId(String pkName){
        if (!"id".equals(pkName)){
            return null;
        }
        Map<String, Object> listMap = new HashMap<>(16);
        // 字段描述
        listMap.put("remark", "字符串主键");
        // 字段类型
        listMap.put("columnKey", "varchar");
        listMap.put("columnKey", "string");
        // 存储字原始段名称
        listMap.put("columnName", "strId");
        // 不为空
        listMap.put("istNotNull", false);

        // 表单显示
        listMap.put("formShow", false);
        // 表单组件类型
        // 小写开头的字段名称
        listMap.put("lowerColumnName", "strId");
        //大写开头的字段名称
        listMap.put("upperColumnName", "StrId");
        // 字段列表显示
        listMap.put("listShow", false);
        return listMap;
    }

    private static Map<String, Object> setTemplateData(GenConfigDto genConfig, String tableName) {
        // 存储模版字段数据
        Map<String, Object> genMap = new HashMap<>(64);
        // 接口别名
        if (StringUtils.isEmpty(genConfig.getApiAlias())){
            genMap.put("apiAlias", genConfig.getTableComment());
        } else {
            genMap.put("apiAlias", genConfig.getApiAlias());
        }
        genMap.put("tableComment", genConfig.getTableComment());
        // 包名称
        genMap.put("package", genConfig.getPackageName());
        // 模块名称
        genMap.put("moduleName", genConfig.getModuleName());
        // 作者
        genMap.put("author", genConfig.getAuthor());
        // 创建日期
        genMap.put("date", LocalDateTimeUtil.dateTimeDefault());
        // 表名
        genMap.put("tableName", tableName);

        Pair<String, String> pair = getEntityName(tableName, genConfig);
        String entityName = pair.getLeft();
        String lowerEntityName = pair.getRight();
        // 大写开头的类名

        genMap.put("entityName", entityName);
        genMap.put("lowerEntityName",  lowerEntityName);
        if (Objects.nonNull(genConfig.getUrlPrefix())){
            genMap.put("urlPrefix", getUrl(genConfig.getUrlPrefix()));
        } else {
            genMap.put("urlPrefix", "sys/"+ genConfig.getModuleName()+"/"+lowerEntityName);
        }
        genMap.put("hasLocalDate", false);
        // 存在 Timestamp 字段
        genMap.put("hasLocalDateTime", false);
        genMap.put("queryHasLocalDate", false);
        // 查询类中存在 Timestamp 字段
        genMap.put("queryHasLocalDateTime", false);
        // 存在 BigDecimal 字段
        genMap.put("hasBigDecimal", false);
        // 查询类中存在 BigDecimal 字段
        genMap.put("queryHasBigDecimal", false);
        // 是否需要创建查询
        genMap.put("hasQuery", false);
        // 自增主键
        genMap.put("auto", false);
        // 存在字典
        genMap.put("hasDict", false);
        return genMap;
    }

    private static String mapperPackage(String packageName, String moduleName) {
        StringBuffer sb = new StringBuffer(packageName);
        sb.append(".persistence.mapper");
        if (StringUtils.isNotEmpty(moduleName)){
            sb.append(SymbolConstant.period).append(moduleName);
        }
        //兼容包名
//        sb.append(".infra.persistence.mapper");
        return sb.toString();
    }

    private static String getPackage(String packageName,String moduleName,String level){
        StringBuffer sb = new StringBuffer(packageName);
        if (StringUtils.isNotEmpty(moduleName)){
            sb.append(SymbolConstant.period).append(moduleName);
        }
        sb.append(SymbolConstant.period).append(level);
        return sb.toString();
    }

    /**
     * 字段处理
     * @param genMap
     * @param queryColumns
     * @param listColumns
     * @param formColumns
     * @param column
     * @param listMap
     * @param colType
     */
    private static void columnDeal(Map<String, Object> genMap, List<Map<String, Object>> queryColumns, List<Map<String, Object>> listColumns, List<Map<String, Object>> formColumns, List<Map<String, Object>> entityColumns, ColumnDto column, Map<String, Object> listMap,String lowerColumnName, String colType,Integer codeType) {
        if (column.isQueryShow()) {
            // 是否存在查询
            genMap.put("hasQuery", true);
            if (LOCAL_DATE.equals(colType)) {
                // 查询中存储 date 类型
                genMap.put("queryHasLocalDate", true);
            }
            if (LOCAL_DATE_TIME.equals(colType)) {
                // 查询中存储 Timestamp 类型
                genMap.put("queryHasLocalDateTime", true);
            }
            if (BIGDECIMAL.equals(colType)) {
                // 查询中存储 BigDecimal 类型
                genMap.put("queryHasBigDecimal", true);
            }
            // 添加到查询列表中
            queryColumns.add(listMap);
        }

        List<String> excludedColumns = CodeTypeEnum.isSys(codeType) ? sysExcludeColumn : apiExcludeColumn;

        if (!excludedColumns.contains(lowerColumnName)) {
            if (CodeTypeEnum.isSys(codeType)) {
                entityColumns.add(listMap);
            } else if (CodeTypeEnum.isApi(codeType)) {
                entityColumns.add(listMap);
            }
        }

        if (column.isListShow()) {
            listColumns.add(listMap);
        }

        if (column.isFormShow()) {
            formColumns.add(listMap);
        }

    }

    /**
     * 非空处理
     * @param isNotNullColumns
     * @param column
     * @param listMap
     * @param colType
     * @param lowerColumnName
     */
    private static void isNullDeal(List<Map<String, Object>> isNotNullColumns, ColumnDto column, Map<String, Object> listMap, String colType, String lowerColumnName) {
        // 添加非空字段信息
        if (column.getNotNull() != null && 1== column.getNotNull() && StringUtils.isNotEmpty(column.getRemark())) {
            if ("String".equals(colType)){
                listMap.put("validated","@NotEmpty(message=\""+ column.getRemark()+"不能为空\")");
            } else {
                listMap.put("validated","@NotNull(message=\""+ column.getRemark()+"不能为空\")");
            }

            isNotNullColumns.add(listMap);
        }
    }


    /**
     * 定义后端文件路径以及名称
     */
    private static String getFileName(String templateName, GenConfigDto genConfig, String entityName, String rootPath, String lowerEntityName) {
        String javaPath = getFilePath(rootPath, "src.main.java", genConfig.getPackageName(), genConfig.getModuleName(), "");
        String mybatisPath = getFilePath(rootPath, "src.main.resources.mybatis", "", genConfig.getModuleName(), "");
        String mapperPackage = getFilePath(rootPath, "src.main.java", genConfig.getPackageName(),"persistence.mapper", genConfig.getModuleName());
        String fileName = entityName + templateName;

        switch (templateName) {
            case "Cmd.java":
            case "Query.java":
                return getJavaFilePath(javaPath, "adapter", "api.command") + fileName;
            case "Context.java":
            case "QueryContext.java":
                return getJavaFilePath(javaPath, "domain", "context") + fileName;
            case "Controller.java":
                return getJavaFilePath(javaPath, "adapter", "api.controller") + fileName;
            case "AdapterConvert.java":
                return getJavaFilePath(javaPath, "adapter", "api.convert") + fileName;
            case "Dto.java":
                return getJavaFilePath(javaPath, "domain", "dto") + fileName;
            case "Base.java":
                return getJavaFilePath(javaPath, "domain", "common.entity") + fileName;
            case "Executor.java":
                fileName =  "I" + fileName;
                return getJavaFilePath(javaPath, "application", "executor") + fileName;
            case "ExecutorImpl.java":
                return getJavaFilePath(javaPath, "application", "executor.impl") + fileName;
            case "Gateway.java":
                fileName =  "I" + fileName;
                return getJavaFilePath(javaPath, "domain", "gateway") + fileName;
            case "GatewayImpl.java":
                return getJavaFilePath(javaPath, "infra", "gateway.impl") + fileName;
            case "InfraConvert.java":
                return getJavaFilePath(javaPath, "infra", "convert") + fileName;
            case "Mapper.java":
                return mapperPackage + fileName;
            case "Mapper.xml":
                return getFilePath(mybatisPath, null, null)+ entityName+ "Mapper.xml";
            case "QueryBase.java":
                return getJavaFilePath(javaPath, "domain", "common.query") + fileName;
            case "Service.java":
                fileName =  "I" + fileName;
                return getJavaFilePath(javaPath, "infra", "service") + fileName;
            case "ServiceImpl.java":
                return getJavaFilePath(javaPath, "infra", "service.impl") + fileName;
            case "api.js":
                return getFilePath(rootPath, genConfig.getApiPath(), genConfig.getModuleName()) + lowerEntityName + ".js";
            case "vue.vue":
                return getFilePath(rootPath, genConfig.getPath(), genConfig.getModuleName() + SymbolConstant.period + lowerEntityName + lowerEntityName) + "index.vue";
            default:
                return null;
        }
    }
    private static String getJavaFilePath(String rootPath, String level, String path) {
        return getFilePath(rootPath, level, null, null, path);
    }

    private static String getFilePath(String rootPath, String path, String module) {
        return getFilePath(rootPath, path, module, "", "");
    }

    private static String getFilePath(String rootPath, String path, String module, String subModule, String packageName) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(rootPath)){
            sb.append(rootPath).append(SymbolConstant.period);
        }
        if (StringUtils.isNotEmpty(path)) {
            sb.append(path).append(SymbolConstant.period);
        }
        if (StringUtils.isNotEmpty(module)) {
            sb.append(module).append(SymbolConstant.period);
        }
        if (StringUtils.isNotEmpty(subModule)) {
            sb.append(subModule).append(SymbolConstant.period);
        }
        if (StringUtils.isNotEmpty(packageName)) {
            sb.append(packageName).append(SymbolConstant.period);
        }
        return StringUtils.replace(sb.toString(), SymbolConstant.period, File.separator);
    }
}
