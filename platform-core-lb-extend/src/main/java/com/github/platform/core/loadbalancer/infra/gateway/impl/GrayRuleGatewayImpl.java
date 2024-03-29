package com.github.platform.core.loadbalancer.infra.gateway.impl;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.loadbalancer.domain.common.entity.GrayRuleBase;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleContext;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleQueryContext;
import com.github.platform.core.loadbalancer.domain.dto.GrayRuleDto;
import com.github.platform.core.loadbalancer.domain.entity.GrayRuleEntity;
import com.github.platform.core.loadbalancer.domain.gateway.IGrayRuleGateway;
import com.github.platform.core.loadbalancer.infra.convert.GrayRuleInfraConvert;
import com.github.platform.core.loadbalancer.infra.service.IGrayRuleService;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
* 灰度规则表网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
@Service
public class GrayRuleGatewayImpl implements IGrayRuleGateway {
    @Resource
    private IGrayRuleService grayRuleService;
    @Resource
    private GrayRuleInfraConvert convert;
    @Resource
    private ICacheService cacheService;
    @Autowired
    private CacheProperties properties;
    @Override
    public GrayRuleEntity findOne() {
        String key = properties.getGrayRule();
        String str = (String)cacheService.get(key);
        GrayRuleEntity grayRuleEntity = JsonUtils.fromJson(str, GrayRuleEntity.class);
        if (Objects.nonNull(grayRuleEntity)){
            return grayRuleEntity;
        }
        GrayRuleBase rule = grayRuleService.findOne();
        return putRedis(rule);
    }
    private GrayRuleEntity putRedis(GrayRuleBase rule){
        GrayRuleEntity entity = null;
        if (Objects.nonNull(rule)){
            entity =  convert.toEntity(rule);
            cacheService.set(properties.getGrayRule(),JsonUtils.toJson(entity));
        }
        return entity;
    }

    @Override
    public PageBean<GrayRuleDto> query(GrayRuleQueryContext context) {
        GrayRuleBase ruleDO = convert.toGrayRuleBase(context);
        PageInfo<GrayRuleBase> pageInfo = grayRuleService.findPageInfo(ruleDO, context.getPageNum(), context.getPageSize());
        List<GrayRuleDto> list = convert.toDtos(pageInfo.getList());
        return new PageBean<>(pageInfo.getPageNum(),pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getPageSize(),list);
    }

    @Override
    public Pair<Boolean, String> insert(GrayRuleContext context) {
        GrayRuleBase ruleDO = convert.toGrayRuleBase(context);
        ruleDO.setId(null);
        boolean flag = grayRuleService.insert(ruleDO);
        if (!flag){
            return Pair.of(false,"添加灰度规则失败！");
        }
        //只能有一条启用的规则
        if (StatusEnum.ON.getStatus().equals(ruleDO.getStatus())){
            grayRuleService.updateOtherOffById(ruleDO.getId());
            putRedis(ruleDO);
            return Pair.of(true,"添加灰度规则成功！");
        }
        return Pair.of(true,"添加灰度规则成功！");
    }

    @Override
    public Pair<Boolean, String> update(GrayRuleContext context) {
        if (Objects.isNull(context.getId())){
            return Pair.of(false,"主键id不能为空！");
        }
        GrayRuleBase ruleDO = convert.toGrayRuleBase(context);
        boolean flag = grayRuleService.updateById(ruleDO);
        if (!flag){
            return Pair.of(false,"修改灰度规则失败！");
        }
        //只能有一条启用的规则
        if (StatusEnum.ON.getStatus().equals(ruleDO.getStatus())){
            grayRuleService.updateOtherOffById(ruleDO.getId());
            putRedis(ruleDO);

        }
        return Pair.of(true,"修改灰度规则成功！");
    }

    @Override
    public Pair<Boolean, String> delete(@NotNull Long id) {
        GrayRuleBase ruleDO = grayRuleService.findById(id);
        if (Objects.isNull(ruleDO)){
            return Pair.of(false,"未找到对应的记录！");
        }
        if (StatusEnum.ON.getStatus().equals(ruleDO.getStatus())){
            return Pair.of(false,"启用中的规则不能删除！");
        }
        boolean flag = grayRuleService.deleteById(id);
        if (flag){
            return Pair.of(true,"删除规则成功！");
        }
        return Pair.of(false,"删除规则失败！");
    }
    @Override
    public GrayRuleDto findById(Long id) {
        GrayRuleBase grayRuleBase = grayRuleService.findById(id);
        return convert.toDto(grayRuleBase);
    }
}
