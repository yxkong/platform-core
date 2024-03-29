
//
//import com.github.platform.core.auth.util.AuthUtil;
//import com.github.platform.core.auth.util.LoginUserInfoUtil;
//import com.github.platform.core.standard.constant.HeaderConstant;
//import com.github.platform.core.sys.infra.configuration.mybatis.plugin.InterceptorBase;
//import net.sf.jsqlparser.JSQLParserException;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.parser.CCJSqlParserUtil;
//import net.sf.jsqlparser.statement.select.PlainSelect;
//import net.sf.jsqlparser.statement.select.Select;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.mapping.StatementType;
//import org.apache.ibatis.parsing.XNode;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Proxy;
//import java.sql.Connection;
//import java.util.Objects;
//
///**
// * 只做单表
// */
//@Intercepts({
//        @Signature(
//                type = StatementHandler.class,
//                method = "prepare",
//                args = {Connection.class, Integer.class})})
//public class DataScopeInterceptor extends InterceptorBase implements Interceptor {
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        StatementHandler statementHandler = realTarget(invocation.getTarget());
//        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
//
//        BoundSql boundSql = statementHandler.getBoundSql();
//        Object obj = boundSql.getParameterObject();
//        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
//        /**存储过程直接放行，不需要数据权限直接放行*/
//        if (StatementType.CALLABLE == ms.getStatementType() || !isNeedDataAuth(ms)) {
//            return invocation.proceed();
//        }
//        /**dml处理*/
//        if (SqlCommandType.INSERT == ms.getSqlCommandType() ||
//                SqlCommandType.UPDATE == ms.getSqlCommandType() || SqlCommandType.DELETE == ms.getSqlCommandType()){
//            return dmlProcess(ms,invocation);
//        }
//        String sql = boundSql.getSql();
//        /**只处理单表查询*/
//        if (SqlCommandType.SELECT == ms.getSqlCommandType() && isSingleTable(sql)){
//            return selectProcess(metaObject,invocation,sql);
//        }
//        return invocation.proceed();
//    }
//    private String getBaseColumnList(String id){
//        return id.substring(0, id.lastIndexOf('.'))+".Base_Column_List";
//    }
//    /**
//     * 判断是否需要数据权限,
//     *  一定有律所和机构这两个字段
//     * @param ms
//     * @return
//     */
//    private boolean isNeedDataAuth(MappedStatement ms){
//        String bcKey = getBaseColumnList(ms.getId());
//        if(!ms.getConfiguration().getSqlFragments().containsKey(bcKey)){
//           return Boolean.FALSE;
//        }
//
//        XNode xNode = ms.getConfiguration().getSqlFragments().get(bcKey);
//        String stringBody = xNode.getStringBody();
//        if (Objects.isNull(stringBody)){
//            return Boolean.FALSE;
//        }
//        return (stringBody.contains(HeaderConstant.OWNER_LAW_FIRM_ID) && stringBody.contains(HeaderConstant.OWNER_OO_ID));
//    }
//
//    /**
//     * dml操作处理
//     * @param ms
//     * @param invocation
//     * @return
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    private Object dmlProcess(MappedStatement ms,Invocation invocation) throws InvocationTargetException, IllegalAccessException {
//        Integer dataOpt = LoginUserInfoUtil..getDataOpt();
//        if (Objects.nonNull(dataOpt) && HeaderConstant.OWNER_DATA_OPT_READONLY.intValue() == dataOpt.intValue()){
//            throw new NoAuthForDataOptException(ResultStatusEnum.NO_AUTH);
//        }
//        return invocation.proceed();
//    }
//
//    /**
//     * select操作处理
//     * @param metaObject
//     * @param invocation
//     * @param sql
//     * @return
//     * @throws JSQLParserException
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    private Object selectProcess(MetaObject metaObject,Invocation invocation,String sql) throws JSQLParserException, InvocationTargetException, IllegalAccessException {
//
//        Select select = (Select) CCJSqlParserUtil.parse(sql);
//        Expression oldWhereEx = ((PlainSelect) select.getSelectBody()).getWhere();
//        StringBuffer  sqlWhere  =  new StringBuffer(oldWhereEx == null ?" 1=1 " : oldWhereEx.toString());
//        switch (DataRoleEnum.of(OwnerUtil.getDataRoleKey())){
//            //所有数据
//            case ALL:
//                //查询所有，不自动加权限
//                break;
//            case DEPT:
//                //查询所有，不自动加权限
//                break;
//            case LAWFIRM:
//                append(sqlWhere,DB_OWNER_LAW_FIRM_ID,OwnerUtil.getLawFirmId());
//                break;
//            case LEADER:
//                append(sqlWhere,DB_OWNER_LAW_FIRM_ID,OwnerUtil.getLawFirmId());
//                append(sqlWhere,DB_OWNER_OO_ID,OwnerUtil.getOoId());
//                break;
//            case GROUP:
//                append(sqlWhere,DB_OWNER_LAW_FIRM_ID,OwnerUtil.getLawFirmId());
//                append(sqlWhere,DB_OWNER_OO_ID,OwnerUtil.getOoId());
//                append(sqlWhere,DB_OWNER_GROUP_ID,OwnerUtil.getGroupId());
//                break;
//            case USER:
//                append(sqlWhere,DB_OWNER_LAW_FIRM_ID,OwnerUtil.getLawFirmId());
//                append(sqlWhere,DB_OWNER_OO_ID,OwnerUtil.getOoId());
//                append(sqlWhere,DB_OWNER_GROUP_ID,OwnerUtil.getGroupId());
//                append(sqlWhere,DB_OWNER_USER_ID,OwnerUtil.getUserId());
//                break;
//            default:
//                append(sqlWhere,DB_OWNER_USER_ID,OwnerUtil.getUserId());
//                break;
//        }
//        ((PlainSelect) select.getSelectBody()).setWhere(CCJSqlParserUtil.parseCondExpression(sqlWhere.toString()));
//
//        metaObject.setValue("delegate.boundSql.sql", select.toString());
//        return invocation.proceed();
//    }
//    private void  append(StringBuffer  sqlWhere,String filed,Integer value){
//        if (Objects.isNull(value)){
//            return;
//        }
//        sqlWhere.append(" and ").append(filed).append("=").append(value);
//    }
//    private  <T> T realTarget(Object target) {
//        if (Proxy.isProxyClass(target.getClass())) {
//            MetaObject metaObject = SystemMetaObject.forObject(target);
//            return realTarget(metaObject.getValue("h.target"));
//        }
//        return (T) target;
//    }
//
//}
