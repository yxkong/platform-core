package com.github.platform.core.message.application.executor;

import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.context.SysNoticeEventLogQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeEventLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;

/**
 * 通知事件日志执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
public interface ISysNoticeEventLogExecutor {
    /**
    * 查询通知事件日志列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysNoticeEventLogDto> query(SysNoticeEventLogQueryContext context);
    /**
    * 新增通知事件日志
    * @param context 新增上下文
    */
    String insert(SysNoticeEventLogContext context);
    /**
    * 根据id查询通知事件日志明细
    * @param id 主键
    * @return 单条记录
    */
    SysNoticeEventLogDto findById(Long id);
    /**
    * 修改通知事件日志
    * @param context 更新上下文
    */
    void update(SysNoticeEventLogContext context);
    /**
    * 根据id删除通知事件日志记录
    * @param id 主键
    */
    void delete(Long id);

    void rePush(Long id);
}