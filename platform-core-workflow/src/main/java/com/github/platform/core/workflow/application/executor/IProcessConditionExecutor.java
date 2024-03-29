package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.domain.context.ProcessConditionContext;
import com.github.platform.core.workflow.domain.context.ProcessConditionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessConditionDto;

import java.util.List;

/**
 * 流程条件执行器
 * @author: yxkong
 * @date: 2023/12/27 11:46
 * @version: 1.0
 */
public interface IProcessConditionExecutor {
    /**
     * 查询流程条件列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<ProcessConditionDto> query(ProcessConditionQueryContext context);

    /**
     * 新增流程条件
     *
     * @param context 新增上下文
     */
    String insert(ProcessConditionContext context);

    /**
     * 根据id查询流程条件明细
     *
     * @param id 主键
     * @return 单条记录
     */
    ProcessConditionDto findById(Long id);

    /**
     * 修改流程条件
     *
     * @param context 更新上下文
     */
    void update(ProcessConditionContext context);

    /**
     * 根据id删除流程条件记录
     *
     * @param id 主键
     */
    void delete(Long id);

    List<OptionsDto> getByType(String processType);
}
