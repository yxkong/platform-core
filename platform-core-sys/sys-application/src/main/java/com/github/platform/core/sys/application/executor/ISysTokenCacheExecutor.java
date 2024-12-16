package com.github.platform.core.sys.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
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
     * 查询明细
     * @param id
     * @return
     */
    SysTokenCacheDto findById(Long id);
    /**
    * 过期登录操作
    * @param id 主键
    */
    void expire(Long id);


}