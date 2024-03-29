package com.github.platform.core.common.plugin.mybatis.dialect;

/**
 * @author: yxkong
 * @date: 2021/6/3 5:16 下午
 * @version: 1.0
 */
public class MySQLDialect extends BaseDialect {

    private static String limitString =" limit ";
    private static String commaString =" , ";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql,offset,String.valueOf(offset),limit,String.valueOf(limit));
    }

    public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
//        if (offset > limit) {
//            return sql + " limit "+offsetPlaceholder+","+limitPlaceholder;
//        } else {
//            return sql + " limit "+limitPlaceholder;
//        }
        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append(sql).append(limitString).append(offsetPlaceholder).append(commaString).append(limitPlaceholder);
        return pagingSelect.toString();
    }

}