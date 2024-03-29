package com.github.platform.core.common.plugin.mybatis;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

@Intercepts(@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }))
public class ShardingResultInterceptor implements Interceptor {

	public final static ThreadLocal<Boolean> HAS_MERGE = new ThreadLocal<Boolean>();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		Object[] args = invocation.getArgs();
		MappedStatement mappedStatement = (MappedStatement) args[0];
		Object parameterObject = args[1];
		RowBounds rowBounds = (RowBounds) args[2];
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);

		Object object = invocation.proceed();

		if (HAS_MERGE.get()) {
			object = MergeResultHandler.mergeResultSet(mappedStatement, boundSql, object, rowBounds);
		}
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
