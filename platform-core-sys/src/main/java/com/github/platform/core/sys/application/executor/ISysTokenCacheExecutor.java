package com.github.platform.core.sys.application.executor;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
/**
 * token缓存执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
public interface ISysTokenCacheExecutor {
    /**
    * 查询token缓存列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysTokenCacheDto> query(SysTokenCacheQueryContext context);
    /**
    * 新增token缓存
    * @param context 新增上下文
    */
    String insert(SysTokenCacheContext context);
    /**
    * 根据id查询token缓存明细
    * @param id 主键
    * @return 单条记录
    */
    SysTokenCacheDto findById(Long id);
    /**
    * 修改token缓存
    * @param context 更新上下文
    */
    void update(SysTokenCacheContext context);
    /**
    * 根据id删除token缓存记录
    * @param id 主键
    */
    void delete(Long id);
}