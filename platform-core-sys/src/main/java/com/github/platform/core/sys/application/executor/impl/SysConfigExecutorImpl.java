package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
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
public class SysConfigExecutorImpl extends BaseExecutor implements ISysConfigExecutor {
    @Resource
    private ISysConfigGateway gateway;
    @Override
    public PageBean<SysConfigDto> query(SysConfigQueryContext context) {
        return gateway.query(context);
    }

    @Override
    public void insert(SysConfigContext context) {
        gateway.insert(context);
    }
    @Override
    public void update(SysConfigContext context) {
        gateway.update(context);
    }

    @Override
    public void delete(Long id) {
        SysConfigDto dto = gateway.findById(id);
        if (Objects.isNull(dto)){
            return;
        }
        gateway.delete(id, dto.getKey());
    }

    @Override
    public SysConfigDto getConfig(String key) {
        return gateway.getConfig(key);
    }

    @Override
    public void reload(String key) {
        if (StringUtils.isNotEmpty(key)){
            gateway.deleteCache(key);
            gateway.getConfig(key);
        }
        List<SysConfigDto> list = gateway.findListBy(null);
        if (CollectionUtil.isEmpty(list)){
            return;
        }
        list.forEach(s->{
            gateway.deleteCache(s.getKey());
            gateway.getConfig(s.getKey());
        });
    }
}
