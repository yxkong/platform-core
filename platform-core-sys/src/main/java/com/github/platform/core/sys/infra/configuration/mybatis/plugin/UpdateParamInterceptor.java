package com.github.platform.core.sys.infra.configuration.mybatis.plugin;

import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * insert or update 前给入参中的entCode、createTime、updateTime赋值
 *
 * @author navyzhung
 * @date 2021/7/2-11:03
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts({
        // @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})/*,
        @Signature(type = Executor.class, method = "createCacheKey", args = {MappedStatement.class, Object.class, RowBounds.class, BoundSql.class})*/
}
)
public class UpdateParamInterceptor extends InterceptorBase implements Interceptor {



    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        BoundSql boundSql = null;
        if (target instanceof CachingExecutor){
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            boundSql = mappedStatement.getBoundSql(invocation.getArgs()[1]);

        }
        /**
         *  invocation 为 Executor 中对应的单表方法
         *  拦截 Executor 的 update 方法 生成sql前将指定参数设置到实体中
         */

        if (invocation.getTarget() instanceof Executor && invocation.getArgs().length == 2 ) {
            return invokeUpdate(invocation,boundSql);
        }
        // 拦截 Executor 的 createCacheKey 方法，pageHelper插件会拦截 query 方法，调用此方法，提前将参数设置到参数集合中
        /*if (invocation.getTarget() instanceof Executor && invocation.getArgs().length == 4) {
            return invokeCacheKey(invocation);
        }*/
        //拦截 ParameterHandler 的 setParameters 方法 动态设置参数
        /*if (invocation.getTarget() instanceof ParameterHandler) {
            return invokeSetParameter(invocation);
        }*/
        return null;
    }

//    private Object invokeCacheKey(Invocation invocation) throws Exception {
//        Executor executor = (Executor) invocation.getTarget();
//        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
//        if (ms.getSqlCommandType() != SqlCommandType.SELECT/* && ms.getSqlCommandType() != SqlCommandType.DELETE*/) {
//            return invocation.proceed();
//        }
//
//        Object parameterObject = invocation.getArgs()[1];
//        RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
//        BoundSql boundSql = (BoundSql) invocation.getArgs()[3];
//
//        List<String> paramNames = new ArrayList<>();
//        boolean hasKey = hasParamKey(paramNames, boundSql.getParameterMappings());
//
//        if (!hasKey) {
//            return invocation.proceed();
//        }
//        // 改写参数
//        parameterObject = processSingle(parameterObject, paramNames);
//
//        return executor.createCacheKey(ms, parameterObject, rowBounds, boundSql);
//    }

    private Object invokeUpdate(Invocation invocation,BoundSql boundSql) throws Exception {
        //代理对象是一个Executor
        Executor executor = (Executor) invocation.getTarget();
        // 获取第一个参数
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        // 非 insert和update 语句 不处理
        if (ms.getSqlCommandType() != SqlCommandType.INSERT && ms.getSqlCommandType() != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }
        // mybatis的参数对象
        Object paramObj = invocation.getArgs()[1];
        if (paramObj == null) {
            return invocation.proceed();
        }

        // 更新语句只传一个基本类型参数, 不做处理
        if (ClassUtils.isPrimitiveOrWrapper(paramObj.getClass())
                || String.class.isAssignableFrom(paramObj.getClass())
                || Number.class.isAssignableFrom(paramObj.getClass())) {
            return invocation.proceed();
        }
        processParam(paramObj, ms);
        //修改原来的参数
        invocation.getArgs()[1] = paramObj;

        return invocation.proceed();
    }

    private void processParam(Object parameterObject, MappedStatement ms) throws IllegalAccessException, InvocationTargetException {
        // 处理参数对象  如果是 map 且map的key 中没有 entCode，添加到参数map中
        // 如果参数是bean，反射设置值
        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            fillInsert(parameterObject);
        } else if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
            fillUpdate(parameterObject);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
    protected  <T> T realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }
}
