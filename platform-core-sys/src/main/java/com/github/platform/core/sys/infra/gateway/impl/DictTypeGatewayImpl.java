package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.sys.SysDictTypeMapper;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysDictTypeBase;
import com.github.platform.core.sys.domain.context.SysDictTypeContext;
import com.github.platform.core.sys.domain.context.SysDictTypeQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictTypeDto;
import com.github.platform.core.sys.domain.gateway.ISysDictTypeGateway;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.convert.SysDictTypeInfraConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类型网关
 * @Author: yxkong
 * @Date: 2024/1/28 11:28
 * @version: 1.0
 */
@Service
@Slf4j
public class DictTypeGatewayImpl extends BaseGatewayImpl implements ISysDictTypeGateway {
    @Resource
    private SysDictTypeInfraConvert typeInfraConvert;
    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    @Override
    public PageBean<SysDictTypeDto> query(SysDictTypeQueryContext context) {
        SysDictTypeBase record = typeInfraConvert.toSysDictTypeBase(context);
        PageHelper.startPage(context.getPageNum(),context.getPageSize());
        List<SysDictTypeBase> list = sysDictTypeMapper.findListBy(record);
        return typeInfraConvert.ofPageBean(new PageInfo<>(list));

    }

    @Override
    public List<SysDictTypeDto> findListBy(SysDictTypeQueryContext context) {
        SysDictTypeBase record = typeInfraConvert.toSysDictTypeBase(context);
        List<SysDictTypeBase> list = sysDictTypeMapper.findListBy(record);
        return typeInfraConvert.toDtos(list);
    }

    @Override
    public void insert(SysDictTypeContext context) {
        SysDictTypeBase record = typeInfraConvert.toSysDictTypeBase(context);
        if (sysDictTypeMapper.isExistDictTypeInsert(context.getName(), context.getType())>0) {
            exception(SysInfraResultEnum.DICT_EXIST_KEY);
        }
        sysDictTypeMapper.insert(record);
    }

    @Override
    public void update(SysDictTypeContext context) {
        SysDictTypeBase dictType = typeInfraConvert.toSysDictTypeBase(context);
        if (sysDictTypeMapper.isExistDictTypeUpdate(context.getId(), context.getName())>0) {
            exception(SysInfraResultEnum.DICT_EXIST_NAME);
        }
        dictType.setType(null);
        sysDictTypeMapper.updateById(dictType);
    }

    @Override
    public void delete(SysDictTypeContext context) {
        int flag = sysDictTypeMapper.deleteById(context.getId());
        if ( flag <= 0) {
            exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }

    @Override
    public SysDictTypeDto findById(Long id) {
        SysDictTypeBase record = sysDictTypeMapper.findById(id);
        return typeInfraConvert.toDto(record);
    }

    @Override
    public SysDictTypeDto findByType(String dictType) {
        if (StringUtils.isEmpty(dictType)){
            return null;
        }
        List<SysDictTypeBase> list = sysDictTypeMapper.findListBy(SysDictTypeBase.builder().type(dictType).build());
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        return typeInfraConvert.toDto(list.get(0));
    }
    @Override
    public void deleteCache(String dictType) {
    }
}
