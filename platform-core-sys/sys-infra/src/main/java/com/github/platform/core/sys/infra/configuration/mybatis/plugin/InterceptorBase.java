package com.github.platform.core.sys.infra.configuration.mybatis.plugin;

import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 通用更新处理
 *
 * @author: yxkong
 * @date: 2022/6/6 9:57 AM
 * @version: 1.0
 */
@Slf4j
public abstract class InterceptorBase implements Interceptor {
    protected static final String CREATE_TIME = "createTime";
    protected static final String DB_CREATE_TIME = "create_time";
    protected static final String UPDATE_TIME = "updateTime";
    protected static final String DB_UPDATE_TIME = "update_time";
    protected static final String CREATE_BY = "createBy";
    protected static final String DB_CREATE_BY = "create_by";
    protected static final String UPDATE_BY = "updateBy";
    protected static final String DB_UPDATE_BY = "update_by";
    protected static final String TENANT_ID = "";
    protected static final String DB_TENANT_ID = "";
    protected static final String DEPT_ID = "deptId";
    protected static final String DB_DEPT_ID = "dept_id";
    protected void put(Map map,String key,Object val){
        if (!map.containsKey(key)){
            map.put(key,val);
        }
    }
    protected void fillInsert(Object paramObj) throws IllegalAccessException, InvocationTargetException{
        LocalDateTime date = LocalDateTimeUtil.dateTime();
        String loginName = null;
        if (AuthUtil.isLogin()) {
            loginName = LoginUserInfoUtil.getLoginName();
        }
        if (paramObj instanceof Map) {
            Map<String,Object> paramMap = (Map<String,Object>) paramObj;
            paramMap.putIfAbsent(CREATE_TIME, date);
            paramMap.putIfAbsent(UPDATE_TIME, date);
            if (Objects.nonNull(loginName)){
                paramMap.putIfAbsent(CREATE_BY,loginName);
                paramMap.putIfAbsent(TENANT_ID, LoginUserInfoUtil.getTenantId());
                paramMap.putIfAbsent(DEPT_ID, LoginUserInfoUtil.getDeptId());
            }
        } else {
            fillProperty(paramObj, CREATE_TIME, date);
            fillProperty(paramObj, UPDATE_TIME, date);
            if (Objects.nonNull(loginName)){
                fillProperty(paramObj,CREATE_BY,loginName);
                fillProperty(paramObj,TENANT_ID, LoginUserInfoUtil.getTenantId());
                fillProperty(paramObj,DEPT_ID, LoginUserInfoUtil.getDeptId());
            }
        }
    }
    protected void fillUpdate(Object paramObj) throws IllegalAccessException, InvocationTargetException{
        LocalDateTime date = LocalDateTimeUtil.dateTime();
        String loginName = null;
        if (AuthUtil.isLogin()) {
            loginName = LoginUserInfoUtil.getLoginName();
        }
        if (paramObj instanceof Map) {
            Map<String,Object> paramMap = (Map<String,Object>) paramObj;
            paramMap.putIfAbsent(UPDATE_TIME, date);
            if (Objects.nonNull(loginName)){
                paramMap.putIfAbsent(UPDATE_BY,loginName);
            }
        } else {
            fillField(paramObj, UPDATE_TIME, date);
            if (Objects.nonNull(loginName)){
                fillField(paramObj,UPDATE_BY,loginName);
            }
        }
    }
    /**
     * 填充字段(map填充或者类属性填充)
     * @param paramObj
     * @param property
     * @param value
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    protected void fillField(Object paramObj, String property, Object value) throws IllegalAccessException, InvocationTargetException {
        if (paramObj instanceof Map) {
            Map<String,Object> originParamMap = (Map<String,Object>) paramObj;
            originParamMap.putIfAbsent(property, value);
        } else {
            fillProperty(paramObj, property, value);
        }
    }

    /**
     * 填充属性
     * @param parameterObject 参数对象
     * @param property 属性
     * @param value 值
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

    /**
     * 是否单表查询
     * @param sql
     * @return
     * @throws JSQLParserException
     */
    protected boolean isSingleSelectTable(String sql) throws JSQLParserException {
        Select select = (Select) CCJSqlParserUtil.parse(sql);

        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        Set<String> tableList = tablesNamesFinder.getTables((Statement) select);
        return tableList.size() == 1;
    }
    protected boolean isSingleUpdateTable(String sql) throws JSQLParserException {
        Update update = (Update) CCJSqlParserUtil.parse(sql);

        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        Set<String> tableList = tablesNamesFinder.getTables(update);
        return tableList.size() == 1;
    }

    protected boolean isSingleTable(String sql, SqlCommandType commandType) throws JSQLParserException {
        if (commandType == SqlCommandType.INSERT){
            return isSingleInsertTable(sql);
        } else if (commandType == SqlCommandType.UPDATE){
            return isSingleUpdateTable(sql);
        }
        return false;
    }

    private boolean isSingleInsertTable(String sql) throws JSQLParserException {
        Insert insert = (Insert) CCJSqlParserUtil.parse(sql);

        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(insert);
        return tableList.size() == 1;
    }

}
