package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordContext;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessApprovalRecordDto;

/**
 * 流程审批记录执行器
 * @author: yxkong
 * @date: 2023/12/27 11:44
 * @version: 1.0
 */
public interface IProcessApprovalRecordExecutor {
    /**
     * 查询流程审批记录列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<ProcessApprovalRecordDto> query(ProcessApprovalRecordQueryContext context);

    /**
     * 新增流程审批记录
     *
     * @param context 新增上下文
     */
    void insert(ProcessApprovalRecordContext context);

    /**
     * 根据id查询流程审批记录明细
     *
     * @param id 主键
     * @return 单条记录
     */
    ProcessApprovalRecordDto findById(Long id);

    /**
     * 修改流程审批记录
     *
     * @param context 更新上下文
     */
    void update(ProcessApprovalRecordContext context);

    /**
     * 根据id删除流程审批记录记录
     *
     * @param id 主键
     */
    void delete(Long id);
}
