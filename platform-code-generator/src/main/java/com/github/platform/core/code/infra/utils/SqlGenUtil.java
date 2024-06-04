package com.github.platform.core.code.infra.utils;

import com.github.platform.core.code.domain.constant.CodeTypeEnum;
import com.github.platform.core.code.domain.constant.QueryTypeEnum;
import com.github.platform.core.common.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * sql生成工具
 * @Author: yxkong
 * @Date: 2023/4/27 3:24 PM
 * @version: 1.0
 */
public class SqlGenUtil {

    public static String buildInsertSql(List<Map<String, Object>> columns, String tableName) {
        StringBuilder sbBuffer = new StringBuilder();
        sbBuffer.append("insert into ").append(tableName);

        sbBuffer.append("\r\n\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (Map<String, Object> column : columns) {
            String lowerColumnName = (String) column.get("lowerColumnName");
            testWhere(sbBuffer, lowerColumnName,column);
            sbBuffer.append(column.get("columnName")).append(",</if>");
        }
        sbBuffer.append("\r\n\t\t</trim>");
        sbBuffer.append("\r\n\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        for (Map<String, Object> column : columns) {
            String lowerColumnName = (String) column.get("lowerColumnName");
            testWhere(sbBuffer, lowerColumnName,column);
            sbBuffer.append("#{").append(lowerColumnName).append("},</if>");
        }
        sbBuffer.append("\r\n\t\t</trim>");
        return sbBuffer.toString();
    }

    private static void testWhere(StringBuilder sbBuffer, String lowerColumnName,Map<String, Object> column) {
        String columnType =(String) column.get("columnType");
        if (StringUtils.isEmpty(columnType) || !"String".equals(columnType)){
            sbBuffer.append("\r\n\t\t\t<if test=\"").append(lowerColumnName).append(" != null \">");
        }else {
            sbBuffer.append("\r\n\t\t\t<if test=\"").append(lowerColumnName).append(" != null  and ").append(lowerColumnName).append(" != ''").append("\">");
        }

    }

    public static String buildUpdateSql(List<Map<String, Object>> columns, String tableName, String pkColumnName, String pkLowerColumnName) {
        StringBuilder sbBuffer = new StringBuilder();
        sbBuffer.append("update ").append(tableName);
        sbBuffer.append("\r\n\t\t<set>");

        for (Map<String, Object> column : columns) {
            String lowerColumnName = (String) column.get("lowerColumnName");
            String columnName = (String) column.get("columnName");

            if (!columnName.equals(pkColumnName)) {
                testWhere(sbBuffer, lowerColumnName,column);
                sbBuffer.append(columnName).append(" = ").append("#{").append(lowerColumnName).append("},</if>");
            }
        }
        sbBuffer.append("\r\n\t\t</set>");
        sbBuffer.append("\r\n\t\twhere ").append(pkColumnName).append(" = #{").append(pkLowerColumnName).append("}");
        return sbBuffer.toString();
    }

    public static String buildListSql(List<Map<String, Object>> columns, String tableName,Integer codeType) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append("\r\n\t\t<include refid=\"BaseColumnList\" />");
        sb.append("\r\n\t\tfrom ").append(tableName).append(" t");
        getWhere(columns, codeType, sb);

        return sb.toString();
    }

    private static void getWhere(List<Map<String, Object>> columns, Integer codeType, StringBuilder sb) {
        sb.append("\r\n\t\t<where>");

        for (Map<String, Object> column : columns) {
            Object obj = column.get("queryType");
            if (obj == null) {
                continue;
            }
            String lowerColumnName = column.get("lowerColumnName").toString();
            String columnName = column.get("columnName").toString();
            String queryType = obj.toString();

            if (StringUtils.isEmpty(queryType) || exclude(lowerColumnName)) {
                continue;
            }
            testWhere(sb, lowerColumnName,column);
            sb.append(" and t.").append(columnName);

            if (QueryTypeEnum.eq.getType().equals(queryType)) {
                sb.append(" = #{").append(lowerColumnName).append("}");
            } else if (QueryTypeEnum.like.getType().equals(queryType)) {
                sb.append(" like concat('%', #{").append(lowerColumnName).append("}, '%')");
            } else if (QueryTypeEnum.notNull.getType().equals(queryType)) {
                sb.append(" is not null");
            } else if (QueryTypeEnum.isNull.getType().equals(queryType)) {
                sb.append(" is null");
            } else if (QueryTypeEnum.geq.getType().equals(queryType)) {
                sb.append(" >= #{").append(lowerColumnName).append("}");
            } else if (QueryTypeEnum.gt.getType().equals(queryType)) {
                sb.append(" > #{").append(lowerColumnName).append("}");
            } else if (QueryTypeEnum.lt.getType().equals(queryType)) {
                sb.append(" < #{").append(lowerColumnName).append("}");
            } else if (QueryTypeEnum.leq.getType().equals(queryType)) {
                sb.append(" <= #{").append(lowerColumnName).append("}");
            } else if (QueryTypeEnum.between.getType().equals(queryType)) {
                String upperColumnName = column.get("upperColumnName").toString();
                sb.append(" between #{").append(lowerColumnName).append("} and #{").append(upperColumnName).append("}");
            }
            sb.append("</if>");
        }

        if (CodeTypeEnum.isSys(codeType)) {
            sb.append("\r\n\t\t\t<if test=\"searchStartTime != null\">");
            sb.append("\r\n\t\t\t\tand t.create_time <![CDATA[>=]]> #{searchStartTime}");
            sb.append("\r\n\t\t\t</if>");
            sb.append("\r\n\t\t\t<if test=\"searchEndTime != null\">");
            sb.append("\r\n\t\t\t\tand t.create_time <![CDATA[<=]]> #{searchEndTime}");
            sb.append("\r\n\t\t\t</if>");
        }

        sb.append("\r\n\t\t</where>");
    }

    private static boolean exclude(String column){
        if ("remark".equals(column) || "updateTime".equals(column) || "createTime".equals(column)){
            return true;
        }
        return false;
    }

    public static String buildListCountSql(List<Map<String, Object>> columns, String tableName, String pkColumnName,Integer codeType) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(t.").append(pkColumnName).append(") from ").append(tableName).append(" t");
        getWhere(columns, codeType, sb);
        return sb.toString();
    }
    public static String createResultStr(List<Map<String, Object>> columns) {
        StringJoiner joiner = new StringJoiner("\r\n\t\t");
        for (Map<String, Object> column : columns) {
            String lowerColumnName = (String) column.get("lowerColumnName");
            String columnName = (String)column.get("columnName");
            String idTag = String.format("<id column=\"%s\" property=\"%s\" />", columnName, lowerColumnName);
            joiner.add(idTag);
        }
        return joiner.toString();
    }

    public static String createBaseColumnListStr(List<Map<String, Object>> columns) {
        StringJoiner selectJoiner = new StringJoiner(",\n\t\t");
        for (Map<String, Object> column : columns) {
            String columnName = (String)column.get("columnName");
            selectJoiner.add( "t." +columnName);
        }
        return selectJoiner.toString();
    }
}
