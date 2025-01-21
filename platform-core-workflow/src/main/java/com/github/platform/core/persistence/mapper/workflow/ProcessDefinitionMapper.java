package com.github.platform.core.persistence.mapper.workflow;
import com.github.platform.core.workflow.domain.common.entity.ProcessDefinitionBase;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
* 流程管理 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
public interface ProcessDefinitionMapper {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(ProcessDefinitionBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(ProcessDefinitionBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    ProcessDefinitionBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<ProcessDefinitionBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过map参数获取列表
    * @param params 参数map
    * @return List<$FlwProcessManageDO>
    */
    List<ProcessDefinitionBase> findList(Map<String,Object> params);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<ProcessDefinitionBase>
    */
    List<ProcessDefinitionBase> findListBy(ProcessDefinitionBase record);

    /**
     * 查询页查询，需要group by
     * @param record
     * @return
     */
    List<ProcessDefinitionBase> findPageBy(ProcessDefinitionBase record);
    /**
    * 通过实体查询总数
    * @param record 参数实体
    * @return 总数
    */
    long findListByCount(ProcessDefinitionBase record);
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
}