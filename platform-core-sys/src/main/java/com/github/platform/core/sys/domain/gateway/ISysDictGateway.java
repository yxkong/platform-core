package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.constant.SysCacheConstant;
import com.github.platform.core.sys.domain.constant.SysCacheKeyPrefix;
import com.github.platform.core.sys.domain.context.SysDictContext;
import com.github.platform.core.sys.domain.context.SysDictQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 字典网关
 *
 * @author: yxkong
 * @date: 2023/2/9 5:32 下午
 * @version: 1.0
 */
public interface ISysDictGateway {
    /**缓存前缀*/
    String ALL_PREFIX_COLON = SysCacheKeyPrefix.DICT_TYPE.getWithColon()+"a:";
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c12h;
    /**
     * 分页查询字典数据
     *
     * @param context
     * @return
     */
    PageBean<SysDictDto> query(SysDictQueryContext context);

    /**
     * 新增字典数据
     * @param context
     * @return
     */
    SysDictDto insert(SysDictContext context);

    /**
     * 修改字典数据
     * @param context
     * @return
     */
    void update(SysDictContext context);
    /**
     * 查询对应的dictType中有无对应的key或label
     * @param context
     * @return
     */
    int isExistDict(SysDictContext context);
    /**
     * 删除字典数据
     *
     * @param context
     * @return
     */
    void delete(SysDictContext context);
    /**
     * 通过主键id 获取实体对象
     * @param id
     * @return
     */
    SysDictDto findById(Long id);

    /**
     * 根据字典类型获取数据
     * 将字符串和int类型区分
     *
     * @param dictType 字典类型
     * @param tenantId 租户
     * @return
     */

    List<SysDictDto> findByType(String dictType,Integer tenantId);
}
