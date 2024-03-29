package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysDictTypeContext;
import com.github.platform.core.sys.domain.context.SysDictTypeQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictTypeDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

/**
 * 字典类型网关
 * @Author: yxkong
 * @Date: 2024/1/28 11:27
 * @version: 1.0
 */
public interface ISysDictTypeGateway {
    /**
     * 分页查询字典类型
     *
     * @param context
     * @return
     */
    PageBean<SysDictTypeDto> query(SysDictTypeQueryContext context);

    /**
     * 查询列表
     * @param context
     * @return
     */
    List<SysDictTypeDto> findListBy(SysDictTypeQueryContext context);

    /**
     * 新增字典类型
     *
     * @param context
     * @return
     */
    void insert(SysDictTypeContext context);

    /**
     * 修改字典
     * @param context
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:dt:a:'+#context.type",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:dt:o:'+#context.type",cacheManager = CacheConstant.cacheManager)
            }
    )
    void update(SysDictTypeContext context);

    /**
     * 删除字典类型
     *
     * @param context
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:dt:a:'+#context.type",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:dt:o:'+#context.type",cacheManager = CacheConstant.cacheManager)
            }
    )
    void delete(SysDictTypeContext context);

    /**
     * 根据id查询字典类型
     * @param id
     * @return
     */
    SysDictTypeDto findById(Long id);

    @Cacheable(cacheNames = CacheConstant.c12h, key = "'sys:dt:o:' + #dictType", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    SysDictTypeDto findByType(String dictType);

    /**
     * 重载字典缓存
     * @param dictType
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:dt:a:'+#dictType",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CacheConstant.c12h,key = "'sys:dt:o:'+#dictType",cacheManager = CacheConstant.cacheManager)
            }
    )
    void deleteCache(String dictType);
}
