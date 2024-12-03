package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.sys.domain.constant.SysCacheKeyPrefix;
import com.github.platform.core.sys.domain.context.SysCascadeContext;
import com.github.platform.core.sys.domain.context.SysCascadeQueryContext;
import com.github.platform.core.sys.domain.dto.SysCascadeDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 级联表网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-01 20:57:44.667
 * @version 1.0
 */
public interface ISysCascadeGateway {
    /**缓存前缀*/
    String PREFIX =  SysCacheKeyPrefix.CASCADE.getPrefix();
    /**缓存前缀加冒号*/
    String PREFIX_COLON = SysCacheKeyPrefix.CASCADE.getWithColon();
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c12h;
    /**
    * 查询级联表列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysCascadeDto> query(SysCascadeQueryContext context);

    /**
     * 列表查询
     * @param context
     * @return
     */
    List<SysCascadeDto> list(SysCascadeQueryContext context);

    /**
    * 新增级联表
    * @param context 新增上下文
    * @return 返回结果
    */
    SysCascadeDto insert(SysCascadeContext context);
    /**
    * 根据id查询级联表明细
    * @param id 主键
    * @return 结果
    */
    SysCascadeDto findById(Long id);

    /**
     * 根据父级id查询子集
     * @param parentId
     * @return
     */
    List<SysCascadeDto> findByParentId(Long parentId);

    /**
     * 根据code查询
     * @param code
     * @param tenantId
     * @return
     */
    SysCascadeDto findByCode(String code, Integer tenantId);
    /**
    * 修改级联表
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, SysCascadeDto> update(SysCascadeContext context);
    /**
    * 根据id删除级联表记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int delete(SysCascadeContext context);



}
