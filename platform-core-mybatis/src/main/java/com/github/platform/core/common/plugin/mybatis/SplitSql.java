package com.github.platform.core.common.plugin.mybatis;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析sql
 *
 * @author yxkong
 */
public class SplitSql {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static String insert = "INSERT";
    private static String update = "UPDATE";
    private static String select = "SELECT";
    private static String delete = "DELETE";
    private MappedStatement mappedStatement;
    private Object parameterObject;
    private BoundSql boundSql;
    private String orderBySql = null;

    public SplitSql(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        this.mappedStatement = mappedStatement;
        this.parameterObject = parameterObject;
        this.boundSql = boundSql;
    }

    public SplitSqlResult getShardingSql(String sql, TableSeg tableSeg) {

        // getShardingVal(tableSeg.shardBy());

        if (mappedStatement.getSqlCommandType().toString().equals(insert)) {
            // 这是一个插入的
            Long shardVaLong = getShardingVal(tableSeg.shardBy());
            if (shardVaLong == null) {
                logger.error("method[getShardingSql] INSERT分表键不能为空{}", JsonUtils.toJson(parameterObject));
                throw new RuntimeException();
            }
            // return convertInertTable(sql, shardVaLong, tableSeg);

            return new SplitSqlResult(convertInertTable(sql, shardVaLong, tableSeg),
                    getParameterMappingList(shardVaLong, tableSeg));
        }
        if (mappedStatement.getSqlCommandType().toString().equals(update)) {
            // 更新 只完成主键更新
            Long shardVaLong = getShardingVal(tableSeg.shardBy());
            if (shardVaLong == null) {
                logger.error("method[getShardingSql] UPDATE分表键不能为空{}", JsonUtils.toJson(parameterObject));
                throw new RuntimeException();
            }
            // return convertUpdateTable(sql, shardVaLong, tableSeg);
            return new SplitSqlResult(convertUpdateTable(sql, shardVaLong, tableSeg),
                    getParameterMappingList(shardVaLong, tableSeg));

        }
        if (mappedStatement.getSqlCommandType().toString().equals(select)) {
            // 查询
            Long shardVaLong = getShardingVal(tableSeg.shardBy());

            // return convertSelectTable(sql, shardVaLong, tableSeg);
            return new SplitSqlResult(convertSelectTable(sql, shardVaLong, tableSeg),
                    getParameterMappingList(shardVaLong, tableSeg));
        }
        if (mappedStatement.getSqlCommandType().toString().equals(delete)) {
            // 删除 只完成主键删除
            Long shardVaLong = getShardingVal(tableSeg.shardBy());
            if (shardVaLong == null) {
                logger.error("method[getShardingSql] DELETE分表键不能为空{}", JsonUtils.toJson(parameterObject));
                throw new RuntimeException();
            }
            // return convertDeleteTable(sql, shardVaLong, tableSeg);
            return new SplitSqlResult(convertDeleteTable(sql, shardVaLong, tableSeg),
                    getParameterMappingList(shardVaLong, tableSeg));
        }

        return new SplitSqlResult(sql, boundSql.getParameterMappings());
    }

    /**
     * 实现插入表转换
     *
     * @param sql
     * @param shardingVal
     * @param tableSeg
     * @return
     */
    public String convertInertTable(String sql, Long shardingVal, TableSeg tableSeg) {
        // 有待优化
        String newTableName = new StringBuffer().append(tableSeg.tableName()).append("_")
                .append(getTableName(shardingVal, tableSeg)).toString();
        sql = sql.replaceFirst(tableSeg.tableName(), newTableName);
        return sql;
    }

    public String convertUpdateTable(String sql, Long shardingVal, TableSeg tableSeg) {

        // 有待优化
        String newTableName = new StringBuffer().append(tableSeg.tableName()).append("_")
                .append(getTableName(shardingVal, tableSeg)).toString();
        sql = sql.replaceFirst(tableSeg.tableName(), newTableName);
        return sql;
    }

    public String convertDeleteTable(String sql, Long shardingVal, TableSeg tableSeg) {

        // 有待优化
        String newTableName = new StringBuffer().append(tableSeg.tableName()).append("_")
                .append(getTableName(shardingVal, tableSeg)).toString();
        sql = sql.replaceFirst(tableSeg.tableName(), newTableName);
        return sql;
    }

    public String convertSelectTable(String sql, Long shardingVal, TableSeg tableSeg) {

        // 有待优化
        if (shardingVal != null) {
            String newTableName = new StringBuffer().append(tableSeg.tableName()).append("_")
                    .append(getTableName(shardingVal, tableSeg)).toString();
            sql = sql.replaceFirst(tableSeg.tableName(), newTableName);
            return sql;
        }

        // 判断sql 是否包含order by
        sql = hasOrderBySql(sql);

        StringBuffer sqlBuffer = new StringBuffer();
        for (int i = 0; i < tableSeg.nums(); i++) {
            String newTableName = new StringBuffer().append(tableSeg.tableName()).append("_")
                    .append(getFormatName(i, tableSeg)).toString();
            String tempSql = sql.replaceFirst(tableSeg.tableName(), newTableName);
            sqlBuffer.append(tempSql);
            if (tableSeg.nums() - 1 != i) {
                sqlBuffer.append(" UNION ALL ");
            }
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (ParameterMapping parameterMapping : parameterMappings) {
                Object val = getVal(parameterMapping.getProperty());
                boundSql.setAdditionalParameter(parameterMapping.getProperty() + i, val);
            }

        }
        if (StringUtils.isNotEmpty(orderBySql)) {
            sqlBuffer.append(" ").append(orderBySql);
        }
        ShardingResultInterceptor.HAS_MERGE.set(true);
        return sqlBuffer.toString();
    }

    public String getTableName(Long shardingVal, TableSeg tableSeg) {

        int len = String.valueOf(tableSeg.nums()).length();

        return String.format("%0" + len + "d", shardingVal % tableSeg.nums());
    }

    public String getFormatName(int i, TableSeg tableSeg) {

        int len = String.valueOf(tableSeg.nums()).length();

        return String.format("%0" + len + "d", i);
    }

    private static String orderByPattern = ".order\\s*by.*";

    public String hasOrderBySql(String sql) {
        Pattern p = Pattern.compile(orderByPattern);
        Matcher matcher = p.matcher(sql.toLowerCase());
        if (matcher.find()) {
            // matcher.start()+"---"+matcher.end()
            orderBySql = sql.substring(matcher.start());
            sql = sql.substring(0, matcher.start());
        }

        return sql;
    }

    /**
     * 获取分表主键值
     *
     * @param parameterName
     * @return
     */
    public Long getShardingVal(String parameterName) {
        return (Long) getVal(parameterName);
    }

    @SuppressWarnings("unchecked")
    public Object getVal(String parameterName) {

        if (parameterObject instanceof Long || parameterObject instanceof String
                || parameterObject instanceof Integer) {
            // return parameterObject;
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (ParameterMapping parameterMapping : parameterMappings) {
                if (parameterMapping.getProperty().equalsIgnoreCase(parameterName)) {
                    return (Long) parameterObject;
                }
            }
            return null;
        }
        if (parameterObject instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) parameterObject;

            return map.get(parameterName);
        }

        try {
            Method method = (Method) parameterObject.getClass().getMethod(getMethodName(parameterName));

            if (method != null) {
                return method.invoke(parameterObject);

            }
        } catch (Exception e) {
            logger.error("err:", e);
            return null;
        }
        return null;
    }

    public List<ParameterMapping> getParameterMappingList(Long shardVaLong, TableSeg tableSeg) {

        if (shardVaLong != null) {
            return boundSql.getParameterMappings();
        }

        List<ParameterMapping> list = new ArrayList<ParameterMapping>();
        for (int i = 0; i < tableSeg.nums(); i++) {
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (ParameterMapping parameterMapping : parameterMappings) {
                ParameterMapping p = new ParameterMapping.Builder(mappedStatement.getConfiguration(),
                        parameterMapping.getProperty() + i, parameterMapping.getJavaType()).build();
                list.add(p);
            }
        }
        return list;
    }

    private static String getMethodName(String fildeName) throws Exception {
        StringBuffer sbBuffer = new StringBuffer("get");
        String firstLetter = fildeName.substring(0, 1).toUpperCase();
        sbBuffer.append(firstLetter).append(fildeName.substring(1, fildeName.length()));
        return sbBuffer.toString();
    }

    public class SplitSqlResult {

        public SplitSqlResult(String sql, List<ParameterMapping> parameterMappings) {
            this.sql = sql;
            this.parameterMappings = parameterMappings;
        }

        private String sql;
        private List<ParameterMapping> parameterMappings;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public List<ParameterMapping> getParameterMappings() {
            return parameterMappings;
        }

        public void setParameterMappings(List<ParameterMapping> parameterMappings) {
            this.parameterMappings = parameterMappings;
        }
    }
}
