package com.github.platform.core.auth.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.constants.AuthTypeEnum;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.auth.util.LoginInfoUtil;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.TimeoutUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 获取token
 *
 * @author: yxkong
 * @date: 2021/12/6 11:45 AM
 * @version: 1.0
 */
@Slf4j
public class DefaultTokenServiceImpl implements ITokenService {

    protected AuthProperties authProperties;
    protected ICacheService cacheService;

    public DefaultTokenServiceImpl(AuthProperties authProperties,ICacheService cacheService) {
        this.authProperties = authProperties;
        this.cacheService = cacheService;
    }

    @Override
    public AuthProperties getAuthProperties() {
        return this.authProperties;
    }

    protected Long getExpire(AuthTypeEnum authType){
        AuthProperties.Login login = this.getLogin(authType);
        if (Objects.isNull(login) || Objects.isNull(login.getExpire())){
            return CacheConstant.defaultExpire;
        }
        return login.getExpire();
    }

    /**
     * 续期
     * @param authType
     * @param token
     * @param loginName
     */
    protected void renewal(AuthTypeEnum authType,String token,String loginName){
        long seconds = TimeoutUtils.toSeconds(getExpire(authType), TimeUnit.MINUTES);
        cacheService.expire(getTokenKey(authType,token), seconds);
        if (StringUtils.isNotEmpty(loginName)){
            cacheService.expire(getMappingKey(authType,loginName), seconds);
        }
        if (log.isDebugEnabled()){
            log.debug("loginName:{},token:{} 续期{}秒成功！",loginName,token,seconds);
        }
    }

    @Override
    public String getTokenByLoginName(AuthTypeEnum authType, String loginName) {
        return  (String)cacheService.get(getMappingKey(authType,loginName));
    }
    private String getTokenKeyByLoginName(AuthTypeEnum authType, String loginName){
        String token = getTokenByLoginName(authType, loginName);
        return getTokenKey(authType,token);
    }

    @Override
    public String getLoginInfoStr(AuthTypeEnum authType,String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        //将token从redis中解析出来
        String loginInfoStr = null;
        try {
            loginInfoStr = (String)cacheService.get(getTokenKey(authType,token));
            if (StringUtils.isNotEmpty(loginInfoStr)){
                /** 续期，不仅要续token，还需要续loginName,大部分情况是没有LoginInfoUtil的*/
                String loginName = LoginInfoUtil.getLoginName();
                if (StringUtils.isEmpty(loginName)){
                    JSONObject json = JSONObject.parseObject(loginInfoStr);
                    loginName = json.getString("loginName");
                }
                if (StringUtils.isEmpty(loginName)){
                    log.info("未找到loginName, 缓存信息为：{}",loginInfoStr);
                }
                renewal(authType,token,loginName );
            }
        } catch (Exception e) {
            log.error("获取用户token:{},的结果异常！", token, e);
        }
        return loginInfoStr;
    }

    @Override
    public String getLoginInfoStrByLoginName(AuthTypeEnum authType, String loginName) {
        String token =  getTokenByLoginName(authType, loginName);
        return getLoginInfoStr(authType,token);
    }

    @Override
    public List<String> getList(AuthTypeEnum authType, List<String> tokens) {
        return cacheService.multiGet(tokens);
    }
    //    /**
//     * 获取 LoginUserInfo
//     *
//     * @param token
//     * @return LoginToken
//     */
//    @Override
//    public LoginUserInfo getLoginUserInfo(String token) {
//        AuthRedisProperties.Login login = authRedisProperties.getLogin();
//        String prefix =CacheKeyUtil.tokenPre();
//        log.info(prefix);
//        //将token从redis中解析出来
//        String loginInfoStr = null;
//        try {
//            loginInfoStr = (String)cacheService.get(getCacheKey(prefix,token));
//        } catch (Exception e) {
//            log.error("获取用户token:{},的结果异常！", token, e);
//        }
//        LoginUserInfo loginUserInfo = JSON.parseObject(loginInfoStr, LoginUserInfo.class);
//        if (Objects.nonNull(loginUserInfo) && Objects.isNull(loginUserInfo.getPerms()) ){
//            loginUserInfo.setPerms(new HashSet<>());
//        }
//        // 续期，不仅要续token，还需要续
//        if (StringUtils.isNotBlank(loginInfoStr) && SecurityContextHolder.getContext().isLogin()) {
//            cacheService.expire(getCacheKey(prefix,token), TimeoutUtils.toSeconds(login.getExpire(), TimeUnit.MINUTES));
//            cacheService.expire(getCacheKey(CacheKeyUtil.userTokenMappingPre(),loginUserInfo.getLoginName()), TimeoutUtils.toSeconds(login.getExpire(), TimeUnit.MINUTES));
//        }
//        // 不考虑从数据库里获取token信息
//        return loginUserInfo;
//    }
}
