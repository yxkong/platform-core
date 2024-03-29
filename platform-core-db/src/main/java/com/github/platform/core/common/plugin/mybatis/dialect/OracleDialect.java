package com.github.platform.core.common.plugin.mybatis.dialect;

/**
 * @author: yxkong
 * @date: 2021/6/3 5:19 下午
 * @version: 1.0
 */
public class OracleDialect extends BaseDialect {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String,
     * int, int)
     */
    @Override
    public String getLimitString(String sql, int offset, int limit) {

        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

        pagingSelect.append(sql);

        pagingSelect.append(" ) row_ ) where rownum_ > ").append(offset).append(" and rownum_ <= ")
                .append(offset + limit);

        return pagingSelect.toString();
    }

}