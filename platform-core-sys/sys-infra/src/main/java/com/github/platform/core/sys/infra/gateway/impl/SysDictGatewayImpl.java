package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.sys.SysDictMapper;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysDictBase;
import com.github.platform.core.sys.domain.context.SysDictContext;
import com.github.platform.core.sys.domain.context.SysDictQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictDto;
import com.github.platform.core.sys.domain.gateway.ISysDictGateway;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.convert.SysDictInfraConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author yxk
 */
@Service
@Slf4j
public class SysDictGatewayImpl extends BaseGatewayImpl implements ISysDictGateway {


    @Resource
    private SysDictInfraConvert dictInfraConvert;

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public PageBean<SysDictDto> query(SysDictQueryContext context) {
        SysDictBase sysDictBase = dictInfraConvert.toSysDictBase(context);
        PageHelper.startPage(context.getPageNum(),context.getPageSize());
        List<SysDictDto> list = sysDictMapper.findDtoListBy(sysDictBase);
        return dictInfraConvert.ofPageBeanDto(new PageInfo<>(list));
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON +#context.tenantId+':'+ #context.dictType", cacheManager = CacheConstant.cacheManager)
    public SysDictDto insert(SysDictContext context) {
        int isExist = this.isExistDict(context);
        if (isExist > 0) {
            throw exception(SysInfraResultEnum.DICT_EXIST);
        }
        SysDictBase record = dictInfraConvert.toSysDictBase(context);
        sysDictMapper.insert(record);
        return dictInfraConvert.toDto(record);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON +#context.tenantId+':'+ #context.dictType", cacheManager = CacheConstant.cacheManager)
    public void update(SysDictContext context) {
        int existDict = this.isExistDict(context);
        if (existDict > 0 ) {
            throw exception(SysInfraResultEnum.DICT_EXIST);
        }
        SysDictBase record = dictInfraConvert.toSysDictBase(context);
        int row = sysDictMapper.updateById(record);
        if ( row <= 0) {
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }

    @Override
    public int isExistDict(SysDictContext context) {
        return  sysDictMapper.isExistDict(context.getDictType(), context.getKey(), context.getLabel(),context.getId());
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON +#context.tenantId+':'+ #context.dictType", cacheManager = CacheConstant.cacheManager)
    public void delete(SysDictContext context) {
        //删除字典
        sysDictMapper.deleteById(context.getId());
    }

    @Override
    public SysDictDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        SysDictBase record = sysDictMapper.findById(id);
        return dictInfraConvert.toDto(record);
    }

    @Override
    @Cacheable(cacheNames =CACHE_NAME, key = "#root.target.PREFIX_COLON +#tenantId +':'+ #dictType", cacheManager = CacheConstant.cacheManager, unless = "#result == null || #result.isEmpty()")
    public List<SysDictDto> findByType(String dictType,Integer tenantId) {
        if (StringUtils.isEmpty(dictType)){
            return null;
        }
        return sysDictMapper.findDtoListBy(SysDictBase.builder().dictType(dictType).tenantId(tenantId).status(StatusEnum.ON.getStatus()).build());
    }
}
