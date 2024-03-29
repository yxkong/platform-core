package com.github.platform.core.sys.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysDictTypeContext;
import com.github.platform.core.sys.domain.context.SysDictTypeQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictTypeDto;

/**
 * 字典类型执行器
 * @Author: yxkong
 * @Date: 2024/1/28 12:41
 * @version: 1.0
 */
public interface ISysDictTypeExecutor {
    /**
     * 分页查询字典
     * @param context
     * @return
     */
    PageBean<SysDictTypeDto> query(SysDictTypeQueryContext context);

    /**
     * 新增字典类型
     * @param context
     */
    void insert(SysDictTypeContext context);

    /**
     * 更新字典类型
     * @param context
     */

    void update(SysDictTypeContext context);

    /**
     * 删除类型
     * @param id
     */
    void delete(Long id);

    /**
     * 重载缓存，为空重载全部
     * @param type
     */
    void reload(String type);
}
