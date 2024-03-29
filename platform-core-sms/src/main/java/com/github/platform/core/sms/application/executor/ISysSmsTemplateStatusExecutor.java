package com.github.platform.core.sms.application.executor;

import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
import com.github.platform.core.standard.entity.dto.PageBean;

/**
 * 模板厂商执行器
 * @author: yxkong
 * @date: 2023/12/27 11:59
 * @version: 1.0
 */
public interface ISysSmsTemplateStatusExecutor {
    /**
     * 查询模板厂商列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<SysSmsTemplateStatusDto> query(SysSmsTemplateStatusQueryContext context);

    /**
     * 新增模板厂商
     *
     * @param context 新增上下文
     * @return 操作结果
     */
    String insert(SysSmsTemplateStatusContext context);

    /**
     * 根据id查询模板厂商明细
     *
     * @param id 主键
     * @return 单条记录
     */
    SysSmsTemplateStatusDto findById(Long id);

    /**
     * 修改模板厂商
     *
     * @param context 更新上下文
     * @return 更新结果
     */
    void update(SysSmsTemplateStatusContext context);

    /**
     * 根据id删除模板厂商记录
     *
     * @param id 主键
     * @return 删除结果
     */
    void delete(Long id);
}
