//package com.github.platform.core.auth.service;
//
//import com.alibaba.fastjson.JSON;
//import com.github.platform.core.cache.infra.service.ICacheService;
//import com.github.platform.core.common.utils.StringUtils;
//import com.github.platform.core.standard.entity.common.LoginInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.TimeoutUtils;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 获取token
// *
// * @author: yxkong
// * @date: 2021/12/6 11:45 AM
// * @version: 1.0
// */
//
//@Slf4j
//public class DefaultTokenServiceImpl implements ITokenService {
//
//    protected AuthRedisProperties authRedisProperties;
//    protected ICacheService cacheService;
//
//    public DefaultTokenServiceImpl(AuthRedisProperties authRedisProperties,ICacheService cacheService) {
//        this.authRedisProperties = authRedisProperties;
//        this.cacheService = cacheService;
//    }
//
//    /**
//     * 获取 LoginUserInfo
//     *
//     * @param token
//     * @return LoginToken
//     */
//    @Override
//    public LoginInfo getLoginInfo(String token) {
//        AuthRedisProperties.Login login = authRedisProperties.getLogin();
//        String prefix = login.getToken();
//        //将token从redis中解析出来
//        String loginInfoStr = null;
//        try {
//            loginInfoStr = (String)cacheService.get(getCacheKey(prefix,token));
//        } catch (Exception e) {
//            log.error("获取用户token:{},的结果异常！", token, e);
//        }
//        LoginInfo loginInfo = JSON.parseObject(loginInfoStr, LoginInfo.class);
//        // 续期，不仅要续token，还需要续
//        if (StringUtils.isNotBlank(loginInfoStr)) {
//            cacheService.expire(getCacheKey(prefix,token), TimeoutUtils.toSeconds(authRedisProperties.getLogin().getExpire(), TimeUnit.MINUTES));
//            cacheService.expire(getCacheKey(login.getUserTokenMapping(),loginInfo.getLoginName()), TimeoutUtils.toSeconds(authRedisProperties.getLogin().getExpire(), TimeUnit.MINUTES));
//        }
//        // 不考虑从数据库里获取token信息
//        return loginInfo;
//    }
//}
