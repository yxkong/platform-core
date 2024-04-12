package com.github.platform.core.auth.constant;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public interface  RoleConstant {
    /**
     * 内置超级管理员角色id
     */
    Long SUPER_ROLE_ID = 1L;
    String SUPER_ROLE_KEY = "admin";

    /**
     * 内置租户管理员角色id
     */
    Long TENANT_ADMIN_ROLE_ID = 2L;
    /**
     * 内置租户管理员角色key
     */
    String TENANT_ADMIN_ROLE_KEY = "tenantAdmin";
    /**三方角色*/
    Long THIRD_ROLE_ID = 3L;
    String THIRD_ROLE = "3";
    /**
     * 内置三方默认角色
     */
    String THIRD_DEFAULT_ROLE = "thirdDefault";
    /**
     * 所有权限
     */
    String ALL_PERMS = "*/*/*";

    /**
     * 所有内置角色
     */
    List<String> innerRoles = Lists.newArrayList(SUPER_ROLE_KEY, TENANT_ADMIN_ROLE_KEY,THIRD_DEFAULT_ROLE);
    /**
     * 三方角色
     */
    Set<String> thirdRole = Sets.newHashSet(THIRD_DEFAULT_ROLE);
    /**
     * 超级管理员角色
     */
    List<String> superRole = Lists.newArrayList(SUPER_ROLE_KEY);
    /**
     * 租户角色
     */
    List<String> tenantAdminRole = Lists.newArrayList(TENANT_ADMIN_ROLE_KEY);

}
