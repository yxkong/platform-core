package com.github.platform.core.persistence.mapper.sys;
import java.util.List;
import java.util.Map;

import com.github.platform.core.sys.domain.common.entity.SysLoginLogBase;
import org.apache.ibatis.annotations.Param;
/**
* 登录日志 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-06-07 14:37:49.624
* @version 1.0
*/
public interface LoginLogMapper  {

    /**
    * 插入实体
    * @param record
    * @return
    */
    int insert(SysLoginLogBase record);
    /**
    * 通过主键id 更新实体
    * @param record
    * @return 1成功  其它失败
    */
    int updateById(SysLoginLogBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id
    * @return
    */
    SysLoginLogBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids
    * @return
    */
    List<SysLoginLogBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过map参数获取列表
    * @param params
    * @return List<$SysLoginLogBase>
    */
    List<SysLoginLogBase> findList(Map<String,Object> params);
    /**
    * 通过实体查询
    * @param record
    * @return List<SysLoginLogBase>
    */
    List<SysLoginLogBase> findListBy(SysLoginLogBase record);
    /**
    * 通过map参数获取 总数
    * @param params
    * @return int
    */
    long findListCount(Map<String,Object> params);
    /**
    * 通过实体查询总数
    * @param record
    * @return int
    */
    long findListByCount(SysLoginLogBase record);
    /**
    * 通过主键id 删除
    * @param id
    * @return
    */
    int deleteById(Long id);
    /**
    * 批量删除
    * @param ids
    * @return
    */
    int deleteByIds(@Param("ids")Long[] ids);
}