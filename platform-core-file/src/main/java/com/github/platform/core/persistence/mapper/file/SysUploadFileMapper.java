package com.github.platform.core.persistence.mapper.file;

import com.github.platform.core.file.domain.common.entity.SysUploadFileBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 上传文件表 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
public interface SysUploadFileMapper {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(SysUploadFileBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(SysUploadFileBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    SysUploadFileBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<SysUploadFileBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<SysUploadFileBase>
    */
    List<SysUploadFileBase> findListBy(SysUploadFileBase record);
    /**
    * 通过实体查询总数
    * @param record 参数实体
    * @return 总数
    */
    Long findListByCount(SysUploadFileBase record);
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
     * 根据fileId查询记录
     * @param storage
     * @param fileId
     * @return
     */
    SysUploadFileBase findByFileId(@Param("storage")String storage,@Param("fileId")String fileId);
}