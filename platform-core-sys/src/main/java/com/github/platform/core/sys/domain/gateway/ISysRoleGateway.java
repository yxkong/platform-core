package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import com.github.platform.core.sys.domain.constant.SysCacheKeyPrefix;
import com.github.platform.core.sys.domain.context.SysRoleContext;
import com.github.platform.core.sys.domain.context.SysRoleQueryContext;
import com.github.platform.core.sys.domain.dto.SysRoleDto;

import java.util.List;
import java.util.Set;

public interface ISysRoleGateway {
    /**缓存前缀*/
    String PREFIX =  SysCacheKeyPrefix.ROLE.getPrefix();
    /**缓存前缀加冒号*/
    String PREFIX_COLON = SysCacheKeyPrefix.ROLE.getWithColon();
    String ROLE_MENU_COLON = PREFIX_COLON + "m:";
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c12h;
    /**
     * 查询角色
     * @param context
     * @return
     */
    PageBean<SysRoleDto> query(SysRoleQueryContext context);

    /**
     * 新增角色
     * @param context
     */
    SysRoleDto insert(SysRoleContext context);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteById(Long roleId);

    /**
     * 根据id查询角色
     * @param roleId
     * @return
     */
    SysRoleDto findById(Long roleId);
	/**
	 * 根据角色名称h或key 在指定租户中查询
	 * @param name 角色名称
	 * @param key 角色key
	 * @param tenantId 租户id
	 * @return 角色信息
	 */
    SysRoleDto findByNameOrKeyAndTenant(String name,String key, Integer tenantId);
    /**
     * 根据角色key查询
     * @param roleKey
     * @return
     */
    SysRoleDto findByRoleKey(String roleKey);

    /**
     * 根据ids查询多个角色
     * @param roleIds
     * @return
     */
    List<SysRoleDto> findByIds(List<Long> roleIds);

    /**
     * 根据角色key查询
     * @param roleKeys
     * @return
     */
    List<SysRoleDto> findByKeys(List<String> roleKeys,Integer tenantId);

    /**
     * 更新角色
     * @param context
     */
    SysRoleDto updateById(SysRoleContext context);


    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    List<SysRoleDto> findRoleByUserId(Long userId);

    /**
     * 查询角色
     * @param context  查询上下文
     * @return
     */
    List<OptionsDto> roles(SysRoleQueryContext context);


    SysRoleDto findByExist(Long id, String roleName, String key, Integer tenantId);
}
