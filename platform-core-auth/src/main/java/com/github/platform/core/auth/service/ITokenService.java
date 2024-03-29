package com.github.platform.core.auth.service;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.constants.AuthTypeEnum;

import java.util.List;

/**
 * token服务，由各个服务实现
 *
 * @author: yxkong
 * @date: 2022/11/24 6:40 下午
 * @version: 1.0
 */
public interface ITokenService {
    AuthProperties getAuthProperties();
    default AuthProperties.Login getLogin(AuthTypeEnum authType){
        if (AuthTypeEnum.API.equals(authType)){
            return getAuthProperties().getApi().getLogin();
        } else if (AuthTypeEnum.SYS.equals(authType)){
            return getAuthProperties().getSys().getLogin();
        }
        return null;
    }
    /**
     * 获取token缓存的key
     * @param token
     * @return
     */
    default String getTokenKey(AuthTypeEnum authType, String token) {
        return getLogin(authType).getToken() + token;
    }
    /**
     * 获取登录名和token映射的缓存key
     * @param authType
     * @param loginName
     * @return
     */
    default String getMappingKey(AuthTypeEnum authType, String loginName) {
        return getLogin(authType).getUserTokenMapping() + loginName;
    }
    /**
     * 根据loginName获取token
     * @param authType
     * @param loginName
     * @return
     */
    String getTokenByLoginName(AuthTypeEnum authType,String loginName);
    /**
     * 获取登录信息,会续租
     *
     * @param token
     * @return
     */
    String getLoginInfoStr(AuthTypeEnum authType, String token);

    /**
     * 根据loginName获取登录信息
     * @param authType
     * @param loginName
     * @return
     */
    String getLoginInfoStrByLoginName(AuthTypeEnum authType, String loginName);

    /**
     * 批量获取用户
     * @param authType
     * @param tokens
     * @return
     */
    List<String> getList(AuthTypeEnum authType, List<String> tokens);



}
