package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysConfigContext;
import com.github.platform.core.sys.domain.context.SysConfigQueryContext;
import com.github.platform.core.sys.domain.dto.SysConfigDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

/**
 * 配置网关
 * @author: yxkong
 * @date: 2023/4/19 4:00 PM
 * @version: 1.0
 */
public interface ISysConfigGateway {
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
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:cfg:'+#context.id",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:cfg:'+#context.tenantId+':'+#context.key",cacheManager = CacheConstant.cacheManager)
            }
    )
    void update(SysConfigContext context);

    /**
     * 删除配置
     * @param id
     * @param key
     */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:cfg:'+#id",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:cfg:'+#tenantId+':'+#key",cacheManager = CacheConstant.cacheManager)
            }
    )
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
    @Cacheable(cacheNames = CacheConstant.c12h, key = "'sys:cfg:' +#tenantId+':'+ #key", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    SysConfigDto getConfig(Integer tenantId,String key);


    /**
     * 删除缓存
     * @param key
     */
    @Cacheable(cacheNames = CacheConstant.c12h, key = "'sys:cfg:' +#tenantId+':'+ #key", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    void deleteCache(Integer tenantId,String key);
}
