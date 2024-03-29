package com.github.platform.core.sms.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.persistence.mapper.sms.SysSmsTemplateStatusMapper;
import com.github.platform.core.sms.domain.common.entity.SysSmsTemplateStatusBase;
import com.github.platform.core.sms.domain.constant.ThirdStatusEnum;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsTemplateStatusGateway;
import com.github.platform.core.sms.infra.convert.SysSmsTemplateStatusInfraConvert;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 模板厂商网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.326
* @version 1.0
*/
@Service
public class SysSmsTemplateStatusGatewayImpl implements ISysSmsTemplateStatusGateway {

    @Resource
    private SysSmsTemplateStatusMapper smsTemplateStatusMapper;
    @Resource
    private SysSmsTemplateStatusInfraConvert convert;
    @Override
    public PageBean<SysSmsTemplateStatusDto> query(SysSmsTemplateStatusQueryContext context) {
        SysSmsTemplateStatusBase smsTemplateStatusBase = convert.toSmsTemplateStatusBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysSmsTemplateStatusBase> list = smsTemplateStatusMapper.findListBy(smsTemplateStatusBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysSmsTemplateStatusDto insert(SysSmsTemplateStatusContext context) {
        SysSmsTemplateStatusBase smsTemplateStatusBase = convert.toSmsTemplateStatusBase(context);
        smsTemplateStatusMapper.insert(smsTemplateStatusBase);
        return convert.toDto(smsTemplateStatusBase);
    }

    @Override
    public SysSmsTemplateStatusDto findById(Long id) {
        SysSmsTemplateStatusBase smsTemplateStatusBase = smsTemplateStatusMapper.findById(id);
        return convert.toDto(smsTemplateStatusBase);
    }

    @Override
    public Pair<Boolean, SysSmsTemplateStatusDto> update(SysSmsTemplateStatusContext context) {
        SysSmsTemplateStatusBase smsTemplateStatusBase = convert.toSmsTemplateStatusBase(context);
        int flag = smsTemplateStatusMapper.updateById(smsTemplateStatusBase);
        return Pair.of( flag>0 , convert.toDto(smsTemplateStatusBase)) ;
    }

    @Override
    public int delete(Long id) {
        return smsTemplateStatusMapper.deleteById(id);
    }

    @Override
    public List<SysSmsTemplateStatusDto> findListByTempNo(String tempNo) {
        SysSmsTemplateStatusBase smsTemplateStatusBase = SysSmsTemplateStatusBase.builder().tempNo(tempNo)
                .status(StatusEnum.ON.getStatus())
                .signStatus(ThirdStatusEnum.SUC.getStatus())
                .tempStatus(ThirdStatusEnum.SUC.getStatus())
                .build();
        List<SysSmsTemplateStatusBase> list = smsTemplateStatusMapper.findListBy(smsTemplateStatusBase);
        return convert.toDtos(list);
    }
}
