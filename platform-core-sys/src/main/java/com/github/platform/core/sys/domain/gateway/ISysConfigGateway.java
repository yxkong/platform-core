package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.constant.SysCacheKeyPrefix;
import com.github.platform.core.sys.domain.context.SysConfigContext;
import com.github.platform.core.sys.domain.context.SysConfigQueryContext;
import com.github.platform.core.sys.domain.dto.SysConfigDto;

import java.util.List;

/**
 * 配置网关
 * @author: yxkong
 * @date: 2023/4/19 4:00 PM
 * @version: 1.0
 */
public interface ISysConfigGateway {
    /**缓存前缀*/
    String PREFIX =  SysCacheKeyPrefix.DEPT.getPrefix();
    /**缓存前缀加冒号*/
    String PREFIX_COLON = SysCacheKeyPrefix.DEPT.getWithColon();
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c12h;
    /**
     * 分页查询
     * @param context
     * @return
     */
    PageBean<SysConfigDto> query(SysConfigQueryContext context);

    /**
     * 列表查询
     * @param context
     * @return
     */
    List<SysConfigDto> findListBy(SysConfigQueryContext context);

    /**
     * 新增配置
     * @param context
     */
    void insert(SysConfigContext context);

    /**
     * 更新配置
     * @param context
     */
    void update(SysConfigContext context);

    /**
     * 删除配置
     * @param id
     * @param key
     */
    void delete(Long id,Integer tenantId,String key);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysConfigDto findById(Long id);

    /**
     * 获取配置
     * @param key
     * @return
     */
    SysConfigDto getConfig(Integer tenantId,String key);


    /**
     * 删除缓存
     * @param key
     */
    void deleteCache(Integer tenantId,String key);
}
