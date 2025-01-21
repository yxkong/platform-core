package com.github.platform.core.persistence.mapper.dingding;
import com.github.platform.core.dingtalk.domain.common.entity.DingDeptBase;
import java.util.List;

import org.apache.ibatis.annotations.Param;
/**
 * 钉钉部门 mapper
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
public interface DingDeptMapper  {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(DingDeptBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(DingDeptBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    DingDeptBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<DingDeptBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<DingDeptBase>
    */
    List<DingDeptBase> findListBy(DingDeptBase record);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertList(@Param("list")List<DingDeptBase> list);

}