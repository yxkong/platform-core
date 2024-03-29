package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.workflow.domain.common.entity.ProcessConditionBase;
import com.github.platform.core.workflow.domain.context.ProcessConditionContext;
import com.github.platform.core.workflow.domain.context.ProcessConditionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessConditionDto;
import com.github.platform.core.workflow.domain.gateway.IProcessConditionGateway;
import com.github.platform.core.persistence.mapper.workflow.ProcessConditionMapper;
import com.github.platform.core.workflow.infra.convert.ProcessConditionInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
/**
* 流程条件网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-30 18:17:25.794
* @version 1.0
*/
@Service
public class ProcessConditionGatewayImpl extends BaseGatewayImpl implements IProcessConditionGateway {

    @Resource
    private ProcessConditionMapper processConditionMapper;
    @Resource
    private ProcessConditionInfraConvert convert;
    @Override
    public PageBean<ProcessConditionDto> query(ProcessConditionQueryContext context) {
        ProcessConditionBase processConditionBase = convert.toProcessConditionBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<ProcessConditionBase> list = processConditionMapper.findListBy(processConditionBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public ProcessConditionDto insert(ProcessConditionContext context) {
        ProcessConditionBase processConditionBase = convert.toProcessConditionBase(context);
        processConditionMapper.insert(processConditionBase);
        return convert.toDto(processConditionBase);
    }

    @Override
    public ProcessConditionDto findById(Long id) {
        ProcessConditionBase processConditionBase = processConditionMapper.findById(id);
        return convert.toDto(processConditionBase);
    }

    @Override
    public Pair<Boolean, ProcessConditionDto> update(ProcessConditionContext context) {
        ProcessConditionBase processConditionBase = convert.toProcessConditionBase(context);
        int flag = processConditionMapper.updateById(processConditionBase);
        return Pair.of( flag>0 , convert.toDto(processConditionBase)) ;
    }

    @Override
    public int delete(Long id) {
        return processConditionMapper.deleteById(id);
    }

    @Override
    public List<ProcessConditionDto> getByType(String processType) {
        ProcessConditionBase conditionBase = ProcessConditionBase.builder().processType(processType).build();
        List<ProcessConditionBase> list = processConditionMapper.findListBy(conditionBase);
        return convert.toDtos(list);
    }
}
