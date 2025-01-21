package com.github.platform.core.persistence.mapper.workflow;
import com.github.platform.core.workflow.domain.common.entity.FormBase;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
/**
* 表单配置 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
public interface FormMapper {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(FormBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(FormBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    FormBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<FormBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过map参数获取列表
    * @param params 参数map
    * @return List<$FormsDO>
    */
    List<FormBase> findList(Map<String,Object> params);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<FormBase>
    */
    List<FormBase> findListBy(FormBase record);
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
    long findListByCount(FormBase record);
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