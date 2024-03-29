package com.github.platform.core.auth.util;

import com.github.platform.core.auth.annotation.RequiredPermissions;
import com.github.platform.core.auth.annotation.RequiredRoles;
import com.github.platform.core.auth.constant.RoleConstant;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.enums.Logical;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.exception.NoLoginException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Token 权限验证，逻辑实现类
 *
 */
public class AuthUtil {
    /**
     * 登录即可访问的
     */
    private static final Set DEFAULT_PERMISSION = Stream.of(
           "sys/auth/logout","sys/auth/info","sys/dict/findByType","sys/menu/getRouters","sys/dept/deptTree",
            "sys/auth/modifyPwd","sys/menu/menuTree","/sys/websocket").collect(Collectors.toSet());
    /**
     * 检验用户是否已经登录，如未登录，则抛出异常
     */
    public static boolean checkLogin() {
        LoginUserInfo loginUserInfo = LoginUserInfoUtil.getLoginUserInfo();
        if (Objects.isNull(loginUserInfo) || Objects.isNull(loginUserInfo.getLoginName())) {
            throw new NoLoginException("未登录或token失效，请重新登录！");
        }
        return true;
    }

    /**
     * 判断是否包含权限
     *
     * @param authorities 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    public static boolean hasPerms(Collection<String> authorities, String permission) {
        if (DEFAULT_PERMISSION.contains(permission)) {
            return Boolean.TRUE;
        }
        //permission.endsWith(x)  解决网关前缀的问题
        return authorities.stream().filter(StringUtils::isNotBlank)
                .anyMatch(x -> x.equalsIgnoreCase(permission) || permission.endsWith(x));
    }

    /**
     * 判断是否包含角色
     *
     * @param roles  角色列表
     * @param roleId 角色
     * @return 用户是否具备某角色权限
     */
    public static boolean hasRole(Collection<Long> roles, Long roleId) {
        return roles.stream().filter(Objects::nonNull)
                .anyMatch(x -> x.equals(roleId));
    }

    /**
     * 是否是管理员
     *
     * @return
     */
    public static Boolean isSuperAdmin() {
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return LoginUserInfoUtil.getUserRoleIds().contains(RoleConstant.SUPER_ROLE_ID);
        }
        return Boolean.FALSE ;
    }
    public static Boolean isAdminTenant(){
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return LoginUserInfoUtil.getUserRoleIds().contains(RoleConstant.TENANT_ROLE_ID);
        }
        return Boolean.FALSE ;
    }

    /**
     * 是否登陆状态
     *
     * @return
     */
    public static Boolean isLogin() {
        return LoginUserInfoUtil.isLogin();
    }
    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public static boolean hasPerms(String permission) {
        return hasPerms(LoginUserInfoUtil.getUserPerms(), permission);
    }

    /**
     * 验证用户是否具备某权限, 如果验证未通过，则抛出异常: NotPermissionException
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public static boolean checkPerms(String permission) {
       return hasPerms(LoginUserInfoUtil.getUserPerms(), permission);
    }

    /**
     * 根据注解(@RequiredPermissions)鉴权,
     *
     * @param requiresPermissions 注解对象
     */
    public static boolean checkPerms(RequiredPermissions requiresPermissions) {
        if (requiresPermissions.logical() == Logical.AND) {
            return checkPermsAnd(requiresPermissions.value());
        } else {
            return checkPermsOr(requiresPermissions.value());
        }
    }

    /**
     * 验证用户是否含有指定权限，必须全部拥有
     *
     * @param permissions 权限列表
     */
    public static boolean checkPermsAnd(String... permissions) {
        Set<String> permissionList = LoginUserInfoUtil.getUserPerms();
        for (String permission : permissions) {
            if (!hasPerms(permissionList, permission)) {
               return false;
            }
        }
        return true;
    }

    /**
     * 验证用户是否含有指定权限，只需包含其中一个
     *
     * @param permissions 权限码数组
     */
    public static boolean checkPermsOr(String... permissions) {
        Set<String> permissionList = LoginUserInfoUtil.getUserPerms();
        for (String permission : permissions) {
            if (hasPerms(permissionList, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param roleId 角色ID
     * @return 用户是否具备某角色
     */
    public static boolean hasRole(Long roleId) {
        return hasRole(LoginUserInfoUtil.getUserRoleIds(), roleId);
    }

    /**
     * 判断用户是否拥有某个角色, 如果验证未通过，则抛出异常: NoAuthForDataOptException
     *
     * @param roleId 角色ID
     */
    public static boolean checkRole(Long roleId) {
        return hasRole(roleId);
    }

    /**
     * 根据注解(@RequiredRoles)鉴权
     *
     * @param requiresRoles 注解对象
     */
    public static boolean checkRole(RequiredRoles requiresRoles) {
        if (requiresRoles.logical() == Logical.AND) {
            return checkRoleAnd(requiresRoles.value());
        } else {
            return checkRoleOr(requiresRoles.value());
        }
    }

    /**
     * 验证用户是否含有指定角色，必须全部拥有
     *
     * @param roleIds 角色ID数组
     */
    public static boolean checkRoleAnd(String... roleIds) {
        List<Long> roleIdList = LoginUserInfoUtil.getUserRoleIds();
        for (String role : roleIds) {
            if (!hasRole(roleIdList, Long.valueOf(role))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证用户是否含有指定角色，只需包含其中一个
     *
     * @param roleIds 角色id数组
     */
    public static boolean checkRoleOr(String... roleIds) {
        List<Long> roleList = LoginUserInfoUtil.getUserRoleIds();
        for (String role : roleIds) {
            if (hasRole(roleList, Long.valueOf(role))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据注解(@RequiredRoles)鉴权
     *
     * @param at 注解对象
     */
    public static boolean checkByAnnotation(RequiredRoles at) {
        String[] roleArray = at.value();
        if (at.logical() == Logical.AND) {
            return checkRoleAnd(roleArray);
        } else {
            return checkRoleOr(roleArray);
        }
    }

    /**
     * 根据注解(@RequiredPermissions)鉴权
     * @param at 注解对象
     */
    public static void checkByAnnotation(RequiredPermissions at) {
        String[] permissionArray = at.value();
        if (at.logical() == Logical.AND) {
            checkPermsAnd(permissionArray);
        } else {
            checkPermsOr(permissionArray);
        }
    }
}
