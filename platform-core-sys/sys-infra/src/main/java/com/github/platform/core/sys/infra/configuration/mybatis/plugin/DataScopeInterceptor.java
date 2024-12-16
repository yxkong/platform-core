package com.github.platform.core.sys.infra.configuration.mybatis.plugin;

import com.github.platform.core.auth.constant.DataScopeEnum;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.constant.CommonFiledMapEnum;
import com.github.platform.core.common.utils.CollectionUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 只做单表
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class})})
public class DataScopeInterceptor extends InterceptorBase {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        BoundSql boundSql = statementHandler.getBoundSql();
        Object obj = boundSql.getParameterObject();
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        /**存储过程直接放行，直接放行*/
        if (StatementType.CALLABLE == ms.getStatementType() || !isTenantAuth(ms)) {
            return invocation.proceed();
        }
        /**dml处理*/
        if (SqlCommandType.INSERT == ms.getSqlCommandType() ||
                SqlCommandType.UPDATE == ms.getSqlCommandType() || SqlCommandType.DELETE == ms.getSqlCommandType()){
            return dmlProcess(ms,invocation);
        }
        String sql = boundSql.getSql();
        /**只处理单表查询*/
        if (SqlCommandType.SELECT == ms.getSqlCommandType() && isSingleTable(sql,ms.getSqlCommandType())){
            return selectProcess(metaObject,invocation,sql);
        }

        return invocation.proceed();
    }
    private String getBaseColumnList(String id){
        return id.substring(0, id.lastIndexOf('.'))+".BaseColumnList";
    }

    /**
     * 判断是否需要租户权限
     * @param ms
     * @return
     */
    private boolean isTenantAuth(MappedStatement ms){
        String bcKey = getBaseColumnList(ms.getId());
        if(!ms.getConfiguration().getSqlFragments().containsKey(bcKey)){
           return Boolean.FALSE;
        }

        XNode xNode = ms.getConfiguration().getSqlFragments().get(bcKey);
        String stringBody = xNode.getStringBody();
        if (Objects.isNull(stringBody)){
            return Boolean.FALSE;
        }
        return stringBody.contains(CommonFiledMapEnum.TENANT_ID.getDbFiled()) ;
    }

    /**
     * dml操作处理
     * @param ms
     * @param invocation
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object dmlProcess(MappedStatement ms,Invocation invocation) throws InvocationTargetException, IllegalAccessException {

        return invocation.proceed();
    }

    /**
     * select操作处理
     * @param metaObject
     * @param invocation
     * @param sql
     * @return
     * @throws JSQLParserException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object selectProcess(MetaObject metaObject,Invocation invocation,String sql) throws JSQLParserException, InvocationTargetException, IllegalAccessException {
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        Expression oldWhereEx = ((PlainSelect) select.getSelectBody()).getWhere();
        if (AuthUtil.isSuperAdmin()){
            return invocation.proceed();
        }
        DataScopeEnum maxPrivilege = LoginUserInfoUtil.getMaxPrivilege();
        List<Object> additionalParams = new ArrayList<>();
        StringBuffer  sqlWhere  =  new StringBuffer(oldWhereEx == null ?" 1=1 " : oldWhereEx.toString());
        switch (maxPrivilege){
            //所有数据
            case ALL:
                //查询所有，不自动加权限
                break;
            case TENANT:
                sqlWhere.append(" AND ").append(CommonFiledMapEnum.TENANT_ID.getDbFiled()).append(" = ?");
                additionalParams.add(LoginUserInfoUtil.getTenantId());
                break;
            case DEPT:
                //查询所有，不自动加权限
                sqlWhere.append(" AND ").append(CommonFiledMapEnum.TENANT_ID.getDbFiled()).append(" = ?")
                        .append(" AND ").append(CommonFiledMapEnum.DEPT_ID.getDbFiled()).append(" = ?");
                additionalParams.add(LoginUserInfoUtil.getTenantId());
                additionalParams.add(LoginUserInfoUtil.getDeptId());
                break;
            case DEPT_DOWN:
                sqlWhere.append(" AND ").append(CommonFiledMapEnum.TENANT_ID.getDbFiled()).append(" = ?");
                additionalParams.add(LoginUserInfoUtil.getTenantId());
                if (CollectionUtil.isNotEmpty(LoginUserInfoUtil.getAllDeptId())){
                    sqlWhere.append(" AND ").append(CommonFiledMapEnum.DEPT_ID.getDbFiled()).append(" in (?)");
                    String collect = LoginUserInfoUtil.getAllDeptId().stream().map(String::valueOf).collect(Collectors.joining(","));
                    additionalParams.add(collect);
                }
                break;
            default:
                sqlWhere.append(" AND ").append(CommonFiledMapEnum.TENANT_ID.getDbFiled()).append(" = ?")
                        .append(" AND ").append(CommonFiledMapEnum.CREATE_BY.getDbFiled()).append(" = ?");
                additionalParams.add(LoginUserInfoUtil.getTenantId());
                additionalParams.add(LoginUserInfoUtil.getLoginName());
                break;
        }
        ((PlainSelect) select.getSelectBody()).setWhere(CCJSqlParserUtil.parseCondExpression(sqlWhere.toString()));

        metaObject.setValue("delegate.boundSql.sql", select.toString());
        metaObject.setValue("delegate.boundSql.additionalParameters", additionalParams);
        return invocation.proceed();
    }
    private  <T> T realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }

}
