package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.persistence.mapper.sys.SysCascadeMapper;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysCascadeBase;
import com.github.platform.core.sys.domain.context.SysCascadeContext;
import com.github.platform.core.sys.domain.context.SysCascadeQueryContext;
import com.github.platform.core.sys.domain.dto.SysCascadeDto;
import com.github.platform.core.sys.domain.gateway.ISysCascadeGateway;
import com.github.platform.core.sys.infra.convert.SysCascadeInfraConvert;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * 级联表网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-01 20:57:44.667
 * @version 1.0
 */
@Service
public class SysCascadeGatewayImpl extends BaseGatewayImpl implements ISysCascadeGateway {

    @Resource
    private SysCascadeMapper sysCascadeMapper;
    @Resource
    private SysCascadeInfraConvert sysCascadeConvert;
    @Override
    public PageBean<SysCascadeDto> query(SysCascadeQueryContext context) {
        SysCascadeBase record = sysCascadeConvert.toSysCascadeBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysCascadeBase> list = sysCascadeMapper.findListBy(record);
        return sysCascadeConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public List<SysCascadeDto> list(SysCascadeQueryContext context) {
        SysCascadeBase record = sysCascadeConvert.toSysCascadeBase(context);
        List<SysCascadeBase> list = sysCascadeMapper.findListBy(record);
        return sysCascadeConvert.toDtos(list);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + ':l'+ #context.parentId",cacheManager = CacheConstant.cacheManager)
    public SysCascadeDto insert(SysCascadeContext context) {
        SysCascadeBase record = sysCascadeConvert.toSysCascadeBase(context);
        sysCascadeMapper.insert(record);
        return sysCascadeConvert.toDto(record);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON + #id", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public SysCascadeDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        SysCascadeBase record = sysCascadeMapper.findById(id);
        return sysCascadeConvert.toDto(record);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON + ':'+ #code+#tenantId", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public SysCascadeDto findByCode(String code, Integer tenantId) {
        SysCascadeBase record = sysCascadeMapper.findByCode(code,tenantId);
        return sysCascadeConvert.toDto(record);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON +':l'+ #parentId", cacheManager = CacheConstant.cacheManager,unless = "#result == null || #result.isEmpty()")
    public List<SysCascadeDto> findByParentId(Long parentId) {
        List<SysCascadeBase> list = sysCascadeMapper.findListBy(SysCascadeBase.builder().parentId(parentId).status(StatusEnum.ON.getStatus()).build());
        return sysCascadeConvert.toDtos(list);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + #context.id",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + ':l'+ #context.id",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + ':'+ #context.code+#context.tenantId",cacheManager = CacheConstant.cacheManager)
            }
    )
    public Pair<Boolean, SysCascadeDto> update(SysCascadeContext context) {
        SysCascadeBase record = sysCascadeConvert.toSysCascadeBase(context);
        int flag = sysCascadeMapper.updateById(record);
        return Pair.of( flag>0 , sysCascadeConvert.toDto(record)) ;
    }

    @Override
    public int delete(SysCascadeContext context) {
        return sysCascadeMapper.deleteById(context.getId());
    }
}
