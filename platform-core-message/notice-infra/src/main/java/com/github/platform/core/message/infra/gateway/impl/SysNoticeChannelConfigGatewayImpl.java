package com.github.platform.core.message.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.message.domain.common.entity.SysNoticeChannelConfigBase;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigContext;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeChannelConfigGateway;
import com.github.platform.core.persistence.mapper.message.SysNoticeChannelConfigMapper;
import com.github.platform.core.message.infra.convert.SysNoticeChannelConfigInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * 通知通道配置网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
@Service
public class SysNoticeChannelConfigGatewayImpl extends BaseGatewayImpl implements ISysNoticeChannelConfigGateway {

    @Resource
    private SysNoticeChannelConfigMapper sysNoticeChannelConfigMapper;
    @Resource
    private SysNoticeChannelConfigInfraConvert sysNoticeChannelConfigConvert;
    @Override
    public PageBean<SysNoticeChannelConfigDto> query(SysNoticeChannelConfigQueryContext context) {
        SysNoticeChannelConfigBase record = sysNoticeChannelConfigConvert.toSysNoticeChannelConfigBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysNoticeChannelConfigBase> list = sysNoticeChannelConfigMapper.findListBy(record);
        return sysNoticeChannelConfigConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysNoticeChannelConfigDto insert(SysNoticeChannelConfigContext context) {
        SysNoticeChannelConfigBase record = sysNoticeChannelConfigConvert.toSysNoticeChannelConfigBase(context);
        sysNoticeChannelConfigMapper.insert(record);
        return sysNoticeChannelConfigConvert.toDto(record);
    }

    @Override
    public SysNoticeChannelConfigDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        SysNoticeChannelConfigBase record = sysNoticeChannelConfigMapper.findById(id);
        return sysNoticeChannelConfigConvert.toDto(record);
    }
    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON + #tenantId + ':' + #channelType", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public SysNoticeChannelConfigDto findChannel(String channelType,Integer tenantId) {
        SysNoticeChannelConfigBase record = sysNoticeChannelConfigMapper.findChannel(channelType,tenantId);
        return sysNoticeChannelConfigConvert.toDto(record);
    }
    @Override
    @Caching(
        evict = {
            @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + #context.tenantId+':'+#context.channelType",cacheManager = CacheConstant.cacheManager),
        }
    )
    public Pair<Boolean, SysNoticeChannelConfigDto> update(SysNoticeChannelConfigContext context) {
        SysNoticeChannelConfigBase record = sysNoticeChannelConfigConvert.toSysNoticeChannelConfigBase(context);
        int flag = sysNoticeChannelConfigMapper.updateById(record);
        return Pair.of( flag>0 , sysNoticeChannelConfigConvert.toDto(record)) ;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + #context.tenantId+':'+#context.channelType",cacheManager = CacheConstant.cacheManager),
            }
    )
    public int delete(SysNoticeChannelConfigContext context) {
        return sysNoticeChannelConfigMapper.deleteById(context.getId());
    }


}
