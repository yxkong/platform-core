package com.github.platform.core.persistence.mapper;

import com.github.platform.core.loadbalancer.domain.common.entity.GrayRuleBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 灰度规则表 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
public interface GrayRuleMapper {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(GrayRuleBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(GrayRuleBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    GrayRuleBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<GrayRuleBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过map参数获取列表
    * @param params 参数map
    * @return List<$GrayRuleDO>
    */
    List<GrayRuleBase> findList(Map<String,Object> params);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<GrayRuleBase>
    */
    List<GrayRuleBase> findListBy(GrayRuleBase record);
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
    long findListByCount(GrayRuleBase record);
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
     * 查询一条有效的记录
     * @return
     */
    GrayRuleBase findOne();

    /**
     * 将其他记录状态为1的更新为0
     * @param id
     * @return
     */
    int updateOtherOffById(@Param("id")Long id);
}