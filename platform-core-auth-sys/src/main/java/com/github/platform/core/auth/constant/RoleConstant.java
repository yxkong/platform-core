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
    String SUPER_ROLE = "1";
    /**
     * 内置租户管理员角色id
     */
    Long TENANT_ROLE_ID = 2L;
    String TENANT_ROLE = "2";
    /**三方角色*/
    Long THIRD_ROLE_ID = 3L;
    String THIRD_ROLE = "3";
    /**
     * 所有权限
     */
    String ALL_PERMS = "*/*/*";

    /**
     * 所有内置角色
     */
    List<Long> innerRoles = Lists.newArrayList(SUPER_ROLE_ID, TENANT_ROLE_ID,THIRD_ROLE_ID);
    /**
     * 三方角色
     */
    Set<Long> thirdRole = Sets.newHashSet(THIRD_ROLE_ID);
    /**
     * 超级管理员角色
     */
    List<Long> superRole = Lists.newArrayList(SUPER_ROLE_ID);
    /**
     * 租户角色
     */
    List<Long> tenantRole = Lists.newArrayList(TENANT_ROLE_ID);

}
