package com.github.platform.core.loadbalancer.infra.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.loadbalancer.domain.common.entity.GrayRuleBase;
import com.github.platform.core.loadbalancer.infra.service.IGrayRuleService;
import com.github.platform.core.persistence.mapper.GrayRuleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* 灰度规则表服务层，对整个mapper的包装
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
@Service("grayRuleService")
public class GrayRuleServiceImpl implements IGrayRuleService {

    @Resource
    private GrayRuleMapper grayRuleMapper;

    @Override
    public boolean insert(GrayRuleBase record){
        return grayRuleMapper.insert(record) >0;
    }

    @Override
    public boolean updateById(GrayRuleBase record){
        return grayRuleMapper.updateById(record)>0;
    }

    @Override
    public GrayRuleBase findById(Long id){
        return grayRuleMapper.findById(id);
    }
    @Override
    public List<GrayRuleBase> findByIds(Long[] ids){
        return grayRuleMapper.findByIds(ids);
    }

    @Override
    public List<GrayRuleBase> findList(Map<String,Object> params){
        return  grayRuleMapper.findList(params);
    }
    @Override
    public List<GrayRuleBase> findListBy(GrayRuleBase record){
        return  grayRuleMapper.findListBy(record);
    }

    @Override
    public PageInfo<GrayRuleBase> findPageInfo(Map<String,Object> params, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<GrayRuleBase> list = grayRuleMapper.findList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<GrayRuleBase> findPageInfo(GrayRuleBase record,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<GrayRuleBase> list = grayRuleMapper.findListBy(record);
        return new PageInfo<>(list);
    }

    @Override
    public long findListCount(Map<String,Object> params){
        return grayRuleMapper.findListCount(params);
    }

    @Override
    public long findListByCount(GrayRuleBase record){
        return grayRuleMapper.findListByCount(record);
    }

    @Override
    public boolean deleteById(Long id){
        return	grayRuleMapper.deleteById(id)>0;
    }

    @Override
    public int deleteByIds(Long[] ids){
        return	grayRuleMapper.deleteByIds(ids);
    }

    @Override
    public int updateOtherOffById(Long id) {
        return grayRuleMapper.updateOtherOffById(id);
    }

    @Override
    public GrayRuleBase findOne() {
        return grayRuleMapper.findOne();
    }
}