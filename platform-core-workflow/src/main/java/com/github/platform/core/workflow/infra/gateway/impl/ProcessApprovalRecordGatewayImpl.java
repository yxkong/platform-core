package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.workflow.domain.common.entity.ProcessApprovalRecordBase;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordContext;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessApprovalRecordDto;
import com.github.platform.core.workflow.domain.gateway.IProcessApprovalRecordGateway;
import com.github.platform.core.persistence.mapper.workflow.ProcessApprovalRecordMapper;
import com.github.platform.core.workflow.infra.convert.ProcessApprovalRecordInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
* 流程审批记录网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-09 11:30:36.194
* @version 1.0
*/
@Service
public class ProcessApprovalRecordGatewayImpl extends BaseGatewayImpl implements IProcessApprovalRecordGateway {

    @Resource
    private ProcessApprovalRecordMapper processApprovalRecordMapper;
    @Resource
    private ProcessApprovalRecordInfraConvert convert;
    @Override
    public PageBean<ProcessApprovalRecordDto> query(ProcessApprovalRecordQueryContext context) {
        ProcessApprovalRecordBase processApprovalRecordBase = convert.toProcessApprovalRecordBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<ProcessApprovalRecordBase> list = processApprovalRecordMapper.findListBy(processApprovalRecordBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public ProcessApprovalRecordDto insert(ProcessApprovalRecordContext context) {
        ProcessApprovalRecordBase processApprovalRecordBase = convert.toProcessApprovalRecordBase(context);
        processApprovalRecordMapper.insert(processApprovalRecordBase);
        return convert.toDto(processApprovalRecordBase);
    }

    @Override
    public ProcessApprovalRecordDto findById(Long id) {
        ProcessApprovalRecordBase processApprovalRecordBase = processApprovalRecordMapper.findById(id);
        return convert.toDto(processApprovalRecordBase);
    }

    @Override
    public Pair<Boolean, ProcessApprovalRecordDto> update(ProcessApprovalRecordContext context) {
        ProcessApprovalRecordBase processApprovalRecordBase = convert.toProcessApprovalRecordBase(context);
        int flag = processApprovalRecordMapper.updateById(processApprovalRecordBase);
        return Pair.of( flag>0 , convert.toDto(processApprovalRecordBase)) ;
    }

    @Override
    public int delete(Long id) {
        return processApprovalRecordMapper.deleteById(id);
    }

    @Override
    public ProcessApprovalRecordDto findByUserInstanceNoAndTaskId(String assignee, String instanceNo, String taskId) {
        return processApprovalRecordMapper.findByUserInstanceNoAndTaskId(assignee,instanceNo,taskId);
    }

    @Override
    public List<ProcessApprovalRecordDto> findByInstanceId(@NotNull  String instanceId) {
        ProcessApprovalRecordBase processApprovalRecordBase = ProcessApprovalRecordBase.builder().instanceId(instanceId).build();
        List<ProcessApprovalRecordBase> list = processApprovalRecordMapper.findListBy(processApprovalRecordBase);
        return convert.toDtos(list);
    }
}
