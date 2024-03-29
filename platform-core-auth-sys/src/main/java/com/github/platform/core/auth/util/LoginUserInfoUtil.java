package com.github.platform.core.auth.util;

import com.github.platform.core.auth.constant.DataScopeEnum;
import com.github.platform.core.auth.context.SecurityContext;
import com.github.platform.core.auth.context.SecurityContextHolder;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.common.utils.CollectionUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 获取登录信息
 *
 * @author: yxkong
 * @date: 2023/4/3 11:04 AM
 * @version: 1.0
 */
public class LoginUserInfoUtil {
    private static SecurityContextHolder<LoginUserInfo>  loginInfo = new SecurityContextHolder<>();

    public static SecurityContext<LoginUserInfo> getContext(){
        return loginInfo.getContext();
    }

    /**
     * 只有一个角色，且是本人数据
     * @return
     */
    public static boolean isOnlyUserData(){
        if (AuthUtil.isSuperAdmin()){
            return false;
        }
        Set<DataScopeEnum> dataScopes = LoginUserInfoUtil.getLoginUserInfo().getDataScopes();
        if (CollectionUtil.isNotEmpty(dataScopes)){
            DataScopeEnum dataScopeEnum = dataScopes.stream().filter(s -> !DataScopeEnum.USER.equals(s)).findFirst().orElse(null);
            //dataScopeEnum 有值，表示有非本人权限的数据
            if (Objects.nonNull(dataScopeEnum)){
                return false;
            }
        }
        return true;
    }
    /**
     * 获取用户LoginUserInfo信息
     * @return
     */
    public static LoginUserInfo getLoginUserInfo(){
        return getContext().getLoginInfo();
    }
    public static Boolean isLogin(){
        return getContext().isLogin();
    }

    /**
     * 设置线程变量
     * 注意：  所有设置的地方注意清除，要不然容易内存泄露
     * @param loginUserInfo
     */
    public static void setLoginUserInfo(LoginUserInfo loginUserInfo){
        loginInfo.setLoginInfo(loginUserInfo);
    }

    /**
     * 清除
     */
    public static void clearContext(){
        loginInfo.clearContext();
    }

    /**
     * 是否包含角色
     * @param role
     * @return
     */
    public static boolean containsRole(String role){
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getRoleKeys().contains(role);
        }
        return false;
    }

    /**
     * 获取租户
     * @return
     */
    public static Integer getTenantId(){
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getTenantId();
        }
        return null;
    }

    /**
     * 获取登录名称
     * @return
     */
    public static String getLoginName(){
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getLoginName();
        }
        return null;
    }

    /**
     * 获取部门id
     * @return
     */
    public static Long getDeptId(){
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getDeptId();
        }
        return null;
    }

    /**
     * 获取部门名称
     * @return
     */
    public static String getDeptName(){
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getDeptName();
        }
        return null;
    }
    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getToken();
        }
        return null;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getId();
        }
        return null;
    }


    /**
     * 获取用户角色信息
     *
     * @return
     */
    public static List<Long> getUserRoleIds() {
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getRoleIds();
        }
        return null;
    }

    /**
     * 获取用户菜单权限信息
     *
     * @return
     */
    public static Set<String> getUserPerms() {
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getPerms();
        }
        return null;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public String getUserName() {
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getUserName();
        }
        return null;
    }

    /**
     * 获取用户手机号
     *
     * @return
     */
    public String getMobile() {
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getMobile();
        }
        return null;
    }
}
