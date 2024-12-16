package com.github.platform.core.monitor.infra.gateway.impl;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.monitor.domain.common.entity.SysOptLogBase;
import com.github.platform.core.monitor.domain.context.SysOptLogContext;
import com.github.platform.core.monitor.domain.context.SysOptLogQueryContext;
import com.github.platform.core.monitor.domain.dto.SysOptLogDto;
import com.github.platform.core.monitor.domain.gateway.ISysOptLogGateway;
import com.github.platform.core.monitor.infra.convert.SysOptLogInfraConvert;
import com.github.platform.core.monitor.infra.service.ISysOptLogService;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* 操作日志网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Service
public class SysOptLogGatewayImpl implements ISysOptLogGateway {

    @Resource
    private ISysOptLogService sysOptLogService;
    @Resource
    private SysOptLogInfraConvert convert;
    @Override
    public PageBean<SysOptLogDto> query(SysOptLogQueryContext context) {
        SysOptLogBase sysOptLogBase = convert.toSysOptLogBase(context);
        PageInfo<SysOptLogBase> pageInfo = sysOptLogService.findPageInfo(sysOptLogBase, context.getPageNum(), context.getPageSize());
        return convert.ofPageBean(pageInfo);
    }

    @Override
    public Pair<Boolean, String> insert(SysOptLogContext context) {
        SysOptLogBase sysOptLogBase = convert.toSysOptLogBase(context);
        boolean flag = sysOptLogService.insert(sysOptLogBase);
        return flag ? Pair.of(true, "新增成功！") : Pair.of(false, "新增失败！");
    }

    @Override
    public SysOptLogDto findById(Long id) {
        SysOptLogBase sysOptLogBase = sysOptLogService.findById(id);
        return convert.toDto(sysOptLogBase);
    }

    @Override
    public Pair<Boolean, String> update(SysOptLogContext context) {
        SysOptLogBase sysOptLogBase = convert.toSysOptLogBase(context);
        boolean flag = sysOptLogService.updateById(sysOptLogBase);
       return flag ? Pair.of(true, "修改成功！") : Pair.of(false, "修改失败！");
    }

    @Override
    public Pair<Boolean, String> delete(Long id) {
        boolean flag = sysOptLogService.deleteById(id);
        return flag ? Pair.of(true, "删除成功！") : Pair.of(false, "删除失败！");
    }
}
