//package com.github.platform.core.sys.infra.service.impl.sys;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.github.platform.core.persistence.entity.sys.DingDepartmentDO;
//import com.github.platform.core.persistence.mapper.sys.DingDepartmentMapper;
//import com.github.platform.core.sys.infra.service.sys.IDingDepartmentService;
///**
// * @author 自定义代码生成器
// * @time 2023-06-16 10:18:09
// * @version 1.0
// *
// **/
//@Service("dingDepartmentService")
//public class DingDepartmentServiceImpl  implements IDingDepartmentService{
//
//	@Resource
//	private DingDepartmentMapper dingDepartmentMapper;
//
//	@Override
//	public int insert(DingDepartmentDO record){
//		return dingDepartmentMapper.insert(record);
//	}
//
//	@Override
//	public int updateById(DingDepartmentDO record){
//		return dingDepartmentMapper.updateById(record);
//	}
//
//	@Override
//	public DingDepartmentDO findById(Long id){
//		return dingDepartmentMapper.findById(id);
//	}
//
//	@Override
//	public List<DingDepartmentDO> findByIds(Long[] ids){
//		return dingDepartmentMapper.findByIds(ids);
//	}
//
//	@Override
//	public List<DingDepartmentDO> findList(Map<String,Object> params){
//		return  dingDepartmentMapper.findList(params);
//	}
//
//	@Override
//	public List<DingDepartmentDO> findListBy(DingDepartmentDO record){
//		return  dingDepartmentMapper.findListBy(record);
//	}
//
//	@Override
//	public PageInfo<DingDepartmentDO> findPageInfo(Map<String,Object> params,int pageNum,int pageSize){
//		PageHelper.startPage(pageNum,pageSize);
//		List<DingDepartmentDO> list = dingDepartmentMapper.findList(params);
//		return new PageInfo<>(list);
//	}
//
//	@Override
//	public PageInfo<DingDepartmentDO> findPageInfo(DingDepartmentDO record,int pageNum,int pageSize){
//		PageHelper.startPage(pageNum,pageSize);
//		List<DingDepartmentDO> list = dingDepartmentMapper.findListBy(record);
//		return new PageInfo<>(list);
//	}
//
//	@Override
//	public int findListCount(Map<String,Object> params){
//		return  dingDepartmentMapper.findListCount(params);
//	}
//
//	@Override
//	public int deleteById(Long id){
//		return	dingDepartmentMapper.deleteById(id);
//	}
//
//	@Override
//	public int deleteByIds(Long[] ids){
//		return	dingDepartmentMapper.deleteByIds(ids);
//	}
//
//	@Override
//	public int insertBatch(List<DingDepartmentDO> list) {
//		return dingDepartmentMapper.insertBatch(list);
//	}
//
//	@Override
//	public List<DingDepartmentDO> findListByDeptId(Long deptId) {
//		Map map = new HashMap();
//    	if(Objects.nonNull(deptId) && deptId != 0L){
//    		map.put("deptId", deptId);
//    	}
//		return dingDepartmentMapper.findList(map);
//	}
//}