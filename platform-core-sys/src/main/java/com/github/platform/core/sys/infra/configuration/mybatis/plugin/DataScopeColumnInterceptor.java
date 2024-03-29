package com.github.platform.core.sys.infra.configuration.mybatis.plugin;

import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.web.util.WebUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * insert or update 前给入参中的entCode、createTime、updateTime赋值
 * @author navyzhung
 * @date 2021/7/2-11:03
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
@Deprecated
public class DataScopeColumnInterceptor implements Interceptor {

    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";
    private static final String CREATE_BY = "createBy";
    private static final String UPDATE_BY = "updateBy";
    private static final String TENANT_ID = "tenantId";
    private static final String DEPT_ID = "deptId";
    private static final String REQUEST_IP = "requestIp";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object paramObj = args[1];

        if (!SqlCommandType.INSERT.equals(ms.getSqlCommandType()) && !SqlCommandType.UPDATE.equals(ms.getSqlCommandType())) {
            return invocation.proceed();
        }

        if (AuthUtil.isLogin()) {
            fillField(paramObj, SqlCommandType.INSERT.equals(ms.getSqlCommandType()) ? CREATE_TIME : UPDATE_TIME, LocalDateTimeUtil.dateTime());
            fillField(paramObj, SqlCommandType.INSERT.equals(ms.getSqlCommandType()) ? CREATE_BY : UPDATE_BY, LoginUserInfoUtil.getLoginName());

            fillField(paramObj, TENANT_ID, LoginUserInfoUtil.getTenantId());
            fillField(paramObj, DEPT_ID, LoginUserInfoUtil.getDeptId());
        }

        if (Objects.nonNull(RequestContextHolder.getRequestAttributes())) {
            fillField(paramObj, REQUEST_IP, WebUtil.getIpAddress());
        }

        return invocation.proceed();
    }

    /**
     * 填充字段(map填充或者类属性填充)
     *
     * @param paramObj
     * @param property
     * @param value
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    protected void fillField(Object paramObj, String property, Object value) throws IllegalAccessException, InvocationTargetException {
        if (paramObj instanceof Map) {
            Map originParamMap = ((Map) paramObj);
            originParamMap.putIfAbsent(property, value);
            Collection list = (Collection) originParamMap.get("list");
            if (CollectionUtil.isNotEmpty(list)) {
                for (Object obj : list) {
                    fillField(obj, property, value);
                }
            }
        } else {
            fillProperty(paramObj, property, value);
        }
    }

    /**
     * 填充属性
     *
     * @param parameterObject 参数对象
     * @param property        属性
     * @param value           值
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void fillProperty(Object parameterObject, String property, Object value) throws IllegalAccessException, InvocationTargetException {
        PropertyDescriptor ps = BeanUtils.getPropertyDescriptor(parameterObject.getClass(), property);
        if (ps != null && ps.getReadMethod() != null && ps.getWriteMethod() != null) {
            Object originValue = ps.getReadMethod().invoke(parameterObject);
            if (originValue == null) {
                ps.getWriteMethod().invoke(parameterObject, value);
            }
        }
    }
}
