package com.github.platform.core.common.plugin.mybatis;

import com.github.platform.core.common.plugin.mybatis.dialect.BaseDialect;
import com.github.platform.core.common.plugin.mybatis.dialect.MySQLDialect;
import com.github.platform.core.common.plugin.mybatis.dialect.OracleDialect;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author: yxkong
 * @date: 2021/6/3 5:15 下午
 * @version: 1.0
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class}) })
public class PaginationInterceptor implements Interceptor {
    // 日志对象
    protected static Logger log = LoggerFactory.getLogger(PaginationInterceptor.class);
    private static ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static DefaultReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin
     * .Invocation)
     */
    @SuppressWarnings("unused")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation
                .getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(
                statementHandler, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        // MetaObject.forObject(object, objectFactory, objectWrapperFactory,
        // reflectorFactory)

        RowBounds rowBounds = (RowBounds) metaStatementHandler
                .getValue("delegate.rowBounds");
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        }

        DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) metaStatementHandler
                .getValue("delegate.parameterHandler");
        // @SuppressWarnings("unchecked")
        // Map<Object, Object> parameterMap = (Map<Object, Object>)
        // defaultParameterHandler.getParameterObject();
        // Object sidx = parameterMap.get("_sidx");
        // Object sord = parameterMap.get("_sord");
        //
        String originalSql = (String) metaStatementHandler
                .getValue("delegate.boundSql.sql");
        //
        // if (sidx != null && sord != null) {
        // originalSql = originalSql + " order by " + sidx + " " + sord;
        // }

        Configuration configuration = (Configuration) metaStatementHandler
                .getValue("delegate.configuration");

        BaseDialect.Type databaseType = null;
        try {
            databaseType = BaseDialect.Type.valueOf(configuration.getVariables()
                    .getProperty("dialect").toUpperCase());
        } catch (Exception e) {
            // ignore
            databaseType = BaseDialect.Type.valueOf("MYSQL");
        }
        if (databaseType == null) {
            throw new RuntimeException(
                    "the value of the dialect property in configuration.xml is not defined : "
                            + configuration.getVariables().getProperty(
                            "dialect"));
        }
        BaseDialect dialect = null;
        switch (databaseType) {
            case ORACLE:
                dialect = new OracleDialect();
                break;
            case MYSQL:
            default:
                dialect = new MySQLDialect();
                break;

        }

        metaStatementHandler.setValue("delegate.boundSql.sql", dialect
                .getLimitString(originalSql, rowBounds.getOffset(),
                        rowBounds.getLimit()));
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        if (log.isDebugEnabled()) {
            BoundSql boundSql = statementHandler.getBoundSql();
            log.debug("生成分页SQL : " + boundSql.getSql());
        }
        return invocation.proceed();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
     */
    @Override
    public void setProperties(Properties arg0) {

    }
}