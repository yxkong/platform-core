package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.domain.context.FormInfoWrapContext;
import com.github.platform.core.workflow.domain.context.FormQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDetailDto;
import com.github.platform.core.workflow.domain.dto.FormDto;

import java.util.List;

/**
 * 表单配置执行器
 *
 * @author: yxkong
 * @date: 2023/12/27 11:40
 * @version: 1.0
 */
public interface IFormExecutor {
    /**
     * 查询表单配置列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<FormDto> query(FormQueryContext context);

    /**
     * 新增表单配置
     *
     * @param context 新增上下文
     */
    void insert(FormInfoWrapContext context);

    /**
     * 根据id查询表单配置明细
     *
     * @param id 主键
     * @return 单条记录
     */
    FormDetailDto findById(Long id);

    /**
     * 修改表单配置
     *
     * @param context 更新上下文
     */
    void update(FormInfoWrapContext context);

    /**
     * 根据id删除表单配置记录
     *
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 更新表单状态
     *
     * @param id
     */
    void updateStatus(Long id);

    /**
     * 获取所有表单选项
     *
     * @return
     */
    List<OptionsDto> allForms();

}
