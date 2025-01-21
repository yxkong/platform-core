package com.github.platform.core.persistence.mapper.sms;

import com.github.platform.core.sms.domain.common.entity.SysSmsTemplateStatusBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
* 模板厂商 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.326
* @version 1.0
*/
public interface SysSmsTemplateStatusMapper {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(SysSmsTemplateStatusBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(SysSmsTemplateStatusBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    SysSmsTemplateStatusBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<SysSmsTemplateStatusBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<SysSmsTemplateStatusBase>
    */
    List<SysSmsTemplateStatusBase> findListBy(SysSmsTemplateStatusBase record);

    /**
    * 通过实体查询总数
    * @param record 参数实体
    * @return 总数
    */
    long findListByCount(SysSmsTemplateStatusBase record);
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