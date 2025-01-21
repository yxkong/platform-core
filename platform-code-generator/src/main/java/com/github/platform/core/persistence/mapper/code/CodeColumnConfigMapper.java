package com.github.platform.core.persistence.mapper.code;

import com.github.platform.core.code.domain.common.entity.CodeColumnConfigBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
* 代码生成字段信息存储 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 14:20:40.462
* @version 1.0
*/
public interface CodeColumnConfigMapper  {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(CodeColumnConfigBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(CodeColumnConfigBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    CodeColumnConfigBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<CodeColumnConfigBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<CodeColumnConfigBase>
    */
    List<CodeColumnConfigBase> findListBy(CodeColumnConfigBase record);
    /**
     * 根据表名查询
     * @param tableName
     * @return
     */
    List<CodeColumnConfigBase> findListByTableName(@Param("tableName") String tableName);
    /**
    * 通过实体查询总数
    * @param record 参数实体
    * @return 总数
    */
    long findListByCount(CodeColumnConfigBase record);
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
     * 批量更新
     * @param list
     * @return
     */
    int updateColumnsById(@Param("list")List<CodeColumnConfigBase> list);

    /**
     * 删除指定表名的数据
     * @param tableName
     */
    void deleteByTableName(@Param("tableName")String tableName);
}