package com.github.platform.core.monitor.infra.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.monitor.domain.common.entity.SysOptLogBase;
import com.github.platform.core.monitor.infra.service.ISysOptLogService;
import com.github.platform.core.persistence.mapper.monitor.SysOptLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* 操作日志服务层，对整个mapper的包装
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Service("sysOptLogService")
public class SysOptLogServiceImpl implements ISysOptLogService {

    @Resource
    private SysOptLogMapper sysOptLogMapper;

    @Override
    public boolean insert(SysOptLogBase record){
        return sysOptLogMapper.insert(record) >0;
    }

    @Override
    public boolean updateById(SysOptLogBase record){
        return sysOptLogMapper.updateById(record)>0;
    }

    @Override
    public SysOptLogBase findById(Long id){
        return sysOptLogMapper.findById(id);
    }
    @Override
    public List<SysOptLogBase> findByIds(Long[] ids){
        return sysOptLogMapper.findByIds(ids);
    }

    @Override
    public List<SysOptLogBase> findList(Map<String,Object> params){
        return  sysOptLogMapper.findList(params);
    }
    @Override
    public List<SysOptLogBase> findListBy(SysOptLogBase record){
        return  sysOptLogMapper.findListBy(record);
    }

    @Override
    public PageInfo<SysOptLogBase> findPageInfo(Map<String,Object> params, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<SysOptLogBase> list = sysOptLogMapper.findList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<SysOptLogBase> findPageInfo(SysOptLogBase record,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<SysOptLogBase> list = sysOptLogMapper.findListBy(record);
        return new PageInfo<>(list);
    }

    @Override
    public long findListCount(Map<String,Object> params){
        return sysOptLogMapper.findListCount(params);
    }

    @Override
    public long findListByCount(SysOptLogBase record){
        return sysOptLogMapper.findListByCount(record);
    }

    @Override
    public boolean deleteById(Long id){
        return	sysOptLogMapper.deleteById(id)>0;
    }

    @Override
    public int deleteByIds(Long[] ids){
        return	sysOptLogMapper.deleteByIds(ids);
    }
}