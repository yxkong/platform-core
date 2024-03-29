package com.github.platform.core.sms.application.executor;

import com.github.platform.core.sms.domain.context.SysSmsTemplateContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.standard.entity.dto.PageBean;

/**
 * 短信模板执行器
 * @author: yxkong
 * @date: 2023/12/27 11:59
 * @version: 1.0
 */
public interface ISysSmsTemplateExecutor {
    /**
     * 查询短信模板列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<SysSmsTemplateDto> query(SysSmsTemplateQueryContext context);

    /**
     * 新增短信模板
     *
     * @param context 新增上下文
     * @return 操作结果
     */
    void insert(SysSmsTemplateContext context);

    /**
     * 根据id查询短信模板明细
     *
     * @param id 主键
     * @return 单条记录
     */
    SysSmsTemplateDto findById(Long id);

    /**
     * 修改短信模板
     *
     * @param context 更新上下文
     * @return 更新结果
     */
    void update(SysSmsTemplateContext context);

    /**
     * 根据id删除短信模板记录
     *
     * @param id 主键
     * @return 删除结果
     */
    void delete(Long id);
}
