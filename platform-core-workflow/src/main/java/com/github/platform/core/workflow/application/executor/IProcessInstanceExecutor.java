package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.ProcessInstanceContext;
import com.github.platform.core.workflow.domain.context.ProcessInstanceQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessRunContext;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;

import java.util.List;

/**
 * 流程实例执行器
 *
 * @author: yxkong
 * @date: 2023/12/27 11:51
 * @version: 1.0
 */
public interface IProcessInstanceExecutor {
    /**
     * 查询流程实例列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<ProcessInstanceDto> query(ProcessInstanceQueryContext context);

    /**
     * 新增流程实例
     *
     * @param context 新增上下文
     */
    void insert(ProcessInstanceContext context);

    /**
     * 根据id查询流程实例明细
     *
     * @param id 主键
     * @return 单条记录
     */
    ProcessInstanceDto findById(Long id);

    /**
     * 更新流程状态
     *
     * @param id     更新上下文
     * @param reason 原因
     */
    void updateStatus(Long id, String reason);

    /**
     * 根据id删除流程实例记录
     *
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 创建流程，包含表单信息
     * @param context
     * @param formdataList
     */
    void createProcessInstanceWithFormData(ProcessRunContext context, List<FormDataContext> formdataList);

    /**
     * 创建流程实例，业务创建时，必须有业务编号，在流程模块创建的，可为空
     * @param context
     */
    void createProcessInstance(ProcessRunContext context);

    /**
     * 挂起/暂停流程
     * @param id
     * @param reason
     */
    void suspend(Long id,String reason);
    /**
     * 挂起/暂停流程
     * @param bizNo
     * @param reason
     */
    void suspend(String bizNo,String reason);

    /**
     * 恢复流程
     * @param id
     * @param reason
     */
    void resume(Long id,String reason);
    /**
     * 恢复流程
     * @param bizNo
     * @param reason
     */
    void resume(String bizNo,String reason);

    /**
     * 终止或取消流程
     * @param id
     */
    void stop(Long id,String reason);
    /**
     * 终止或取消流程
     * @param bizNo
     */
    void stop(String bizNo,String reason);


}
