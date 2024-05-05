package com.github.platform.core.sys.infra.gateway.impl;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
import com.github.platform.core.sys.domain.gateway.ISysTokenCacheGateway;
import com.github.platform.core.sys.infra.convert.SysTokenCacheInfraConvert;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * token映射操作
 * @author: yxkong
 * @date: 2024/4/29 22:19
 * @version: 1.0
 */
@Service
public class TokenCacheGatewayImpl implements ITokenCacheGateway {
    @Resource
    private ISysTokenCacheGateway sysTokenCacheGateway;
    @Resource
    private SysTokenCacheInfraConvert sysTokenCacheConvert;
    @Resource
    private AuthProperties authProperties;
    @Override
    public TokenCacheEntity findByToken(String token) {
        SysTokenCacheDto tokenCacheDto = sysTokenCacheGateway.findByToken(token);
        return sysTokenCacheConvert.toEntity(tokenCacheDto);
    }

    @Override
    public List<TokenCacheEntity> findByLoginName(Integer tenantId, String loginName) {
        List<SysTokenCacheDto> list = sysTokenCacheGateway.findByLoginName(tenantId, loginName);
        return sysTokenCacheConvert.toEntityList(list);
    }

    @Override
    public TokenCacheEntity saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo ,boolean isLogin) {
        long seconds = TimeoutUtils.toSeconds(authProperties.getSys().getExpire(), TimeUnit.MINUTES);
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        SysTokenCacheDto cacheDto =  sysTokenCacheGateway.findByToken(token);
        SysTokenCacheContext context = SysTokenCacheContext.builder()
                .token(token)
                .loginName(loginName)
                .loginInfo(loginInfo)
                .expireTime(LocalDateTimeUtil.plusSecond(seconds))
                .build();
        if (Objects.nonNull(userInfo)){
            if (isLogin){
                context.setLastLoginTime(LocalDateTimeUtil.parseDefaultShort(userInfo.getLoginTime()));
            }
            context.setUpdateTime(LocalDateTimeUtil.dateTime());
        }
        SysTokenCacheDto tokenCacheDto =  null;
        if (Objects.isNull(cacheDto)){
            context.setCreateBy(loginName);
            context.setCreateTime(LocalDateTimeUtil.dateTime());
            tokenCacheDto = sysTokenCacheGateway.insert(context);
        } else {
            context.setId(cacheDto.getId());
            tokenCacheDto = sysTokenCacheGateway.update(context);
        }
        return sysTokenCacheConvert.toEntity(tokenCacheDto);
    }

    @Override
    public void expireByToken( String token) {
        SysTokenCacheDto cacheDto =  sysTokenCacheGateway.findByToken(token);
        sysTokenCacheGateway.expire(toExpireContext(cacheDto));
    }
    private SysTokenCacheContext toExpireContext(SysTokenCacheDto dto){
        return SysTokenCacheContext.builder()
                .id(dto.getId())
                .token(dto.getToken())
                .loginName(dto.getLoginName())
                .tenantId(dto.getTenantId())
                .expireTime(LocalDateTimeUtil.dateTime())
                .build();
    }

    @Override
    public void expireByLoginName(Integer tenantId, String loginName) {
        List<SysTokenCacheDto> list = sysTokenCacheGateway.findByLoginName(tenantId, loginName);
        if (CollectionUtil.isEmpty(list)){
            return;
        }
        list.forEach(s->{
            sysTokenCacheGateway.expire(toExpireContext(s));
        });

    }
}
