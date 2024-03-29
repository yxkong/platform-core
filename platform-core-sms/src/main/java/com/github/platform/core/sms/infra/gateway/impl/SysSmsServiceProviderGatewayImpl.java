package com.github.platform.core.sms.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.persistence.mapper.sms.SysSmsServiceProviderMapper;
import com.github.platform.core.sms.domain.common.entity.SysSmsServiceProviderBase;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderContext;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsServiceProviderDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsServiceProviderGateway;
import com.github.platform.core.sms.infra.convert.SysSmsServiceProviderInfraConvert;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 服务商网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@Service
public class SysSmsServiceProviderGatewayImpl implements ISysSmsServiceProviderGateway {
    @Resource
    private SysSmsServiceProviderMapper serviceProviderMapper;
    @Resource
    private SysSmsServiceProviderInfraConvert convert;
    @Override
    public PageBean<SysSmsServiceProviderDto> query(SysSmsServiceProviderQueryContext context) {
        SysSmsServiceProviderBase smsServiceProviderBase = convert.toSmsServiceProviderBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysSmsServiceProviderBase> list = serviceProviderMapper.findListBy(smsServiceProviderBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysSmsServiceProviderDto insert(SysSmsServiceProviderContext context) {
        SysSmsServiceProviderBase smsServiceProviderBase = convert.toSmsServiceProviderBase(context);
        serviceProviderMapper.insert(smsServiceProviderBase);
        return convert.toDto(smsServiceProviderBase);
    }

    @Override
    public SysSmsServiceProviderDto findById(Long id) {
        SysSmsServiceProviderBase smsServiceProviderBase = serviceProviderMapper.findById(id);
        return convert.toDto(smsServiceProviderBase);
    }

    @Override
    public Pair<Boolean, SysSmsServiceProviderDto> update(SysSmsServiceProviderContext context) {
        SysSmsServiceProviderBase smsServiceProviderBase = convert.toSmsServiceProviderBase(context);
        int flag =  serviceProviderMapper.updateById(smsServiceProviderBase);
        return Pair.of( flag>0 , convert.toDto(smsServiceProviderBase)) ;
    }

    @Override
    public int delete(Long id) {
       return serviceProviderMapper.deleteById(id);
    }

    @Override
    public List<SysSmsServiceProviderDto> findAll() {
        SysSmsServiceProviderBase record = SysSmsServiceProviderBase.builder().status(StatusEnum.ON.getStatus()).build();
        List<SysSmsServiceProviderBase> list = serviceProviderMapper.findListBy(record);
        return convert.toDtos(list);
    }

    @Override
    public SysSmsServiceProviderDto findByProNo(String proNo) {
        SysSmsServiceProviderBase sms = serviceProviderMapper.findByProNo(proNo);
        return convert.toDto(sms);
    }
}
