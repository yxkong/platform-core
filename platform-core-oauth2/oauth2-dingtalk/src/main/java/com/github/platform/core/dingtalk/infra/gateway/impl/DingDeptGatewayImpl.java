package com.github.platform.core.dingtalk.infra.gateway.impl;

import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.dingtalk.domain.common.entity.DingDeptBase;
import com.github.platform.core.dingtalk.domain.context.DingDeptContext;
import com.github.platform.core.dingtalk.domain.dto.DingDeptDto;
import com.github.platform.core.dingtalk.domain.gateway.IDingDeptGateway;
import com.github.platform.core.dingtalk.infra.convert.DingDeptInfraConvert;
import com.github.platform.core.persistence.mapper.dingding.DingDeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * 钉钉部门网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
@Service
public class DingDeptGatewayImpl extends BaseGatewayImpl implements IDingDeptGateway {

    @Resource
    private DingDeptMapper dingDeptMapper;
    @Resource
    private DingDeptInfraConvert dingDeptInfraConvert;



    @Override
    public DingDeptDto findById(Long id) {
        DingDeptBase record = dingDeptMapper.findById(id);
        return dingDeptInfraConvert.toDto(record);
    }

    @Override
    public Boolean update(DingDeptContext context){
        DingDeptBase record = dingDeptInfraConvert.toDingDeptBase(context);
        return dingDeptMapper.updateById(record) > 0;
    }

    @Override
    public List<DingDeptDto> findListBy(DingDeptContext context){
        DingDeptBase record = dingDeptInfraConvert.toDingDeptBase(context);
        List<DingDeptBase> list = dingDeptMapper.findListBy(record);
        return dingDeptInfraConvert.toDtos(list);
    }


}
