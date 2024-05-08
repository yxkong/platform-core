package com.github.platform.core.persistence.mapper.sys;

import com.github.platform.core.sys.domain.common.entity.SysUserBase;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysUserMapper  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysUserBase record);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysUserBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysUserBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysUserBase> findByIds(@Param("ids") Long[] ids);

	/**
	 * 通过实体查询
	 * @param record
	 * @return List<SysUserBase>
	 */
	List<SysUserDto> findListBy(SysUserBase record);
	/**
	 * 指定角色查询用户,关联角色表查询
	 * @param sysUser
	 * @param roleKey 角色不能为空
	 * @return
	 */
	List<SysUserDto> findListByRole(@Param("user") SysUserBase sysUser,@NotNull @Param("roleKey") String roleKey);
	/**
	 *  查询多个部门下的用户(用于查询部门下的所有用户)
	 * @param deptIds
	 * @param sysUser
	 * @return
	 */
	List<SysUserDto> findListByDept(@Param("deptIds") Set<Long> deptIds, @Param("user") SysUserBase sysUser);


	/**
	 * 通过map参数获取 总数
	 *
	 * @param record
	 * @return int
	 */
	long findListByCount(SysUserBase record);
	/**
	 * 根据登录名查询
	 *
	 * @param loginName
	 * @return
	 */
	SysUserBase findByLoginName(@Param("loginName") String loginName);


	/**
	 * 根据用户名查询
	 *
	 * @param mobile
	 * @return
	 */
	SysUserBase findByMobile(@Param("mobile") String mobile);

	/**
	 * 根据密钥查询
	 * @param secretKey
	 * @return
	 */
	SysUserBase findBySecretKey(String secretKey);

    /**
     * 修改密码
     *
     * @param record
     * @return
     */
    int modifyPwd(SysUserBase record);

	/**
	 * 根据租户和关键词模糊查询
	 * @param key 登录名模糊
	 * @param tenantId
	 * @return
	 */
	List<SysUserDto> fuzzySearch(@Param("loginName") String key, @Param("tenantId") Integer tenantId);

	/**
	 * 查多个用户，逗号分隔loginName
	 * @param loginNames
	 * @return
	 */
    List<SysUserDto> queryByUsers(@Param("users")List<String> loginNames);

	/**
	 * 根据多个角色key查用户
	 * @param roleKeys
	 * @return
	 */
    List<SysUserDto> queryByRoleKeys(@Param("roleKeys")List<String> roleKeys,@Param("tenantId")Integer tenantId);


}