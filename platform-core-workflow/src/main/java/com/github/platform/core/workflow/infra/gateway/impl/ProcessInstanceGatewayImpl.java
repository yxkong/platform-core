package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.workflow.domain.common.entity.ProcessInstanceBase;
import com.github.platform.core.workflow.domain.constant.InstanceStatusEnum;
import com.github.platform.core.workflow.domain.context.ProcessInstanceContext;
import com.github.platform.core.workflow.domain.context.ProcessInstanceQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import com.github.platform.core.workflow.domain.gateway.IProcessInstanceGateway;
import com.github.platform.core.workflow.infra.convert.ProcessInstanceInfraConvert;
import com.github.platform.core.persistence.mapper.workflow.ProcessInstanceMapper;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
/**
* 流程实例网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Service
public class ProcessInstanceGatewayImpl extends BaseGatewayImpl implements IProcessInstanceGateway {

    @Resource
    private ProcessInstanceMapper flwProcessInstanceMapper;
    @Resource
    private ProcessInstanceInfraConvert convert;
    @Override
    public PageBean<ProcessInstanceDto> query(ProcessInstanceQueryContext context) {
        ProcessInstanceBase flwProcessInstanceBase = convert.toFlwProcessInstanceBase(context);
        //TODO 非管理员，需要根据自己的数据权限查询，需要统一处理
//        if (!AuthUtil.isSuperAdmin()){
//            flwProcessInstanceBase.setCreateBy(LoginUserInfoUtil.getLoginName());
//        }
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<ProcessInstanceBase> list = flwProcessInstanceMapper.findListBy(flwProcessInstanceBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public ProcessInstanceDto insert(ProcessInstanceContext context) {
        ProcessInstanceBase flwProcessInstanceBase = convert.toFlwProcessInstanceBase(context);
        flwProcessInstanceMapper.insert(flwProcessInstanceBase);
        return convert.toDto(flwProcessInstanceBase);
    }

    @Override
    public ProcessInstanceDto findById(Long id) {
        ProcessInstanceBase flwProcessInstanceBase = flwProcessInstanceMapper.findById(id);
        return convert.toDto(flwProcessInstanceBase);
    }

    @Override
    public Pair<Boolean, ProcessInstanceDto> update(ProcessInstanceContext context) {
        ProcessInstanceBase flwProcessInstanceBase = convert.toFlwProcessInstanceBase(context);
        int flag = flwProcessInstanceMapper.updateById(flwProcessInstanceBase);
        return Pair.of( flag>0 , convert.toDto(flwProcessInstanceBase)) ;
    }

    @Override
    public void updateByInstanceId(String instanceId, InstanceStatusEnum status) {
        LocalDateTime endTime = null;
        if (status.isComplete()){
            endTime = LocalDateTimeUtil.dateTime();
        }
        flwProcessInstanceMapper.updateByInstanceId(instanceId,status.getStatus(),endTime);
    }

    @Override
    public int delete(Long id) {
        return flwProcessInstanceMapper.deleteById(id);
    }

    @Override
    public ProcessInstanceDto findByBizNoAndProcessNo(String bizNo, String processNo) {
        ProcessInstanceBase instanceBase = flwProcessInstanceMapper.findByBizNoAndProcessNo(bizNo, processNo);
        return  convert.toDto(instanceBase);
    }

    @Override
    public ProcessInstanceDto findByInstanceNo(String instanceNo) {
        ProcessInstanceBase instanceBase = flwProcessInstanceMapper.findByInstanceNo(instanceNo);
        return  convert.toDto(instanceBase);
    }

    @Override
    public ProcessInstanceDto findByInstanceId(String instanceId) {
        if (StringUtils.isEmpty(instanceId)){
            return null;
        }
        ProcessInstanceBase instanceBase = flwProcessInstanceMapper.findByInstanceId(instanceId);
        return  convert.toDto(instanceBase);
    }

}
