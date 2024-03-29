package com.github.platform.core.auth.util;

import com.github.platform.core.auth.context.SecurityContext;
import com.github.platform.core.auth.context.SecurityContextHolder;
import com.github.platform.core.standard.entity.common.LoginInfo;

import java.util.Objects;

/**
 * 登录信息工具类
 * @author: yxkong
 * @date: 2023/4/20 7:28 PM
 * @version: 1.0
 */
public class LoginInfoUtil {
    private static SecurityContextHolder<LoginInfo> contextHolder = new SecurityContextHolder<>();

    public static SecurityContext<LoginInfo> getContext(){
        return contextHolder.getContext();
    }
    /**
     * 获取用户LoginInfo信息
     * @return
     */
    public static LoginInfo getLoginInfo(){
        return getContext().getLoginInfo();
    }
    public static Boolean isLogin(){
        return getContext().isLogin();
    }
    /**
     * 设置线程变量
     * 注意：  所有设置的地方注意清除，要不然容易内存泄露
     * @param loginInfo
     */
    public static void setLoginInfo(LoginInfo loginInfo){
        contextHolder.setLoginInfo(loginInfo);
    }

    /**
     * 清除
     */
    public static void clearContext(){
        contextHolder.clearContext();
    }
    /**
     * 获取租户
     * @return
     */
    public static Integer getTenantId(){
        LoginInfo userInfo = LoginInfoUtil.getLoginInfo();
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
        LoginInfo userInfo = LoginInfoUtil.getLoginInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getLoginName();
        }
        return null;
    }
    public static String getToken() {
        LoginInfo userInfo = LoginInfoUtil.getLoginInfo();
        if(Objects.nonNull(userInfo)){
            return userInfo.getToken();
        }
        return null;
    }

}
