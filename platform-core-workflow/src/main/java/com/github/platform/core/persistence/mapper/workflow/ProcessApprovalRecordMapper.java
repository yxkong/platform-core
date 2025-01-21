package com.github.platform.core.persistence.mapper.workflow;
import com.github.platform.core.workflow.domain.common.entity.ProcessApprovalRecordBase;
import java.util.List;
import java.util.Map;

import com.github.platform.core.workflow.domain.dto.ProcessApprovalRecordDto;
import org.apache.ibatis.annotations.Param;
/**
* 流程审批记录 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-09 11:30:36.194
* @version 1.0
*/
public interface ProcessApprovalRecordMapper  {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(ProcessApprovalRecordBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(ProcessApprovalRecordBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    ProcessApprovalRecordBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<ProcessApprovalRecordBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<ProcessApprovalRecordBase>
    */
    List<ProcessApprovalRecordBase> findListBy(ProcessApprovalRecordBase record);
    /**
    * 通过实体查询总数
    * @param record 参数实体
    * @return 总数
    */
    long findListByCount(ProcessApprovalRecordBase record);
    /**
    * 通过主键id 删除
    * @param id 主键
    * @return 删除结果，1成功，0失败
    */
    int deleteById(Long id);
    /**
    * 批量删除
    * @param ids 主键
    * @return 删除的条数
    */
    int deleteByIds(@Param("ids")Long[] ids);

    /**
     * 根据用户和实例编号，任务id查询记录
     * @param assignee
     * @param instanceNo
     * @param taskId
     * @return
     */
    ProcessApprovalRecordDto findByUserInstanceNoAndTaskId(@Param("assignee")String assignee, @Param("instanceNo")String instanceNo, @Param("taskId")String taskId);
}