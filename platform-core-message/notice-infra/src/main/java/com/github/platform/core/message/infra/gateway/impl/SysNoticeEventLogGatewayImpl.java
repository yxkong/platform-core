package com.github.platform.core.message.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.message.domain.common.entity.SysNoticeEventLogBase;
import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.context.SysNoticeEventLogQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeEventLogDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeEventLogGateway;
import com.github.platform.core.persistence.mapper.message.SysNoticeEventLogMapper;
import com.github.platform.core.message.infra.convert.SysNoticeEventLogInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * 通知事件日志网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
@Service
public class SysNoticeEventLogGatewayImpl extends BaseGatewayImpl implements ISysNoticeEventLogGateway {

    @Resource
    private SysNoticeEventLogMapper sysNoticeEventLogMapper;
    @Resource
    private SysNoticeEventLogInfraConvert sysNoticeEventLogConvert;
    @Override
    public PageBean<SysNoticeEventLogDto> query(SysNoticeEventLogQueryContext context) {
        SysNoticeEventLogBase record = sysNoticeEventLogConvert.toSysNoticeEventLogBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysNoticeEventLogBase> list = sysNoticeEventLogMapper.findListBy(record);
        return sysNoticeEventLogConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysNoticeEventLogDto insert(SysNoticeEventLogContext context) {
        SysNoticeEventLogBase record = sysNoticeEventLogConvert.toSysNoticeEventLogBase(context);
        sysNoticeEventLogMapper.insert(record);
        return sysNoticeEventLogConvert.toDto(record);
    }

    @Override
    public SysNoticeEventLogDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        SysNoticeEventLogBase record = sysNoticeEventLogMapper.findById(id);
        return sysNoticeEventLogConvert.toDto(record);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON + #msgId", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public SysNoticeEventLogDto findByMsgId(String msgId) {
        if (Objects.isNull(msgId)){
            return null;
        }
        SysNoticeEventLogBase record = sysNoticeEventLogMapper.findByMsgId(msgId);
        return sysNoticeEventLogConvert.toDto(record);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + #context.msgId",cacheManager = CacheConstant.cacheManager)
    public Pair<Boolean, SysNoticeEventLogDto> update(SysNoticeEventLogContext context) {
        SysNoticeEventLogBase record = sysNoticeEventLogConvert.toSysNoticeEventLogBase(context);
        int flag = sysNoticeEventLogMapper.updateById(record);
        return Pair.of( flag>0 , sysNoticeEventLogConvert.toDto(record)) ;
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + #context.msgId",cacheManager = CacheConstant.cacheManager)
    public int delete(SysNoticeEventLogContext context) {
        return sysNoticeEventLogMapper.deleteById(context.getId());
    }
}
