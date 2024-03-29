package com.github.platform.core.schedule.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.persistence.mapper.schedule.SysJobMapper;
import com.github.platform.core.schedule.domain.common.entity.SysJobBase;
import com.github.platform.core.schedule.domain.context.SysJobContext;
import com.github.platform.core.schedule.domain.context.SysJobQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.schedule.domain.gateway.ISysJobGateway;
import com.github.platform.core.schedule.domain.constant.JobStatusEnum;
import com.github.platform.core.schedule.infra.convert.SysJobInfraConvert;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* 任务管理网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@Service
@Slf4j
public class SysJobGatewayImpl implements ISysJobGateway {

    @Resource
    private SysJobMapper sysJobMapper;
    @Resource
    private SysJobInfraConvert convert;
    @Override
    public PageBean<SysJobDto> query(SysJobQueryContext context) {
        SysJobBase record = convert.toSysJobBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysJobBase> list = sysJobMapper.findListBy(record);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysJobDto insert(SysJobContext context) {
        SysJobBase sysJobBase = convert.toSysJobBase(context);
        sysJobBase.setJobStatus(JobStatusEnum.INIT.getStatus());
        sysJobMapper.insert(sysJobBase);
        return convert.toDto(sysJobBase);
    }

    @Override
    public SysJobDto findById(Long id) {
        SysJobBase sysJobBase = sysJobMapper.findById(id);
        return convert.toDto(sysJobBase);
    }

    @Override
    public  Pair<Boolean,SysJobDto> update(SysJobContext context) {
        SysJobBase sysJobBase = convert.toSysJobBase(context);
        int i = sysJobMapper.updateById(sysJobBase);
        return Pair.of(i>0,convert.toDto(sysJobBase)) ;
    }

    @Override
    public boolean update(SysJobDto sysJobDto) {
        SysJobBase sysJobBase = convert.toSysJobBase(sysJobDto);
        return sysJobMapper.updateById(sysJobBase) > 0;
    }

    @Override
    public int delete(Long id) {
        return sysJobMapper.deleteById(id);
    }

    @Override
    public SysJobDto jobUnique(SysJobContext context) {
        log.info(JsonUtils.toJson(context));
        if (Objects.isNull(context.getId())){
            SysJobBase jobBase = SysJobBase.builder().beanName(context.getBeanName()).tenantId(context.getTenantId()).build();
            List<SysJobBase> list = sysJobMapper.findListBy(jobBase);
            if (CollectionUtil.isEmpty(list)){
                return null;
            }
            return  convert.toDto(list.get(0)) ;
        }
        SysJobBase sysJobBase  = sysJobMapper.findByBeanNameNotId(context.getId(),context.getBeanName(),context.getTenantId());
        return convert.toDto(sysJobBase);
    }
}
