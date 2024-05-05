package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

/**
 * token缓存网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
public interface ISysTokenCacheGateway {
    /**
    * 查询token缓存列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysTokenCacheDto> query(SysTokenCacheQueryContext context);
    /**
    * 新增token缓存
    * @param context 新增上下文
    * @return 返回结果
    */
    SysTokenCacheDto insert(SysTokenCacheContext context);
    /**
    * 根据id查询token缓存明细
    * @param id 主键
    * @return 结果
    */
    SysTokenCacheDto findById(Long id);

    /**
     * 根据token查询缓存信息
     * @param token
     * @return
     */
    @Cacheable(cacheNames = CacheConstant.c8h, key = "'sys:token:' +#token", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    SysTokenCacheDto findByToken(String token);
    /**
     * 根据token查询缓存信息
     * @param tenantId
     * @return
     */
    List<SysTokenCacheDto> findByLoginName(Integer tenantId, String loginName);
    /**
    * 修改token缓存
    * @param context 修改上下文
    * @return 更新结果
    */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CacheConstant.c8h,key = "'sys:token:'+#context.token",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CacheConstant.c8h,key = "'sys:token:'+#context.tenantId+':'+#context.loginName",cacheManager = CacheConstant.cacheManager)
            }
    )
    SysTokenCacheDto update(SysTokenCacheContext context);
    /**
    * 根据id删除token缓存记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CacheConstant.c8h,key = "'sys:token:'+#context.token",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CacheConstant.c8h,key = "'sys:token:'+#context.tenantId+':'+#context.loginName",cacheManager = CacheConstant.cacheManager)
            }
    )
    int expire(SysTokenCacheContext context);


}
