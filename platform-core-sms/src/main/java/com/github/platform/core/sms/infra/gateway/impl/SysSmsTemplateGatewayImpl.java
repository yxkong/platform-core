package com.github.platform.core.sms.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.persistence.mapper.sms.SysSmsTemplateMapper;
import com.github.platform.core.sms.domain.common.entity.SysSmsTemplateBase;
import com.github.platform.core.sms.domain.context.SysSmsTemplateContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsTemplateGateway;
import com.github.platform.core.sms.infra.convert.SysSmsTemplateInfraConvert;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 短信模板网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.124
* @version 1.0
*/
@Service
public class SysSmsTemplateGatewayImpl implements ISysSmsTemplateGateway {

    @Resource
    private SysSmsTemplateMapper smsTemplateMapper;
    @Resource
    private SysSmsTemplateInfraConvert convert;
    @Override
    public PageBean<SysSmsTemplateDto> query(SysSmsTemplateQueryContext context) {
        SysSmsTemplateBase smsTemplateBase = convert.toSmsTemplateBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysSmsTemplateBase> list = smsTemplateMapper.findListBy(smsTemplateBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysSmsTemplateDto insert(SysSmsTemplateContext context) {
        SysSmsTemplateBase smsTemplateBase = convert.toSmsTemplateBase(context);
        smsTemplateMapper.insert(smsTemplateBase);
        return convert.toDto(smsTemplateBase);
    }

    @Override
    public SysSmsTemplateDto findById(Long id) {
        SysSmsTemplateBase smsTemplateBase = smsTemplateMapper.findById(id);
        return convert.toDto(smsTemplateBase);
    }

    @Override
    public Pair<Boolean, SysSmsTemplateDto> update(SysSmsTemplateContext context) {
        SysSmsTemplateBase smsTemplateBase = convert.toSmsTemplateBase(context);
        int flag = smsTemplateMapper.updateById(smsTemplateBase);
        return Pair.of( flag>0 , convert.toDto(smsTemplateBase)) ;
    }

    @Override
    public int delete(Long id) {
        return  smsTemplateMapper.deleteById(id);
    }

    @Override
    public SysSmsTemplateDto findByTempNo(String tempNo) {
        SysSmsTemplateBase record = smsTemplateMapper.findByTempNo(tempNo);
        return convert.toDto(record);
    }
}
