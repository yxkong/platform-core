package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.workflow.application.executor.IProcessApprovalRecordExecutor;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordContext;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessApprovalRecordDto;
import com.github.platform.core.workflow.domain.gateway.IProcessApprovalRecordGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;
/**
* 流程审批记录执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-09 11:30:36.194
* @version 1.0
*/
@Service
@Slf4j
public class ProcessApprovalRecordExecutorImpl extends SysExecutor implements IProcessApprovalRecordExecutor {
    @Resource
    private IProcessApprovalRecordGateway gateway;
    @Override
    public PageBean<ProcessApprovalRecordDto> query(ProcessApprovalRecordQueryContext context){
        context.setTenantId(getTenantId(context));
        return gateway.query(context);
    };
    @Override
    public void insert(ProcessApprovalRecordContext context){
        context.setTenantId(getTenantId(context));
        ProcessApprovalRecordDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public ProcessApprovalRecordDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(ProcessApprovalRecordContext context) {
        context.setTenantId(getTenantId(context));
        Pair<Boolean, ProcessApprovalRecordDto> update = gateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    @Override
    public void delete(Long id) {
        int d = gateway.delete(id);
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
