package com.github.platform.core.message.application.executor;

import com.github.platform.core.message.domain.context.SysNoticeTemplateContext;
import com.github.platform.core.message.domain.context.SysNoticeTemplateQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
import com.github.platform.core.standard.entity.dto.PageBean;

/**
 * 消息通知模板执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:24.593
 * @version 1.0
 */
public interface ISysNoticeTemplateExecutor {
    /**
    * 查询消息通知模板列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysNoticeTemplateDto> query(SysNoticeTemplateQueryContext context);
    /**
    * 新增消息通知模板
    * @param context 新增上下文
    */
    String insert(SysNoticeTemplateContext context);
    /**
    * 根据id查询消息通知模板明细
    * @param id 主键
    * @return 单条记录
    */
    SysNoticeTemplateDto findById(Long id);
    /**
    * 修改消息通知模板
    * @param context 更新上下文
    */
    void update(SysNoticeTemplateContext context);
    /**
    * 根据id删除消息通知模板记录
    * @param id 主键
    */
    void delete(Long id);
}