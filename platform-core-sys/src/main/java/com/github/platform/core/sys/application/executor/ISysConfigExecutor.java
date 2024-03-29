package com.github.platform.core.sys.application.executor;

import com.github.platform.core.cache.domain.entity.ConfigEntity;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysConfigContext;
import com.github.platform.core.sys.domain.context.SysConfigQueryContext;
import com.github.platform.core.sys.domain.dto.SysConfigDto;

/**
 * 配置执行器
 *
 * @author: yxkong
 * @date: 2023/12/27 11:25
 * @version: 1.0
 */
public interface ISysConfigExecutor {
    /**
     * 查询配置
     * @param context
     * @return
     */
    PageBean<SysConfigDto> query(SysConfigQueryContext context);

    /**
     * 新增配置
     * @param context
     */
    void insert(SysConfigContext context);

    /**
     * 更新配置
     * @param context
     */

    void update(SysConfigContext context);

    /**
     * 删除配置
     * @param id
     */

    void delete(Long id);

    /**
     * 获取配置
     * @param key
     * @return
     */
    SysConfigDto getConfig(String key);

    /**
     * 重载配置，为空重载全部
     * @param key
     */
    void reload(String key);
}
