package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.persistence.mapper.sys.SysTokenCacheMapper;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.common.entity.SysTokenCacheBase;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
import com.github.platform.core.sys.domain.gateway.ISysTokenCacheGateway;
import com.github.platform.core.sys.infra.convert.SysTokenCacheInfraConvert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * token缓存网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
@Service
public class SysTokenCacheGatewayImpl extends BaseGatewayImpl implements ISysTokenCacheGateway {

    @Resource
    private SysTokenCacheMapper sysTokenCacheMapper;
    @Resource
    private SysTokenCacheInfraConvert sysTokenCacheConvert;
    @Override
    public PageBean<SysTokenCacheDto> query(SysTokenCacheQueryContext context) {
        SysTokenCacheBase record = sysTokenCacheConvert.toSysTokenCacheBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysTokenCacheBase> list = sysTokenCacheMapper.findListBy(record);
        return sysTokenCacheConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysTokenCacheDto insert(SysTokenCacheContext context) {
        SysTokenCacheBase record = sysTokenCacheConvert.toSysTokenCacheBase(context);
        sysTokenCacheMapper.insert(record);
        return sysTokenCacheConvert.toDto(record);
    }

    @Override
    public SysTokenCacheDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        SysTokenCacheBase record = sysTokenCacheMapper.findById(id);
        return sysTokenCacheConvert.toDto(record);
    }

    @Override
    public SysTokenCacheDto findByToken(String token) {
        //查找token，只能查比当前时间大的
        List<SysTokenCacheBase> list = sysTokenCacheMapper.findListBy(SysTokenCacheBase.builder().token(token).searchStartTime(LocalDateTimeUtil.dateTimeDefaultShort()).build());
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        return sysTokenCacheConvert.toDto(list.get(0));
    }

    @Override
    public List<SysTokenCacheDto> findByLoginName(Integer tenantId, String loginName) {
        List<SysTokenCacheBase> list = sysTokenCacheMapper.findListBy(SysTokenCacheBase.builder().tenantId(tenantId).loginName(loginName).build());
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        return sysTokenCacheConvert.toDtos(list);
    }

    @Override
    public SysTokenCacheDto update(SysTokenCacheContext context) {
        SysTokenCacheBase record = sysTokenCacheConvert.toSysTokenCacheBase(context);
        int flag = sysTokenCacheMapper.updateById(record);
        return sysTokenCacheConvert.toDto(record) ;
    }

    @Override
    public int expire(SysTokenCacheContext context) {
        //过期只是把expire_time改为当前时间减去1分钟
        context.setExpireTime(LocalDateTimeUtil.dateTime().minusMinutes(1));
        return sysTokenCacheMapper.updateById(context);
    }
}
