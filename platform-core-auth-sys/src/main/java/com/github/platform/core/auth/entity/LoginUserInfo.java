package com.github.platform.core.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.auth.constant.DataScopeEnum;
import com.github.platform.core.auth.constant.RoleConstant;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.common.LoginInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 基础用户信息<br>
 * 如果是通过网关调用服务的，此文件更新以后，需要重新部署网关，否则字段在网关里没有，设置到header中的属性缺失
 *
 * @author yxkong
 * @create 2023/2/9 下午2:15
 * @desc UserInfo
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo extends LoginInfo {

    /**邮箱*/
    private String email;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 接口密钥
     */
    private String secretKey;
    /**
     * 部门名称
     */
    @Schema(description ="部门名称")
    private String deptName;

    /**
     * 部门ID
     */
    @Schema(description ="部门ID")
    private Long deptId;

    /**
     * 角色列表
     */
    @Schema(description ="角色列表id")
    private List<Long> roleIds = new ArrayList<>();
    @Schema(description ="角色列表key")
    private List<String> roleKeys = new ArrayList<>();
    @Schema(description ="角色名称列表")
    private List<String> roleNames = new ArrayList<>();

    /**
     * 菜单权限列表
     */
    @Schema(description ="菜单权限列表")
    private Set<String> perms = new HashSet<>();

    /**
     * 数据权限列表
     */
    @Schema(description ="数据权限列表")
    private Set<DataScopeEnum> dataScopes = new HashSet<>();

    /**
     * 所在部门以及子部门
     */
    @Schema(description ="所在部门以及子部门")
    private Set<Long> deptIds = new HashSet<>();

    @JsonIgnore
    public String getWebSocketKey(){
        return this.loginName + SymbolConstant.colon + this.token;
    }
    /**
     * 是否超级管理员
     * @return
     */
    public boolean isSuperAdmin(){
        if (CollectionUtil.isEmpty(roleKeys)){
            return false;
        }
        return roleKeys.contains(RoleConstant.SUPER_ROLE_KEY);
    }

    /**
     * 是否租户管理员
     * @return
     */
    public boolean isTenantAdmin(){
        if (CollectionUtil.isEmpty(roleKeys)){
            return false;
        }
        return roleKeys.contains(RoleConstant.TENANT_ADMIN_ROLE_KEY);
    }

}
