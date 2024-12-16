package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.constant.SysCacheKeyPrefix;
import com.github.platform.core.sys.domain.context.SysDictTypeContext;
import com.github.platform.core.sys.domain.context.SysDictTypeQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictDto;
import com.github.platform.core.sys.domain.dto.SysDictTypeDto;

import java.util.List;

/**
 * 字典类型网关
 * @Author: yxkong
 * @Date: 2024/1/28 11:27
 * @version: 1.0
 */
public interface ISysDictTypeGateway {
    /**缓存前缀*/
    String PREFIX = SysCacheKeyPrefix.DICT_TYPE.getPrefix();
    /**缓存前缀加冒号*/
    String PREFIX_COLON = SysCacheKeyPrefix.DICT_TYPE.getWithColon();
    /**全部缓存前缀*/
    String ALL_PREFIX_COLON = SysCacheKeyPrefix.DICT_TYPE.getWithColon()+"a:";
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c12h;
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

    void update(SysDictTypeContext context);

    /**
     * 删除字典类型
     *
     * @param context
     * @return
     */
    void delete(SysDictTypeContext context);

    /**
     * 根据id查询字典类型
     * @param id
     * @return
     */
    SysDictTypeDto findById(Long id);


    SysDictTypeDto findByType(String dictType,Integer tenantId);

    /**
     * 重载字典缓存
     * @param dictType
     * @return
     */
    void deleteCache(String dictType,Integer tenantId);
    /**查询租户对应的所有*/
    List<SysDictDto> findAllType(Integer tenantId);

    /**
     * 删除租户对应的所有缓存
     */
    void deleteAllCache(Integer tenantId);
}
