package com.github.platform.core.persistence.mapper.workflow;
import com.github.platform.core.workflow.domain.common.entity.ProcessInstanceBase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;

/**
* 流程实例 mappper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
public interface ProcessInstanceMapper {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(ProcessInstanceBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(ProcessInstanceBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    ProcessInstanceBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<ProcessInstanceBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过map参数获取列表
    * @param params 参数map
    * @return List<$FlwProcessInstanceDO>
    */
    List<ProcessInstanceBase> findList(Map<String,Object> params);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<ProcessInstanceBase>
    */
    List<ProcessInstanceBase> findListBy(ProcessInstanceBase record);
    /**
    * 通过map参数获取 总数
    * @param params 参数map
    * @return 总数
    */
    long findListCount(Map<String,Object> params);
    /**
    * 通过实体查询总数
    * @param record 参数实体
    * @return 总数
    */
    long findListByCount(ProcessInstanceBase record);
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
     * 根据业务编号和流程类型查询实例信息
     * @param bizNo  业务编号，不能为空
     * @param processNo 流程编号，可以为空
     * @return
     */
    ProcessInstanceBase findByBizNoAndProcessNo(@NotNull @Param("bizNo")String bizNo, @Param("processNo")String processNo);

    /**
     * 根据实例编号查询流程实例
     * @param instanceNo
     * @return
     */
    ProcessInstanceBase findByInstanceNo(@Param("instanceNo")String instanceNo);

    /**
     * 根据实例id获取信息
     * @param instanceId
     * @return
     */
    ProcessInstanceBase findByInstanceId(@Param("instanceId")String instanceId);

    /**
     * 更新实例状态
     * @param instanceId
     * @param status
     * @param endTime
     */
    void updateByInstanceId(@Param("instanceId")String instanceId, @Param("status") Integer status, @Param("endTime")LocalDateTime endTime);
}