package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.sys.domain.common.entity.SysFlowRuleBase;
import com.github.platform.core.sys.domain.context.SysFlowRuleContext;
import com.github.platform.core.sys.domain.context.SysFlowRuleQueryContext;
import com.github.platform.core.sys.domain.dto.SysFlowRuleDto;
import com.github.platform.core.sys.domain.gateway.ISysFlowRuleGateway;
import com.github.platform.core.persistence.mapper.sys.SysFlowRuleMapper;
import com.github.platform.core.sys.infra.convert.SysFlowRuleInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/**
 * 状态机配置规则网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
@Service
public class SysFlowRuleGatewayImpl extends BaseGatewayImpl implements ISysFlowRuleGateway {

    @Resource
    private SysFlowRuleMapper sysFlowRuleMapper;
    @Resource
    private SysFlowRuleInfraConvert sysFlowRuleConvert;
    @Override
    public PageBean<SysFlowRuleDto> query(SysFlowRuleQueryContext context) {
        SysFlowRuleBase record = sysFlowRuleConvert.toSysFlowRuleBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysFlowRuleBase> list = sysFlowRuleMapper.findListBy(record);
        return sysFlowRuleConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public List<SysFlowRuleDto> findByBizType(String bizType) {
        List<SysFlowRuleBase> list = sysFlowRuleMapper.findListBy(SysFlowRuleBase.builder().bizType(bizType).build());
        return sysFlowRuleConvert.toDtos(list);
    }

    @Override
    public SysFlowRuleDto insert(SysFlowRuleContext context) {
        SysFlowRuleBase record = sysFlowRuleConvert.toSysFlowRuleBase(context);
        sysFlowRuleMapper.insert(record);
        return sysFlowRuleConvert.toDto(record);
    }

    @Override
    public SysFlowRuleDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        SysFlowRuleBase record = sysFlowRuleMapper.findById(id);
        return sysFlowRuleConvert.toDto(record);
    }

    @Override
    public Pair<Boolean, SysFlowRuleDto> update(SysFlowRuleContext context) {
        SysFlowRuleBase record = sysFlowRuleConvert.toSysFlowRuleBase(context);
        int flag = sysFlowRuleMapper.updateById(record);
        return Pair.of( flag>0 , sysFlowRuleConvert.toDto(record)) ;
    }

    @Override
    public int delete(SysFlowRuleContext context) {
        return sysFlowRuleMapper.deleteById(context.getId());
    }
}
