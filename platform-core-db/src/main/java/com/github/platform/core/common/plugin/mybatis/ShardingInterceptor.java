package com.github.platform.core.common.plugin.mybatis;


import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Objects;
import java.util.Properties;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class ShardingInterceptor implements Interceptor {
	// 日志对象
	protected static Logger log = LoggerFactory.getLogger(ShardingInterceptor.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final DefaultReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		ShardingResultInterceptor.HAS_MERGE.set(false);

		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
		BoundSql boundSql = statementHandler.getBoundSql();

		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");

		if (Objects.nonNull(originalSql)) {

			Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");

			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
					.getValue("delegate.mappedStatement");
			String id = mappedStatement.getId();
			String className = id.substring(0, id.lastIndexOf("."));
			Class<?> classObj = Class.forName(className);
			// 根据配置自动生成分表SQL
			TableSeg tableSeg = classObj.getAnnotation(TableSeg.class);
			if (tableSeg != null) {
				SplitSql makeSql = new SplitSql(mappedStatement, parameterObject, boundSql);
				SplitSql.SplitSqlResult splitSqlResult = makeSql.getShardingSql(originalSql, tableSeg);

				originalSql = splitSqlResult.getSql();
				metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);

			//	metaStatementHandler.setValue("delegate.boundSql.parameterMappings",
			//			splitSqlResult.getParameterMappings());
			}
		}
		Object object = invocation.proceed();
		return object;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}
