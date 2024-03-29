package com.github.platform.core.sms.application.executor;

import com.github.platform.core.sms.domain.context.SmsLogQueryContext;
import com.github.platform.core.sms.domain.context.SysSmsLogContext;
import com.github.platform.core.sms.domain.dto.SysSmsLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;

/**
 * 短信日志执行器
 * @author: yxkong
 * @date: 2023/12/27 11:57
 * @version: 1.0
 */
public interface ISysSmsLogExecutor {
    /**
     * 查询短信日志列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<SysSmsLogDto> query(SmsLogQueryContext context);

    /**
     * 新增短信日志
     *
     * @param context 新增上下文
     * @return 操作结果
     */
    void insert(SysSmsLogContext context);

    /**
     * 根据id查询短信日志明细
     *
     * @param id 主键
     * @return 单条记录
     */
    SysSmsLogDto findById(Long id);

    /**
     * 修改短信日志
     *
     * @param context 更新上下文
     * @return 更新结果
     */
    void update(SysSmsLogContext context);

    /**
     * 根据id删除短信日志记录
     *
     * @param id 主键
     * @return 删除结果
     */
    void delete(Long id);
}
