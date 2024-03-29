package com.github.platform.core.auth.context;


import com.github.platform.core.standard.entity.common.LoginInfo;

/**
 * @author: yxkong
 * @date: 2021/6/3 11:32 上午
 * @version: 1.0
 */
public class SecurityContextHolder<T extends LoginInfo> {
    private static SecurityContextHolderStrategy strategy;

    static {
        initialize();
    }

    public void clearContext() {
        strategy.clearContext();
    }

    public SecurityContext getContext() {
        return strategy.getContext();
    }


    private static void initialize() {
        try {
            strategy = new ThreadLocalSecurityContextHolderStrategy();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setContext(SecurityContext context) {
        strategy.setContext(context);
    }
    public void setLoginInfo(T loginInfo) {
        strategy.setContext(new SecurityContextImpl<>(loginInfo));
    }
}