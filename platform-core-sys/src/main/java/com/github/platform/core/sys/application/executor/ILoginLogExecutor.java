package com.github.platform.core.sys.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysLoginLogQueryContext;
import com.github.platform.core.sys.domain.dto.SysLoginLogDto;

/**
 * 登录日志执行器
 * @author: yxkong
 * @date: 2023/12/27 11:08
 * @version: 1.0
 */
public interface ILoginLogExecutor {
    /**
     * 查询登录日志列表
     * @param context
     * @return
     */
    PageBean<SysLoginLogDto> query(SysLoginLogQueryContext context);
    /**
     * 根据id查询登录日志明细
     * @param id
     * @return
     */
    SysLoginLogDto findById(Long id);
}
