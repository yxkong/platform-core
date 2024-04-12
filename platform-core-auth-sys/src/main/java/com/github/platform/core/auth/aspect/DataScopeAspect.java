package com.github.platform.core.auth.aspect;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.entity.BaseAdminEntity;
import com.github.platform.core.standard.entity.BaseDO;
import com.google.common.base.Joiner;
import com.github.platform.core.auth.annotation.DataScope;
import com.github.platform.core.auth.constant.DataScopeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import com.github.platform.core.common.utils.CollectionUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * 数据过滤处理
 *
 * @author wangxiaozhou
 */
@Slf4j
@Aspect
@ConditionalOnWebApplication(type = SERVLET)
@Component
public class DataScopeAspect {

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Before(value = "@annotation(dataScope)")
    public void doBefore(JoinPoint point, DataScope dataScope) {
        Object params = point.getArgs()[0];
        if (Objects.isNull(params)) {
            return;
        }
        baseAdminEntity(dataScope, params);
    }

    private void baseAdminEntity(DataScope dataScope, Object params) {
        if (!(params instanceof BaseAdminEntity)){
            return;
        }
        BaseAdminEntity entity = (BaseAdminEntity) params;
        // 获取当前的用户
        LoginUserInfo loginUser = LoginUserInfoUtil.getLoginUserInfo();
        if (AuthUtil.isSuperAdmin()) {
            entity.setDataScope("");
            log.info("系统管理员,数据权限为所有:{}", loginUser.getLoginName());
            return;
        }
        StringBuilder sqlString = new StringBuilder();
        Set<DataScopeEnum> scopes = loginUser.getDataScopes();
        scopes = Objects.isNull(scopes) ? new HashSet<>() : scopes;
        Set<Long> deptIds = loginUser.getDeptIds();
        String tableAlias = dataScope.tableAlias();
        if (log.isDebugEnabled()){
            log.debug("数据权限上下文:loginName={},scope={},deptIds={}", loginUser.getLoginName(), scopes, deptIds);
        }

        if (scopes.contains(DataScopeEnum.TENANT)) {
            sqlString.append(String.format(" OR %s.tenant_id = '%s' ", tableAlias, loginUser.getTenantId()));
        } else {
            if (scopes.contains(DataScopeEnum.USER)) {
                sqlString.append(String.format(" OR (%s.tenant_id = '%s' AND %s.create_by = '%s')", tableAlias, loginUser.getTenantId(), tableAlias, loginUser.getLoginName()));
            }

            if (scopes.contains(DataScopeEnum.DEPT)) {
                sqlString.append(String.format(" OR (%s.tenant_id = '%s' AND %s.dept_id = %s)", tableAlias, loginUser.getTenantId(), tableAlias, loginUser.getDeptId()));
            }

            if (scopes.contains(DataScopeEnum.DEPT_DOWN)) {
                sqlString.append(String.format(" OR (%s.tenant_id = '%s' AND %s.dept_id in(%s))", tableAlias, loginUser.getTenantId(), tableAlias, Joiner.on(",").join(deptIds)));
            }
        }

        if (CollectionUtil.isEmpty(scopes)) {
            log.warn("当前用户数据权限标记异常,默认只查本人创建的数据:{}", loginUser.getLoginName());
            sqlString.append(String.format(" OR (%s.tenant_id = '%s' AND %s.create_by = '%s')", tableAlias, loginUser.getTenantId(), tableAlias, loginUser.getLoginName()));
        }

        String scopeSql = " AND (" + sqlString.substring(4) + ")";
        entity.setDataScope(scopeSql);
        log.info("数据权限生成sql:{}", scopeSql);
    }

}
