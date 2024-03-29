//package com.github.platform.core.sys.infra.service;
//
//import com.github.platform.core.auth.configuration.properties.AuthProperties;
//import com.github.platform.core.auth.entity.LoginUserInfo;
//import com.github.platform.core.cache.infra.service.ICacheService;
//import com.github.platform.core.auth.service.impl.DefaultTokenServiceImpl;
//import com.github.platform.core.auth.service.ILoginTokenService;
//import com.github.platform.core.common.utils.JsonUtils;
//import com.github.platform.core.common.utils.StringUtils;
//import org.springframework.data.redis.core.TimeoutUtils;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * LoginToken服务实现
// *
// * @author: yxkong
// * @date: 2023/1/5 1:41 PM
// * @version: 1.0
// */
//@Service
//public class LoginTokenServiceImpl extends DefaultTokenServiceImpl implements ILoginTokenService {
//
//
//
//    public LoginTokenServiceImpl(AuthProperties authProperties, ICacheService cacheService) {
//        super(authRedisProperties,cacheService);
//    }
//
//    @Override
//    public boolean cacheUserInfo(String token, LoginUserInfo loginUserInfo) {
//        AuthRedisProperties.Login login = authRedisProperties.getLogin();
//        //缓存用户信息 token:userInfo
//        String cacheKey = this.getCacheKey(login.getToken(),token);
//        cacheService.set(cacheKey, JsonUtils.toJson(loginUserInfo), TimeoutUtils.toSeconds(login.getExpire(), TimeUnit.MINUTES));
//
//        //缓存用户名和token关系 loginName:token
//        cacheService.set(login.getUserTokenMapping()+loginUserInfo.getLoginName(), token, TimeoutUtils.toSeconds(login.getExpire(), TimeUnit.MINUTES));
//        return true;
//    }
//
//    /**
//     * 删除用户登陆信息
//     *
//     * @param loginName
//     */
//    @Override
//    public void delUserInfoCache(String loginName,String token) {
//        AuthRedisProperties.Login login = authRedisProperties.getLogin();
//        //取出token后 删除用户名和token关系 loginName:token
//        String cacheKey = login.getUserTokenMapping()+loginName;
//        if (StringUtils.isEmpty(token)){
//            token = (String)cacheService.get(cacheKey);
//        }
//        //删除用户上次token的用户信息 token:userInfo
//        cacheService.del(cacheKey,this.getCacheKey(login.getToken(),token));
//    }
//}
