package com.github.platform.core.gateway.infra.gateway.impl;

import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.gateway.domain.gateway.TokenParseGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 获取token
 *
 * @author: yxkong
 * @date: 2021/12/6 11:45 AM
 * @version: 1.0
 */
@Service
@Slf4j
public class TokenParseGatewayImpl implements TokenParseGateway {
    @Resource
    private ICacheService cacheService;
    @Override
    public String getLoginToken(String token) {
        //将token从redis中解析出来
        String loginInfoStr = null;
        try {
            loginInfoStr = (String) cacheService.get(token);
        } catch (Exception e) {
            log.error("获取用户token:{},的结果：{} 异常！",token,loginInfoStr,e);
        }
        // 不考虑从数据库里获取token信息
        return loginInfoStr;
    }
}
