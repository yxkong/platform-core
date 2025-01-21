package com.github.platform.core.persistence.mapper.sys;
import com.github.platform.core.sys.domain.common.entity.SysUserConfigBase;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * 用户配置 mapper
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
public interface SysUserConfigMapper  {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(SysUserConfigBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(SysUserConfigBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    SysUserConfigBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<SysUserConfigBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<SysUserConfigBase>
    */
    List<SysUserConfigBase> findListBy(SysUserConfigBase record);

}