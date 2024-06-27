package com.github.platform.core.sys.infra.gateway.impl;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
import com.github.platform.core.sys.domain.gateway.ISysTokenCacheGateway;
import com.github.platform.core.sys.infra.convert.SysTokenCacheInfraConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
@Slf4j
public class TokenCacheGatewayImpl implements ITokenCacheGateway {
    @Resource
    private ISysTokenCacheGateway sysTokenCacheGateway;
    @Resource
    private SysTokenCacheInfraConvert sysTokenCacheConvert;
    @Resource
    private AuthProperties authProperties;
    private  long getSeconds(){
        return TimeoutUtils.toSeconds(authProperties.getSys().getExpire(), TimeUnit.MINUTES);
    }
    @Override
    public TokenCacheEntity findByToken(String token) {
        //查询缓存或数据库
        SysTokenCacheDto tokenCacheDto = sysTokenCacheGateway.findByToken(token);
        if (Objects.isNull(tokenCacheDto)){
            return null;
        }
        return sysTokenCacheConvert.toEntity(tokenCacheDto);
    }

    @Override
    public TokenCacheEntity findByLoginName(Integer tenantId, String loginName) {

        SysTokenCacheDto record = sysTokenCacheGateway.findByLoginName(tenantId, loginName);
        return sysTokenCacheConvert.toEntity(record);
    }

    @Override
    public TokenCacheEntity saveOrUpdate(Integer tenantId, String token, String loginName, String loginInfo ,boolean isLogin) {
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        if (!AuthUtil.checkLogin()){
            log.warn("未检测到登录信息，不做缓存，token:{} loginName:{} loginInfo:{}",token,loginName,loginInfo);
        }
        if (StringUtils.isEmpty(loginName)){
            loginName = userInfo.getLoginName();
        }
        SysTokenCacheDto cacheDto =  sysTokenCacheGateway.findByToken(token);
        LocalDateTime localDateTime = LocalDateTimeUtil.dateTime();
        SysTokenCacheContext context = SysTokenCacheContext.builder()
                .token(token)
                .loginName(loginName)
                .loginInfo(loginInfo)
                .expireTime(localDateTime.plusSeconds( getSeconds()))
                .build();
        if (Objects.nonNull(userInfo)){
            if (isLogin){
                context.setLastLoginTime(LocalDateTimeUtil.parseDefault(userInfo.getLoginTime()));
            }
            context.setUpdateTime(localDateTime);
        }
        SysTokenCacheDto tokenCacheDto =  null;
        if (Objects.isNull(cacheDto)){
            context.setCreateBy(loginName);
            context.setCreateTime(localDateTime);
            context.setLoginWay(userInfo.getLoginWay());
            context.setTenantId(userInfo.getTenantId());
            tokenCacheDto = sysTokenCacheGateway.insert(context);
        } else {
            context.setId(cacheDto.getId());
            context.setRemark("last:"+cacheDto.getExpireTime());
            tokenCacheDto  = sysTokenCacheGateway.update(context);
        }
        return sysTokenCacheConvert.toEntity(tokenCacheDto);
    }

    @Override
    public void expireByToken( String token) {
        SysTokenCacheDto cacheDto =  sysTokenCacheGateway.findByToken(token);
        if (Objects.isNull(cacheDto)){
            return;
        }
        sysTokenCacheGateway.expire(toExpireContext(cacheDto));
    }

    @Override
    public void expireById(Long id){
        SysTokenCacheDto cacheDto = sysTokenCacheGateway.findById(id);
        if (Objects.isNull(cacheDto)){
            return;
        }
        sysTokenCacheGateway.expire(toExpireContext(cacheDto));

    }

    private SysTokenCacheContext toExpireContext(SysTokenCacheDto dto){
        return SysTokenCacheContext.builder()
                .id(dto.getId())
                .token(dto.getToken())
                .loginName(dto.getLoginName())
                .tenantId(dto.getTenantId())
                .status(StatusEnum.OFF.getStatus())
                .build();
    }

    @Override
    public void expireByLoginName(Integer tenantId, String loginName) {
        //查询所有有效的token，并过期，这里不能使用批量更新，因为要操作缓存
        SysTokenCacheQueryContext queryContext = SysTokenCacheQueryContext.builder().tenantId(tenantId).loginName(loginName).searchStartTime(LocalDateTimeUtil.dateTimeDefaultShort()).build();
        List<SysTokenCacheDto> list = sysTokenCacheGateway.findListBy(queryContext);

        if (CollectionUtil.isEmpty(list)){
            return;
        }
        list.forEach(s->{
            sysTokenCacheGateway.expire(toExpireContext(s));
        });
    }
}
