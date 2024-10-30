package com.github.platform.core.message.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.message.domain.common.entity.SysNoticeTemplateBase;
import com.github.platform.core.message.domain.context.SysNoticeTemplateContext;
import com.github.platform.core.message.domain.context.SysNoticeTemplateQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeTemplateGateway;
import com.github.platform.core.persistence.mapper.message.SysNoticeTemplateMapper;
import com.github.platform.core.message.infra.convert.SysNoticeTemplateInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * 消息通知模板网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-10-10 10:51:10.862
 * @version 1.0
 */
@Service
public class SysNoticeTemplateGatewayImpl extends BaseGatewayImpl implements ISysNoticeTemplateGateway {

    @Resource
    private SysNoticeTemplateMapper sysNoticeTemplateMapper;
    @Resource
    private SysNoticeTemplateInfraConvert sysNoticeTemplateConvert;
    @Override
    public PageBean<SysNoticeTemplateDto> query(SysNoticeTemplateQueryContext context) {
        SysNoticeTemplateBase record = sysNoticeTemplateConvert.toSysNoticeTemplateBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysNoticeTemplateBase> list = sysNoticeTemplateMapper.findListBy(record);
        return sysNoticeTemplateConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysNoticeTemplateDto insert(SysNoticeTemplateContext context) {
        SysNoticeTemplateBase record = sysNoticeTemplateConvert.toSysNoticeTemplateBase(context);
        sysNoticeTemplateMapper.insert(record);
        return sysNoticeTemplateConvert.toDto(record);
    }

    @Override
    public SysNoticeTemplateDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        SysNoticeTemplateBase record = sysNoticeTemplateMapper.findById(id);
        return sysNoticeTemplateConvert.toDto(record);
    }

    @Override
    public Pair<Boolean, SysNoticeTemplateDto> update(SysNoticeTemplateContext context) {
        SysNoticeTemplateBase record = sysNoticeTemplateConvert.toSysNoticeTemplateBase(context);
        int flag = sysNoticeTemplateMapper.updateById(record);
        return Pair.of( flag>0 , sysNoticeTemplateConvert.toDto(record)) ;
    }

    @Override
    public int delete(SysNoticeTemplateContext context) {
        return sysNoticeTemplateMapper.deleteById(context.getId());
    }
}
