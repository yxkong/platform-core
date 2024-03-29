package com.github.platform.core.sms.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sms.application.executor.ISysSmsTemplateStatusExecutor;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsTemplateStatusGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* 模板厂商执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.326
* @version 1.0
*/
@Service
@Slf4j
public class SysSmsTemplateStatusExecutorImpl extends BaseExecutor implements ISysSmsTemplateStatusExecutor {
    @Resource
    private ISysSmsTemplateStatusGateway gateway;
    @Override
    public PageBean<SysSmsTemplateStatusDto> query(SysSmsTemplateStatusQueryContext context){
        return gateway.query(context);
    };
    @Override
    public String insert(SysSmsTemplateStatusContext context){
        SysSmsTemplateStatusDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        return record.getStrId();
    }
    @Override
    public SysSmsTemplateStatusDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(SysSmsTemplateStatusContext context) {
        context.setProNo(null);
        context.setTempNo(null);
        Pair<Boolean, SysSmsTemplateStatusDto> update = gateway.update(context);
        if (!update.getKey()){
            exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    @Override
    public void delete(Long id) {
        int delete = gateway.delete(id);
        if (delete <=0 ){
            exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
