package com.github.platform.core.schedule.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.schedule.SysJobLogMapper;
import com.github.platform.core.schedule.domain.common.entity.SysJobLogBase;
import com.github.platform.core.schedule.domain.context.SysJobLogContext;
import com.github.platform.core.schedule.domain.context.SysJobLogQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobLogDto;
import com.github.platform.core.schedule.domain.gateway.ISysJobLogGateway;
import com.github.platform.core.schedule.infra.convert.SysJobLogInfraConvert;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
* 任务执行日志网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
@Service
public class SysJobLogGatewayImpl implements ISysJobLogGateway {
    @Resource
    private SysJobLogMapper sysJobLogMapper;
    @Resource
    private SysJobLogInfraConvert convert;
    @Override
    public PageBean<SysJobLogDto> query(SysJobLogQueryContext context) {
        SysJobLogBase record = convert.toSysJobLogBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysJobLogBase> list = null;
        if (StringUtils.isNotEmpty(context.getExecuteId())){
            //查询子任务的执行记录
            list = sysJobLogMapper.findListSub(record);
        } else {
            list =  sysJobLogMapper.findListBy(record);
        }
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysJobLogDto insert(SysJobLogContext context) {
        sysJobLogMapper.insert(context);
        return convert.toDto(context);
    }

    @Override
    public SysJobLogDto findById(Long id) {
        SysJobLogBase sysJobLogBase = sysJobLogMapper.findById(id);
        return convert.toDto(sysJobLogBase);
    }
    @Override
    @Async("asyncEventExecutor")
    public int updateAsync(SysJobLogContext context) {
        return sysJobLogMapper.updateById(context);
    }

    @Override
    public int delete(Long id) {
        return sysJobLogMapper.deleteById(id);
    }
}
