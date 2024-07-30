package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.application.executor.ISysDictTypeExecutor;
import com.github.platform.core.sys.domain.context.SysDictTypeContext;
import com.github.platform.core.sys.domain.context.SysDictTypeQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictTypeDto;
import com.github.platform.core.sys.domain.gateway.ISysDictGateway;
import com.github.platform.core.sys.domain.gateway.ISysDictTypeGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 字典类型执行器
 * @Author: yxkong
 * @Date: 2024/1/28 12:42
 * @version: 1.0
 */
@Service
@Slf4j
public class SysDictTypeExecutorImpl extends BaseExecutor implements ISysDictTypeExecutor {
    @Resource
    private ISysDictTypeGateway dictTypeGateway;
    @Resource
    private ISysDictGateway dictGateway;

    @Override
    public PageBean<SysDictTypeDto> query(SysDictTypeQueryContext context) {
        return dictTypeGateway.query(context);
    }
    @Override
    public void insert(SysDictTypeContext context) {
        dictTypeGateway.insert(context);
    }

    @Override
    public void update(SysDictTypeContext context) {
        dictTypeGateway.update(context);
    }
    @Override
    public void delete(Long id) {
        SysDictTypeDto dto = dictTypeGateway.findById(id);
        if (Objects.isNull(dto)){
            return;
        }
        dictTypeGateway.delete(SysDictTypeContext.builder().id(id).type(dto.getType()).build());
    }
    @Override
    public void reload(String type) {
        if (StringUtils.isNotEmpty(type)){
            dictTypeGateway.deleteCache(type);
            dictGateway.findByType(type);
            return;
        }
        List<SysDictTypeDto> list = dictTypeGateway.findListBy(null);
        if (CollectionUtil.isEmpty(list)){
            return;
        }
        dictTypeGateway.deleteAllCache();
        list.forEach(s->{
            dictTypeGateway.deleteCache(s.getType());
            dictGateway.findByType(s.getType());
        });

    }
}
