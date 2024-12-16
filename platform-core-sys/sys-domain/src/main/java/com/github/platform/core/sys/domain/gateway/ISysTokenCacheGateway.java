package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.constant.SysCacheKeyPrefix;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;

import java.util.List;

/**
 * token缓存网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
public interface ISysTokenCacheGateway {
    /**缓存前缀*/
    String PREFIX =  SysCacheKeyPrefix.TOKEN_CACHE.getPrefix();
    /**缓存前缀加冒号*/
    String PREFIX_COLON = SysCacheKeyPrefix.TOKEN_CACHE.getWithColon();
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c30m;
    /**
    * 查询token缓存列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysTokenCacheDto> query(SysTokenCacheQueryContext context);

    /**
     * 列表查询
     * @param context
     * @return
     */
    List<SysTokenCacheDto> findListBy(SysTokenCacheQueryContext context);
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
    SysTokenCacheDto findByToken(String token);
    /**
     * 根据token查询缓存信息
     * @param tenantId
     * @return
     */
    SysTokenCacheDto findByLoginName(Integer tenantId, String loginName);
    /**
    * 修改token缓存
    * @param context 修改上下文
    * @return 更新结果
    */
    SysTokenCacheDto update(SysTokenCacheContext context);
    /**
    * 根据id删除token缓存记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int expire(SysTokenCacheContext context);


}
