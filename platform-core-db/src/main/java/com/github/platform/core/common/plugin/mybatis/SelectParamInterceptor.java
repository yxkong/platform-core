package com.github.platform.core.common.plugin.mybatis;
//
//import com.github.platform.arbitration.domain.constant.DataRoleEnum;
//import com.github.platform.arbitration.infrastructure.common.util.OwnerUtil;
//import org.apache.commons.lang.StringUtils;
//import org.apache.ibatis.binding.MapperMethod;
//import org.apache.ibatis.builder.annotation.ProviderSqlSource;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ParameterMapping;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.beans.BeanUtils;
//import org.springframework.util.ClassUtils;
//
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * 查询时根据角色增加权限
// *   1， 不能改写invocation.proceed()为executor.query ,这么改写把链给断了
// *   2,  select 不像insert或update都是由代码生成器生成的
// * @author yxkong
// * @date 2020/8/1-21:26
// */
//@SuppressWarnings({"rawtypes", "unchecked"})
//@Intercepts(
//        {
//                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
//        }
//)
//public class SelectParamInterceptor extends InterceptorBase implements Interceptor {
//
//
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        //代理对象是一个Executor
//        Executor executor = (Executor) invocation.getTarget();
//        //获取目标方法的所有参数
//        Object[] args = invocation.getArgs();
//        // 获取第一个参数
//        MappedStatement ms = (MappedStatement) args[0];
//        if (ms.getSqlCommandType() != SqlCommandType.SELECT) {
//            return invocation.proceed();
//        }
//        Object parameter = args[1];
//        RowBounds rowBounds = (RowBounds) args[2];
//        ResultHandler resultHandler = (ResultHandler) args[3];
//        CacheKey cacheKey;
//        // 如果类型是map，提前处理，要不ms.getBoundSql(parameter)会报错
//        parameter = processParamAnnotation(ms,parameter);
//        BoundSql boundSql;
//        //使用不同的方法时，获取boundSql
//        if (args.length == 4) {
//            //<E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;
//            boundSql = ms.getBoundSql(parameter);
//            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
//        } else {
//            // <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;
//            boundSql = (BoundSql) args[5];
//            cacheKey = (CacheKey) args[4];
//        }
//        // 改写参数
//        parameter = processParam(parameter, boundSql);
//
//        BoundSql companyPeriodBoundSql = new BoundSql(ms.getConfiguration(), companyPeriodSql, boundSql.getParameterMappings(), parameter);
//
//        args[1] = parameter;
//        return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, companyPeriodBoundSql);
//    }
//
//    private Object processParam(Object paramObj, BoundSql boundSql) throws Exception {
//
//        Map<String, Object> paramMap = new MapperMethod.ParamMap<>();
//        if (ClassUtils.isPrimitiveOrWrapper(paramObj.getClass())
//                || String.class.isAssignableFrom(paramObj.getClass())
//                || Number.class.isAssignableFrom(paramObj.getClass())) {
//            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//            if (parameterMappings.size() == 1) {
//                ParameterMapping parameterMapping = parameterMappings.get(0);
//                if (!StringUtils.equals(parameterMapping.getProperty(), OWNER_USER_ID)) {
//                    paramMap.put(parameterMapping.getProperty(), paramObj);
//                    paramMap.put("param1", paramObj);
//                    paramMap.put(OWNER_USER_ID, OwnerUtil.getUserId());
//                    paramMap.put("param2", OwnerUtil.getUserId());
//                    paramObj = paramMap;
//                }
//            }
//        } else {
//            fillentCode(paramObj);
//        }
//        return paramObj;
//    }
//
//    private Object processParamAnnotation(MappedStatement ms ,Object paramObj) {
//        Map<String, Object> map = new MapperMethod.ParamMap<>();
//        if(paramObj == null){
//            // 参数为空
//        } else if(paramObj instanceof Map){
//            //解决不可变Map的情况
//            map.putAll((Map) paramObj);
//        } else {
//            // sqlSource为ProviderSqlSource时，处理只有1个参数的情况
//            if (ms.getSqlSource() instanceof ProviderSqlSource) {
//                ProviderSqlSource providerSqlSource = (ProviderSqlSource) ms.getSqlSource();
//                providerSqlSource.g
//                String[] providerMethodArgumentNames = ExecutorUtil.getProviderMethodArgumentNames((ProviderSqlSource) ms.getSqlSource());
//                if (providerMethodArgumentNames != null && providerMethodArgumentNames.length == 1) {
//                    paramMap.put(providerMethodArgumentNames[0], parameterObject);
//                    paramMap.put("param1", parameterObject);
//                }
//            }
//            //动态sql时的判断条件不会出现在ParameterMapping中，但是必须有，所以这里需要收集所有的getter属性
//            //TypeHandlerRegistry可以直接处理的会作为一个直接使用的对象进行处理
//            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass());
//            MetaObject metaObject = MetaObjectUtil.forObject(parameterObject);
//            //需要针对注解形式的MyProviderSqlSource保存原值
//            if (!hasTypeHandler) {
//                for (String name : metaObject.getGetterNames()) {
//                    paramMap.put(name, metaObject.getValue(name));
//                }
//            }
//            //下面这段方法，主要解决一个常见类型的参数时的问题
//            if (boundSql.getParameterMappings() != null && boundSql.getParameterMappings().size() > 0) {
//                for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
//                    String name = parameterMapping.getProperty();
//                    if (!name.equals(GLOBALPERIOD)
//                            && paramMap.get(name) == null) {
//                        if (hasTypeHandler
//                                || parameterMapping.getJavaType().equals(parameterObject.getClass())) {
//                            paramMap.put(name, parameterObject);
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//
//        switch (DataRoleEnum.of(OwnerUtil.getRoleKey())){
//            //仲裁委角色
//            case ARBITRATION:
//            case ROBOT:
//            case FINTECH:
//                //查询所有，不自动加权限
//            case LAWFIRM:
//                map.putIfAbsent(OWNER_LAW_FIRM_ID,OwnerUtil.getLawFirmId());
//            case LEADER:
//                map.putIfAbsent(OWNER_LAW_FIRM_ID,OwnerUtil.getLawFirmId());
//                map.putIfAbsent(OWNER_OO_ID,OwnerUtil.getOoId());
//            case GROUP:
//                map.putIfAbsent(OWNER_LAW_FIRM_ID,OwnerUtil.getLawFirmId());
//                map.putIfAbsent(OWNER_OO_ID,OwnerUtil.getOoId());
//                map.putIfAbsent(OWNER_GROUP_ID,OwnerUtil.getGroupId());
//            case USER:
//                map.putIfAbsent(OWNER_LAW_FIRM_ID,OwnerUtil.getLawFirmId());
//                map.putIfAbsent(OWNER_OO_ID,OwnerUtil.getOoId());
//                map.putIfAbsent(OWNER_GROUP_ID,OwnerUtil.getGroupId());
//                map.putIfAbsent(OWNER_USER_ID,OwnerUtil.getUserId());
//            default:
//                map.putIfAbsent(OWNER_USER_ID,OwnerUtil.getUserId());
//        }
//        return map;
//    }
//
//
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//    }
//
//}
