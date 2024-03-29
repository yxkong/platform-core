package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.DeleteEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.workflow.domain.common.entity.ProcessDefinitionBase;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionContext;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessDefinitionDto;
import com.github.platform.core.workflow.domain.gateway.IProcessDefinitionGateway;
import com.github.platform.core.persistence.mapper.workflow.ProcessDefinitionMapper;
import com.github.platform.core.workflow.infra.convert.ProcessManageInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* 流程管理网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@Service
public class ProcessDefinitionGatewayImpl extends BaseGatewayImpl implements IProcessDefinitionGateway {

    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;
    @Resource
    private ProcessManageInfraConvert convert;
    @Override
    public PageBean<ProcessDefinitionDto> query(ProcessDefinitionQueryContext context) {
        ProcessDefinitionBase processDefinition = convert.toProcessDefinitionBase(context);
        if (Objects.isNull(context.getDeleted())){
            processDefinition.setDeleted(DeleteEnum.NO.getStatus());
        }
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<ProcessDefinitionBase> list = processDefinitionMapper.findPageBy(processDefinition);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public PageBean<ProcessDefinitionDto> queryHistory(ProcessDefinitionQueryContext context) {
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        ProcessDefinitionBase processDefinition = ProcessDefinitionBase.builder().processNo(context.getProcessNo()).build();
        List<ProcessDefinitionBase> list = processDefinitionMapper.findListBy(processDefinition);
        List<ProcessDefinitionBase> collect = list.stream().filter(s -> !s.getId().equals(context.getId())).collect(Collectors.toList());
        return convert.ofPageBean(new PageInfo<>(collect));
    }

    @Override
    public ProcessDefinitionDto insert(ProcessDefinitionContext context) {
        ProcessDefinitionBase processDefinition = convert.toProcessDefinitionBase(context);
        if (Objects.isNull(processDefinition.getProcessVersion())){
            processDefinition.setProcessVersion(1);
        }
        processDefinitionMapper.insert(processDefinition);
        return convert.toDto(processDefinition);
    }

    @Override
    public ProcessDefinitionDto findByProcessNo(String processNo, Integer status, Integer tenantId) {
        ProcessDefinitionBase processManageBase = ProcessDefinitionBase.builder().processNo(processNo).status(status).tenantId(tenantId).build();
        List<ProcessDefinitionBase> list = processDefinitionMapper.findListBy(processManageBase);
        if (CollectionUtil.isNotEmpty(list)){
            return convert.toDto(list.get(0));
        }
        return null;
    }
    @Override
    public ProcessDefinitionDto findByProcessNo(String processNo, Integer version) {
        ProcessDefinitionBase processManageBase = ProcessDefinitionBase.builder().processNo(processNo).processVersion(version).build();
        List<ProcessDefinitionBase> list = processDefinitionMapper.findListBy(processManageBase);
        if (CollectionUtil.isNotEmpty(list)){
            return convert.toDto(list.get(0));
        }
        return findByProcessNo(processNo,version,null);
    }


    @Override
    public ProcessDefinitionDto findLatestByProcessNo(String processNo) {
        return findByProcessNo(processNo,null);
    }

    @Override
    public ProcessDefinitionDto findById(Long id) {
        ProcessDefinitionBase processDefinition = processDefinitionMapper.findById(id);
        return convert.toDto(processDefinition);
    }

    @Override
    public Pair<Boolean, ProcessDefinitionDto> update(ProcessDefinitionContext context) {
        ProcessDefinitionBase processDefinition = convert.toProcessDefinitionBase(context);
        int flag = processDefinitionMapper.updateById(processDefinition);
        return Pair.of( flag>0 , convert.toDto(processDefinition)) ;
    }

    @Override
    public int delete(Long id) {
        return processDefinitionMapper.deleteById(id);
    }




    @Override
    public List<ProcessDefinitionDto> findByType(Integer tenantId, String processType) {
        ProcessDefinitionBase processManageBase = ProcessDefinitionBase.builder().tenantId(tenantId).processType(processType).status(StatusEnum.ON.getStatus()).build();
        List<ProcessDefinitionBase> list = processDefinitionMapper.findListBy(processManageBase);
        return convert.toDtos(list);
    }
}
