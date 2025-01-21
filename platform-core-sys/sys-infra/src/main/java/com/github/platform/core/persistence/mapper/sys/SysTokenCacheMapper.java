package com.github.platform.core.persistence.mapper.sys;
import com.github.platform.core.sys.domain.common.entity.SysTokenCacheBase;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
/**
 * token缓存 mapper
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
public interface SysTokenCacheMapper  {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(SysTokenCacheBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(SysTokenCacheBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    SysTokenCacheBase findById(Long id);

    /**
     * 根据token查询对象
     * @param token
     * @return
     */
    SysTokenCacheBase findByToken(String token);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<SysTokenCacheBase> findByIds(@Param("ids") Long[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<SysTokenCacheBase>
    */
    List<SysTokenCacheBase> findListBy(SysTokenCacheBase record);
    /**
    * 通过实体查询总数
    * @param record 参数实体
    * @return 总数
    */
    long findListByCount(SysTokenCacheBase record);
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
     *
     * @param tenantId
     * @param loginName
     * @return
     */
    SysTokenCacheBase findByLoginName(@Param("tenantId")Integer tenantId, @Param("loginName")String loginName);
}