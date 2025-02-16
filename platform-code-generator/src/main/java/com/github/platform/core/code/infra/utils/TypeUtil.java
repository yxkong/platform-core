package com.github.platform.core.code.infra.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 类型转化
 * @author yxkong
 */
@Slf4j
public class TypeUtil {
    /**
     * 转换mysql数据类型为java数据类型
     * @param mysqlType
     * @return
     */
    public static String mysqlTypeToJavaType(Object mysqlType) {
        String type = String.valueOf(mysqlType).toLowerCase().trim();
        switch (type){
            case "char":
            case "longtext":
            case "mediumtext":
            case "varchar":
            case "text":
            case "blob":
            case "tinyblob":
            case "longblob":
                return "String";
            case "date":
                return "LocalDate";
            case "time":
            case "datetime":
            case "timestamp":
                return "LocalDateTime";
            case "smallint":
            case "smallint unsigned":
            case "tinyint":
            case "tinyint unsigned":
            case "int":
            case "int unsigned":
                return "Integer";
            case "bit":
                return "Boolean";
            case "bigint":
            case "bigint unsigned":
                return "Long";
            case "decimal":
            case "decimal unsigned":
            case "numeric":
            case "numeric unsigned":
                return "BigDecimal";
            case "float":
            case "float unsigned":
                return  "Float";
            case "double":
            case "double unsigned":
                return "Double";
            default:
                return null;
        }
    }
}
