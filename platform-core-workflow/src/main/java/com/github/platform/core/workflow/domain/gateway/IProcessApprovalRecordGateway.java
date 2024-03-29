package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordContext;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessApprovalRecordDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
* 流程审批记录网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-09 11:30:36.194
* @version 1.0
*/
public interface IProcessApprovalRecordGateway {
    /**
    * 查询流程审批记录列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<ProcessApprovalRecordDto> query(ProcessApprovalRecordQueryContext context);
    /**
    * 新增流程审批记录
    * @param context 新增上下文
    * @return 返回结果
    */
    ProcessApprovalRecordDto insert(ProcessApprovalRecordContext context);
    /**
    * 根据id查询流程审批记录明细
    * @param id 主键
    * @return 结果
    */
    ProcessApprovalRecordDto findById(Long id);
    /**
    * 修改流程审批记录
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, ProcessApprovalRecordDto> update(ProcessApprovalRecordContext context);
    /**
    * 根据id删除流程审批记录记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    /**
     * 查询用户对应实例的任务
     * @param assignee
     * @param instanceNo
     * @param taskId
     * @return
     */
    ProcessApprovalRecordDto findByUserInstanceNoAndTaskId(String assignee, String instanceNo, String taskId);

    /**
     * 根据流程实例查询审批记录
     * @param instanceId
     * @return
     */
    List<ProcessApprovalRecordDto> findByInstanceId(String instanceId);

}
