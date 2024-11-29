package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.application.executor.IProcessConditionExecutor;
import com.github.platform.core.workflow.domain.context.ProcessConditionContext;
import com.github.platform.core.workflow.domain.context.ProcessConditionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessConditionDto;
import com.github.platform.core.workflow.domain.gateway.IProcessConditionGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
* 流程条件执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-30 18:17:25.794
* @version 1.0
*/
@Service
@Slf4j
public class ProcessConditionExecutorImpl extends SysExecutor implements IProcessConditionExecutor {
    @Resource
    private IProcessConditionGateway gateway;
    @Override
    public PageBean<ProcessConditionDto> query(ProcessConditionQueryContext context){
        context.setTenantId(getTenantId(context));
        return gateway.query(context);
    };
    @Override
    public String insert(ProcessConditionContext context){
        context.setTenantId(getTenantId(context));
        ProcessConditionDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        return record.getStrId();
    }
    @Override
    public ProcessConditionDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(ProcessConditionContext context) {
        context.setTenantId(getTenantId(context));
        Pair<Boolean, ProcessConditionDto> update = gateway.update(context);
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

    @Override
    public List<OptionsDto> getByType(String processType) {
        List<OptionsDto> rst = new ArrayList<>();
        List<ProcessConditionDto> formDtos = gateway.getByType(processType);
        if (CollectionUtil.isNotEmpty(formDtos)){
            formDtos.forEach(s->{
                rst.add(new OptionsDto(s.getExpression(),s.getProcessType(),s.getName()));
            });
        }
        return rst;
    }
}
