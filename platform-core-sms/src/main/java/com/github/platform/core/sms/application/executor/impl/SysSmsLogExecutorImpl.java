package com.github.platform.core.sms.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sms.application.executor.ISysSmsLogExecutor;
import com.github.platform.core.sms.domain.context.SysSmsLogContext;
import com.github.platform.core.sms.domain.context.SmsLogQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsLogDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsLogGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* 短信日志执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-07-04 10:23:45.615
* @version 1.0
*/
@Service
@Slf4j
public class SysSmsLogExecutorImpl extends SysExecutor implements ISysSmsLogExecutor {
    @Resource
    private ISysSmsLogGateway gateway;
    @Override
    public PageBean<SysSmsLogDto> query(SmsLogQueryContext context){
        context.setTenantId(getTenantId(context));
        return gateway.query(context);
    };
    @Override
    public void insert(SysSmsLogContext context){
        context.setTenantId(getTenantId(context));
        SysSmsLogDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public SysSmsLogDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(SysSmsLogContext context) {
        context.setTenantId(getTenantId(context));
        gateway.update(context);
    }
    @Override
    public void delete(Long id) {
        int delete = gateway.delete(id);
        if (delete <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
