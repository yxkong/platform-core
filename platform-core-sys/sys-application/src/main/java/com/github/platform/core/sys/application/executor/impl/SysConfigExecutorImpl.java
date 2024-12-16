package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.application.executor.ISysConfigExecutor;
import com.github.platform.core.sys.domain.context.SysConfigContext;
import com.github.platform.core.sys.domain.context.SysConfigQueryContext;
import com.github.platform.core.sys.domain.dto.SysConfigDto;
import com.github.platform.core.sys.domain.gateway.ISysConfigGateway;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 配置执行器
 * @author: yxkong
 * @date: 2023/4/19 3:51 PM
 * @version: 1.0
 */
@Service
public class SysConfigExecutorImpl extends SysExecutor implements ISysConfigExecutor {
    @Resource
    private ISysConfigGateway gateway;
    @Override
    public PageBean<SysConfigDto> query(SysConfigQueryContext context) {
        context.setTenantId(getTenantId(context));
        return gateway.query(context);
    }

    @Override
    public void insert(SysConfigContext context) {
        context.setTenantId(getTenantId(context));
        gateway.insert(context);
    }
    @Override
    public void update(SysConfigContext context) {
        context.setTenantId(getTenantId(context));
        gateway.update(context);
    }

    @Override
    public void delete(Long id) {
        SysConfigDto dto = gateway.findById(id);
        if (Objects.isNull(dto)){
            return;
        }
        gateway.delete(id, dto.getTenantId(),dto.getKey());
    }

    @Override
    public SysConfigDto getConfig(SysConfigQueryContext context) {
        context.setTenantId(getTenantId(context));
        return gateway.getConfig(context.getTenantId(),context.getKey());
    }

    @Override
    public void reload(SysConfigQueryContext context) {
        String key = context.getKey();
        Integer tenantId = getTenantId(context);
        if (StringUtils.isNotEmpty(key)){
            gateway.deleteCache(tenantId,key);
            gateway.getConfig(tenantId,key);
        }
        List<SysConfigDto> list = gateway.findListBy(null);
        if (CollectionUtil.isEmpty(list)){
            return;
        }
        list.forEach(s->{
            gateway.deleteCache(s.getTenantId(),s.getKey());
            gateway.getConfig(s.getTenantId(),s.getKey());
        });
    }
}
