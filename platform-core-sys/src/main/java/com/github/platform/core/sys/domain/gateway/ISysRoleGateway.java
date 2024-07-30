package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
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
     * 查询列表
     * @param context
     * @return
     */
    List<SysRoleDto> findListBy(SysRoleQueryContext context);

    /**
     * 新增角色
     * @param context
     */
    void add(SysRoleContext context);

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
    void update(SysRoleContext context);


    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    List<SysRoleDto> findRoleByUserId(Long userId);

    /**
     * 删除用户角色
     * @param userId 用户id
     */
    void deleteUserRoleByUserId(Long userId);

    /**
     * 新增用户角色
     * @param userId
     * @param tenantId
     * @param roleKeys
     */
    void addUserRole(Long userId,Integer tenantId, Set<String> roleKeys);

    /**
     * 根据角色id查询菜单id
     * @param roleId 角色id
     * @return
     */
    Set<Long> queryMenuIds(Long roleId);


    /**
     * 查询角色
     * @param context  查询上下文
     * @return
     */
    List<OptionsDto> roles(SysRoleQueryContext context);


}
