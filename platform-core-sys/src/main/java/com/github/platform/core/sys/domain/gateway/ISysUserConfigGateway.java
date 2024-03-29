package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.sys.domain.context.SysUserConfigContext;
import com.github.platform.core.sys.domain.dto.SysUserConfigDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 用户配置网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
public interface ISysUserConfigGateway {
    /**
    * 新增用户配置
    * @param context 新增上下文
    * @return 返回结果
    */
    SysUserConfigDto insert(SysUserConfigContext context);
    /**
    * 根据id查询用户配置明细
    * @param id 主键
    * @return 结果
    */
    SysUserConfigDto findById(Long id);
    /**
    * 修改用户配置
    * @param context 修改上下文
    * @return 更新结果
    */
    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:uc:'+#context.loginName+'*'",allEntries = true,cacheManager = CacheConstant.cacheManager)
    Boolean update(SysUserConfigContext context);
    /**
    * 通过实体参数获取列表
    * @param context 查询参数
    * @return List<SysUserConfigDto>
    */
    List<SysUserConfigDto> findListBy(SysUserConfigContext context);

    /**
     * 获取用户所有的配置
     * @param loginName
     * @return
     */
    @Cacheable(cacheNames = CacheConstant.c12h, key = "'sys:uc:' + #context.loginName", cacheManager = CacheConstant.cacheManager, unless = "#result == null || #result.isEmpty()")
    List<SysUserConfigDto> getUserAllConfig(String loginName);

    /**
     * 查询用户某个配置
     * @param loginName
     * @param configKey
     * @return
     */
    @Cacheable(cacheNames = CacheConstant.c12h, key = "'sys:uc:' + #loginName+':'+#configKey", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    SysUserConfigDto getConfig(String loginName, String configKey);
}
