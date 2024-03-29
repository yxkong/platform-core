//package com.github.platform.core.sys.infra.service.impl.sys;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.github.platform.core.persistence.entity.sys.DingUserInfoDO;
//import com.github.platform.core.persistence.mapper.sys.DingUserInfoMapper;
//import com.github.platform.core.sys.infra.service.sys.IDingUserInfoService;
///**
// * @author 自定义代码生成器
// * @time 2023-06-16 10:20:03
// * @version 1.0
// *
// **/
//@Service("dingUserInfoService")
//public class DingUserInfoServiceImpl  implements IDingUserInfoService{
//
//	@Resource
//	private DingUserInfoMapper dingUserInfoMapper;
//
//	@Override
//	public int insert(DingUserInfoDO record){
//		return dingUserInfoMapper.insert(record);
//	}
//
//	@Override
//	public int updateById(DingUserInfoDO record){
//		return dingUserInfoMapper.updateById(record);
//	}
//
//	@Override
//	public DingUserInfoDO findById(Long id){
//		return dingUserInfoMapper.findById(id);
//	}
//
//	@Override
//	public List<DingUserInfoDO> findByIds(Long[] ids){
//		return dingUserInfoMapper.findByIds(ids);
//	}
//
//	@Override
//	public List<DingUserInfoDO> findList(Map<String,Object> params){
//		return  dingUserInfoMapper.findList(params);
//	}
//
//	@Override
//	public List<DingUserInfoDO> findListBy(DingUserInfoDO record){
//		return  dingUserInfoMapper.findListBy(record);
//	}
//
//	@Override
//	public PageInfo<DingUserInfoDO> findPageInfo(Map<String,Object> params,int pageNum,int pageSize){
//		PageHelper.startPage(pageNum,pageSize);
//		List<DingUserInfoDO> list = dingUserInfoMapper.findList(params);
//		return new PageInfo<>(list);
//	}
//
//	@Override
//	public PageInfo<DingUserInfoDO> findPageInfo(DingUserInfoDO record,int pageNum,int pageSize){
//		PageHelper.startPage(pageNum,pageSize);
//		List<DingUserInfoDO> list = dingUserInfoMapper.findListBy(record);
//		return new PageInfo<>(list);
//	}
//
//	@Override
//	public int findListCount(Map<String,Object> params){
//		return  dingUserInfoMapper.findListCount(params);
//	}
//
//	@Override
//	public int deleteById(Long id){
//		return	dingUserInfoMapper.deleteById(id);
//	}
//
//	@Override
//	public int deleteByIds(Long[] ids){
//		return	dingUserInfoMapper.deleteByIds(ids);
//	}
//
//	@Override
//	public int insertBatch(List<DingUserInfoDO> list) {
//		return dingUserInfoMapper.insertBatch(list);
//	}
//
//	@Override
//	public DingUserInfoDO findByName(String name) {
//		return dingUserInfoMapper.findByName(name);
//	}
//
//
//}