package com.github.platform.core.schedule.application.executor;

import com.github.platform.core.schedule.domain.context.SysJobLogQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;

/**
 * 任务日志
 * @author: yxkong
 * @date: 2023/12/27 12:02
 * @version: 1.0
 */
public interface ISysJobLogExecutor {
    /**
     * 查询任务执行日志列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<SysJobLogDto> query(SysJobLogQueryContext context);

    /**
     * 根据id查询任务执行日志明细
     *
     * @param id 主键
     * @return 单条记录
     */
    SysJobLogDto findById(Long id);
}
