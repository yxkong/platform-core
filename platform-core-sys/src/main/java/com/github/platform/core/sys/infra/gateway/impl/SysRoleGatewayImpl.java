package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.sys.SysRoleMapper;
import com.github.platform.core.persistence.mapper.sys.SysUserRoleMapper;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import com.github.platform.core.sys.domain.common.entity.SysUserRoleBase;
import com.github.platform.core.sys.domain.context.SysRoleContext;
import com.github.platform.core.sys.domain.context.SysRoleQueryContext;
import com.github.platform.core.sys.domain.dto.SysRoleDto;
import com.github.platform.core.sys.domain.gateway.ISysRoleGateway;
import com.github.platform.core.sys.infra.convert.SysRoleInfraConvert;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yxkong
 */
@Slf4j
@Service
public class SysRoleGatewayImpl extends BaseGatewayImpl implements ISysRoleGateway {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleInfraConvert roleInfraConvert;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public PageBean<SysRoleDto> query(SysRoleQueryContext context) {
        SysRoleBase sysRoleBase = roleInfraConvert.toSysRoleBase(context);
        PageHelper.startPage(context.getPageNum(),context.getPageSize());
        List<SysRoleBase> list = sysRoleMapper.findListBy(sysRoleBase);
        return roleInfraConvert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public SysRoleDto insert(SysRoleContext context) {
        SysRoleBase sysRoleBase = roleInfraConvert.toSysRoleBase(context);
        sysRoleMapper.insert(sysRoleBase);
        return roleInfraConvert.toDto(sysRoleBase);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
     @Caching(
            evict = {
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + #roleId",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames =CacheConstant.c1h, key = "#root.target.ROLE_MENU_COLON +  #roleId",cacheManager = CacheConstant.cacheManager)
            }
    )
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public SysRoleDto findById(Long roleId) {
        SysRoleBase sysUser = sysRoleMapper.findById(roleId);
        if (Objects.isNull(sysUser)) {
            return null;
        }
        return roleInfraConvert.toDto(sysUser);
    }

    @Override
    public SysRoleDto findByNameOrKeyAndTenant(String name, String key, Integer tenantId) {
        SysRoleBase sysRoleBase = sysRoleMapper.findByNameOrKeyAndTenant(name, key, tenantId);
        return roleInfraConvert.toDto(sysRoleBase);
    }

    @Override
    public List<SysRoleDto> findByIds(List<Long> roleIds) {
        List<SysRoleBase> list = sysRoleMapper.findByIds(roleIds.toArray(new Long[0]));
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return roleInfraConvert.toDtos(list);
    }

    @Override
    public List<SysRoleDto> findByKeys(List<String> roleKeys,Integer tenantId) {
        List<SysRoleBase> list = sysRoleMapper.findByKeys(roleKeys.toArray(new String[]{}),tenantId);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return roleInfraConvert.toDtos(list);
    }

    @Override
    public List<OptionsDto> roles(SysRoleQueryContext context) {
        List<OptionsDto> rst = new ArrayList<>();
        SysRoleBase record = roleInfraConvert.toSysRoleBase(context);
        List<SysRoleBase> list = sysRoleMapper.findListBy(record);
        if (CollectionUtil.isNotEmpty(list)){
            list.forEach(s->{
                rst.add(new OptionsDto(s.getKey(),s.getStrId(),s.getName()));
            });
        }
        return rst;
    }

    @Override
    public SysRoleDto findByExist(Long id, String roleName, String key, Integer tenantId) {
        SysRoleBase sysRoleBase = sysRoleMapper.findByExist(id, roleName, key, tenantId);
        return roleInfraConvert.toDto(sysRoleBase);
    }

    @Override
    public SysRoleDto findByRoleKey(String roleKey) {
        if (StringUtils.isEmpty(roleKey)){
            return null;
        }
        List<SysRoleBase> list = sysRoleMapper.findListBy(SysRoleBase.builder().key(roleKey).build());
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        return roleInfraConvert.toDto(list.get(0)) ;
    }


    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON + #context.id",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames =CacheConstant.c1h, key = "#root.target.ROLE_MENU_COLON +  #context.id",cacheManager = CacheConstant.cacheManager)
            }
    )
    public SysRoleDto updateById(SysRoleContext context) {
        SysRoleBase sysRole = roleInfraConvert.toSysRoleBase(context);
        sysRoleMapper.updateById(sysRole);
        return roleInfraConvert.toDto(sysRole);
    }

    @Override
    public List<SysRoleDto> findRoleByUserId(Long userId) {
        List<SysUserRoleBase> userRoleList = sysUserRoleMapper.findByIds(new Long[]{userId});
        if (CollectionUtil.isEmpty(userRoleList)) {
            return Lists.newArrayList();
        }

        Long[] roleIds = userRoleList.stream().map(SysUserRoleBase::getRoleId).toArray(Long[]::new);

        List<SysRoleBase> sysRoleBaseList = sysRoleMapper.findByIds(roleIds);
        return roleInfraConvert.toDtos(sysRoleBaseList);
    }
}
