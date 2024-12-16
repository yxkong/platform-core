package com.github.platform.core.gateway.admin.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.gateway.admin.domain.common.entity.GatewayRouteBase;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteQueryContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteDto;
import com.github.platform.core.gateway.admin.domain.gateway.IGatewayRouteGateway;
import com.github.platform.core.gateway.admin.infra.convert.GatewayRouteInfraConvert;
import com.github.platform.core.persistence.mapper.gateway.admin.GatewayRouteMapper;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * 网关路由网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@Service
public class GatewayRouteGatewayImpl extends BaseGatewayImpl implements IGatewayRouteGateway {

    @Resource
    private GatewayRouteMapper gatewayRoutesMapper;
    @Resource
    private GatewayRouteInfraConvert gatewayRoutesConvert;
    @Override
    public PageBean<GatewayRouteDto> query(GatewayRouteQueryContext context) {
        GatewayRouteBase record = gatewayRoutesConvert.toGatewayRoutesBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<GatewayRouteBase> list = gatewayRoutesMapper.findListBy(record);
        return gatewayRoutesConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public List<GatewayRouteDto> findBy(GatewayRouteContext context) {
        GatewayRouteBase record = gatewayRoutesConvert.toGatewayRoutesBase(context);
        List<GatewayRouteBase> list = gatewayRoutesMapper.findListBy(record);
        return gatewayRoutesConvert.toDtos(list);
    }

    @Override
    public GatewayRouteDto insert(GatewayRouteContext context) {
        GatewayRouteBase record = gatewayRoutesConvert.toGatewayRoutesBase(context);
        gatewayRoutesMapper.insert(record);
        return gatewayRoutesConvert.toDto(record);
    }

    @Override
    public GatewayRouteDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        GatewayRouteBase record = gatewayRoutesMapper.findById(id);
        return gatewayRoutesConvert.toDto(record);
    }

    @Override
    public Pair<Boolean, GatewayRouteDto> update(GatewayRouteContext context) {
        GatewayRouteBase record = gatewayRoutesConvert.toGatewayRoutesBase(context);
        int flag = gatewayRoutesMapper.updateById(record);
        return Pair.of( flag>0 , gatewayRoutesConvert.toDto(record)) ;
    }

    @Override
    public int delete(GatewayRouteContext context) {
        return gatewayRoutesMapper.deleteById(context.getId());
    }
}
