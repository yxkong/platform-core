//package com.github.platform.core.sys.infra.service.sys;
//
//import java.util.List;
//import java.util.Map;
//
//import com.github.pagehelper.PageInfo;
//import com.github.platform.core.persistence.entity.sys.DingDepartmentDO;
///**
// * @author 自定义代码生成器
// * @time 2023-06-16 10:18:09
// * @version 1.0
// *
// **/
//
//public interface IDingDepartmentService  {
//
//	/**
//	 * 插入实体
//	 * @param record
//	 * @return
//	 */
//	int insert(DingDepartmentDO record);
//	/**
//	* 通过主键id 更新实体
//	* @param record
//	* @return 1成功  其它失败
//	*/
//	int updateById(DingDepartmentDO record);
//	/**
//	 * 通过主键id 获取实体对象
//	 * @param id
//	 * @return
//	 */
//	DingDepartmentDO findById(Long id);
//	/**
//	* 通过主键ids 获取多个实体对象(最多200条)
//	* @param ids
//	* @return
//	*/
//	List<DingDepartmentDO> findByIds(Long[] ids);
//	/**
//	* 通过map参数获取列表
//	* @param params 查询参数
//	* @return List<DingDepartmentDO>
//	*/
//	List<DingDepartmentDO> findList(Map<String,Object> params);
//	/**
//	* 通过map参数获取列表
//	* @param record 查询参数
//	* @return List<DingDepartmentDO>
//	*/
//	List<DingDepartmentDO> findListBy(DingDepartmentDO record);
//	/**
//	 * 通过map参数获取列表 分页
//	 * @param params 查询参数
//	 * @param pageNum 页数
//	 * @param pageSize 每页大小
//	 * @return PageInfo<DingDepartmentDO>
//	 */
//	PageInfo<DingDepartmentDO> findPageInfo(Map<String,Object> params,int pageNum,int pageSize);
//	/**
//	* 通过实体参数获取列表分页
//	* @param record
//	* @param pageNum 页数
//	* @param pageSize 每页大小
//	* @return PageInfo<DingDepartmentDO>
//	*/
//	PageInfo<DingDepartmentDO> findPageInfo(DingDepartmentDO record,int pageNum,int pageSize);
//	/**
//	* 通过map参数获取 总数
//	* @param params
//	* @return int
//	*/
//	int findListCount(Map<String,Object> params);
//	/**
//	* 通过主键id 删除
//	* @param id
//	* @return
//	*/
//	int deleteById(Long id);
//	/**
//	* 批量删除
//	* @param ids
//	* @return
//	*/
//	int deleteByIds(Long[] ids);
//
//	/**
//	 * 批量插入
//	 * @param list
//	 * @return
//	 */
//	int insertBatch(List<DingDepartmentDO> list);
//
//	List<DingDepartmentDO> findListByDeptId(Long deptId);
//}