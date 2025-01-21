package com.github.platform.core.persistence.mapper.sys;

import com.github.platform.core.sys.domain.common.entity.SysThirdUserBase;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
* 三方用户 mapper
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-31 16:04:49.640
* @version 1.0
*/
public interface SysThirdUserMapper {

    /**
    * 插入实体
    * @param record
    * @return
    */
    int insert(SysThirdUserBase record);

    /**
     * 批量插入
     * @param record
     * @return
     */
    int insertList(@Param("list")List<SysThirdUserBase> record);
    /**
    * 通过主键id 更新实体
    * @param record
    * @return 1成功  其它失败
    */
    int updateById(SysThirdUserBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id
    * @return
    */
    SysThirdUserBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids
    * @return
    */
    List<SysThirdUserBase> findByIds(@Param("ids") Long[] ids);

    /**
     * 根据渠道+手机号查询
     * @param channel
     * @param mobiles
     * @return
     */
    List<SysThirdUserBase> queryUsersByMobile(@Param("channel") String channel,@Param("mobiles") List<String> mobiles,@Param("tenantId") Integer tenantId);

    /**
     * 根据渠道+登录名查询
     * @param channel
     * @param users  登录名称
     * @return
     */
    List<SysThirdUserDto> queryUsersByLoginName(@Param("channel") String channel, @Param("users") List<String> users, @Param("tenantId") Integer tenantId);

    /**
    * 通过实体查询
    * @param record
    * @return List<SysThirdUserBase>
    */
    List<SysThirdUserBase> findListBy(SysThirdUserBase record);
    /**
    * 通过实体查询总数
    * @param record
    * @return int
    */
    long findListByCount(SysThirdUserBase record);
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

    SysThirdUserBase findByChannel(@Param("openId")String openId, @Param("channel")String channel,@Param("tenantId") Integer tenantId);
}