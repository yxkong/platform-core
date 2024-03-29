package com.github.platform.core.loadbalancer.infra.service;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.loadbalancer.domain.common.entity.GrayRuleBase;

import java.util.List;
import java.util.Map;
/**
* 灰度规则表服务层，对整个mapper的包装
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
public interface IGrayRuleService  {
    /**
    * 插入实体
    * @param record 实体
    * @return 返回插入结果
    */
    boolean insert(GrayRuleBase record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    boolean updateById(GrayRuleBase record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回单条
    */
    GrayRuleBase findById(Long id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键
    * @return 返回列表
    */
    List<GrayRuleBase> findByIds(Long[] ids);
    /**
    * 通过map参数获取列表
    * @param params 查询参数
    * @return List<GrayRuleBase>
    */
    List<GrayRuleBase> findList(Map<String,Object> params);
    /**
    * 通过map参数获取列表
    * @param record 查询参数
    * @return List<GrayRuleBase>
    */
    List<GrayRuleBase> findListBy(GrayRuleBase record);
    /**
    * 通过map参数获取列表 分页
    * @param params 查询参数
    * @param pageNum 页数
    * @param pageSize 每页大小
    * @return PageInfo<GrayRuleBase> 分页结果
    */
    PageInfo<GrayRuleBase> findPageInfo(Map<String,Object> params,int pageNum,int pageSize);
    /**
    * 通过实体参数获取列表分页
    * @param record 查询实体
    * @param pageNum 页数
    * @param pageSize 每页大小
    * @return PageInfo<GrayRuleBase> 分页结果
    */
    PageInfo<GrayRuleBase> findPageInfo(GrayRuleBase record,int pageNum,int pageSize);
    /**
    * 通过map参数获取 总数
    * @param params 查询参数
    * @return 总数
    */
    long findListCount(Map<String,Object> params);
    /**
    * 通过实体参数获取 总数
    * @param record 查询实体
    * @return 总数
    */
    long findListByCount(GrayRuleBase record);
    /**
    * 通过主键id 删除
    * @param id 主键
    * @return 返回删除结果
    */
    boolean deleteById(Long id);
    /**
    * 批量删除
    * @param ids 主键
    * @return 删除条数
    */
    int deleteByIds(Long[] ids);
    /**
     * 把非本id的启用数据更新为禁用
     * @param id
     */
    int updateOtherOffById(Long id);

    /**
     * 查询一条有效的灰度规则
     * @return
     */
    GrayRuleBase findOne();
}