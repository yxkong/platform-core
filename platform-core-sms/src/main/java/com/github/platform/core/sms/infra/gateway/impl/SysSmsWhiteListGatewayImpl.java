package com.github.platform.core.sms.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.persistence.mapper.sms.SysSmsWhiteListMapper;
import com.github.platform.core.sms.domain.common.entity.SysSmsWhiteListBase;
import com.github.platform.core.sms.domain.context.SysSmsWhiteListContext;
import com.github.platform.core.sms.domain.context.SysSmsWhiteListQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsWhiteListDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsWhiteListGateway;
import com.github.platform.core.sms.infra.convert.SysSmsWhiteListInfraConvert;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* 短信白名单网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@Service
public class SysSmsWhiteListGatewayImpl implements ISysSmsWhiteListGateway {

    @Resource
    private SysSmsWhiteListMapper smsWhiteListMapper;
    @Resource
    private SysSmsWhiteListInfraConvert convert;
    @Override
    public PageBean<SysSmsWhiteListDto> query(SysSmsWhiteListQueryContext context) {
        SysSmsWhiteListBase smsWhiteListBase = convert.toSmsWhiteListBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysSmsWhiteListBase> list = smsWhiteListMapper.findListBy(smsWhiteListBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysSmsWhiteListDto insert(SysSmsWhiteListContext context) {
        SysSmsWhiteListBase smsWhiteListBase = convert.toSmsWhiteListBase(context);
        smsWhiteListMapper.insert(smsWhiteListBase);
        return convert.toDto(smsWhiteListBase);
    }

    @Override
    public SysSmsWhiteListDto findById(Long id) {
        SysSmsWhiteListBase smsWhiteListBase = smsWhiteListMapper.findById(id);
        return convert.toDto(smsWhiteListBase);
    }

    @Override
    public Pair<Boolean, SysSmsWhiteListDto> update(SysSmsWhiteListContext context) {
        SysSmsWhiteListBase smsWhiteListBase = convert.toSmsWhiteListBase(context);
        int flag = smsWhiteListMapper.updateById(smsWhiteListBase);
        return Pair.of( flag>0 , convert.toDto(smsWhiteListBase)) ;
    }

    @Override
    public int delete(Long id) {
        return smsWhiteListMapper.deleteById(id);
    }
    public boolean existMobile(String mobile){
        SysSmsWhiteListBase record = smsWhiteListMapper.findByMobile(mobile);
        if (Objects.nonNull(record) && record.getStatus().equals(StatusEnum.ON.getStatus())){
            return true;
        }
        return false;
    }
}
