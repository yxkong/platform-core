package com.github.platform.core.schedule.application.executor;

import com.github.platform.core.schedule.domain.context.SysJobContext;
import com.github.platform.core.schedule.domain.context.SysJobQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.exception.ApplicationException;
import org.quartz.SchedulerException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务管理执行器
 *
 * @author: yxkong
 * @date: 2023/12/27 12:01
 * @version: 1.0
 */
public interface ISysJobExecutor {
    /**
     * 查询任务管理列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<SysJobDto> query(SysJobQueryContext context);

    /**
     * 根据id查询任务管理明细
     *
     * @param id 主键
     * @return 单条记录
     */
    SysJobDto findById(Long id);

    /**
     * 根据id删除任务管理记录
     *
     * @param id 主键
     * @return 删除结果
     */
    void delete(Long id) throws SchedulerException;

    /**
     * 新增定时任务
     *
     * @param context
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    void addJob(SysJobContext context);

    /**
     * @param context
     * @return
     */
    @Transactional(rollbackFor = {Exception.class, IllegalArgumentException.class})
    void updateJob(SysJobContext context) throws SchedulerException, ApplicationException;

    void triggerJob(Long id,String handlerParam) throws SchedulerException;

    void pauseJob(Long id) throws SchedulerException;

    void resumeJob(Long id) throws SchedulerException;
}
