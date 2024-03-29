package com.github.platform.core.sms.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.persistence.mapper.sms.SysSmsLogMapper;
import com.github.platform.core.sms.domain.common.entity.SysSmsLogBase;
import com.github.platform.core.sms.domain.context.SysSmsLogContext;
import com.github.platform.core.sms.domain.context.SmsLogQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsLogDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsLogGateway;
import com.github.platform.core.sms.infra.convert.SysSmsLogInfraConvert;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 短信日志网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-07-04 10:23:45.615
* @version 1.0
*/
@Service
public class SysSmsLogGatewayImpl implements ISysSmsLogGateway {

    @Resource(name = "sysSmsLogMapper")
    private SysSmsLogMapper sysSmsLogMapper;
    @Resource
    private SysSmsLogInfraConvert smsLogInfraConvert;
    @Override
    public PageBean<SysSmsLogDto> query(SmsLogQueryContext context) {
        SysSmsLogBase smsLog = smsLogInfraConvert.toSmsLogBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysSmsLogBase> list = sysSmsLogMapper.findListBy(smsLog);
        return smsLogInfraConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysSmsLogDto insert(SysSmsLogContext context) {
        SysSmsLogBase smsLogDO = smsLogInfraConvert.toSmsLogBase(context);
        sysSmsLogMapper.insert(smsLogDO);
        return smsLogInfraConvert.toDto(smsLogDO);
    }

    @Override
    public SysSmsLogDto findById(Long id) {
        SysSmsLogBase smsLogDO= sysSmsLogMapper.findById(id);
        return smsLogInfraConvert.toDto(smsLogDO);
    }

    @Override
    public SysSmsLogDto update(SysSmsLogContext context) {
        SysSmsLogBase smsLogDO = smsLogInfraConvert.toSmsLogBase(context);
        sysSmsLogMapper.updateById(smsLogDO);
        return smsLogInfraConvert.toDto(smsLogDO) ;
    }

    @Override
    public int delete(Long id) {
        return sysSmsLogMapper.deleteById(id);
    }

    @Override
    public SysSmsLogDto findByMsgId(String msgId) {
        SysSmsLogBase record = sysSmsLogMapper.findByMsgId(msgId);
        return smsLogInfraConvert.toDto(record);
    }
}
