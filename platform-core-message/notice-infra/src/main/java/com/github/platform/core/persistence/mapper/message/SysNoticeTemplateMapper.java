package com.github.platform.core.persistence.mapper.message;
import com.github.platform.core.message.domain.common.entity.SysNoticeTemplateBase;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * 消息通知模板 mapper
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:24.593
 * @version 1.0
 */
public interface SysNoticeTemplateMapper  {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(SysNoticeTemplateBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(SysNoticeTemplateBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    SysNoticeTemplateBase findById(Long id);
    /**
     * 根据模板编号查找通知模板
     * @param tempNo
     * @return
     */
    SysNoticeTemplateBase findByTempNo(String tempNo);

    /**
     * 根据事件类型和租户查找通知模板
     * @param eventType
     * @param tenantId
     * @return
     */
    SysNoticeTemplateBase findEventType(String eventType, Integer tenantId);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<SysNoticeTemplateBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<SysNoticeTemplateBase>
    */
    List<SysNoticeTemplateBase> findListBy(SysNoticeTemplateBase record);
    /**
    * 通过实体查询总数
    * @param record 参数实体
    * @return 总数
    */
    long findListByCount(SysNoticeTemplateBase record);
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