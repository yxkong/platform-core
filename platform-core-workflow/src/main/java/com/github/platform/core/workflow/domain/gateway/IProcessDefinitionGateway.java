package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionContext;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessDefinitionDto;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
* 流程管理网关层，隔离模型和实现
 * 流程定义一旦确定就不会变动，可以缓存时间长一些
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
public interface IProcessDefinitionGateway {
    /**
    * 查询流程管理列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<ProcessDefinitionDto> query(ProcessDefinitionQueryContext context);

    /**
     * 根据审批流编号查询历史流程
     * @param context
     * @return
     */
    PageBean<ProcessDefinitionDto> queryHistory(ProcessDefinitionQueryContext context);
    /**
    * 新增流程管理
    * @param context 新增上下文
    * @return 返回结果
    */
    ProcessDefinitionDto insert(ProcessDefinitionContext context);

    /**
     * 查询流程数据
     * @param processNo
     * @param status
     * @param tenantId
     * @return
     */
//    @Cacheable(cacheNames = {CacheConstant.c8h}, key = "'wf:d:' + #processNo", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    ProcessDefinitionDto findByProcessNo(@NotNull String  processNo, Integer status, Integer tenantId);
    /**
    * 根据id查询流程管理明细
    * @param id 主键
    * @return 结果
    */
    ProcessDefinitionDto findById(Long id);
    /**
    * 修改流程管理
    * @param context 修改上下文
    * @return 更新结果
    */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = {CacheConstant.c8h}, key = "'wf:d:' + #context.processNo",allEntries = true, cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = {CacheConstant.c8h}, key = "'wf:d:' + #context.tenantId+':'+#context.type", cacheManager = CacheConstant.cacheManager),
            }
    )
    Pair<Boolean, ProcessDefinitionDto> update(ProcessDefinitionContext context);
    /**
    * 根据id删除流程管理记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    /**
     * 根据流程编号和版本查询流程定义
     * @param processNo
     * @param version
     * @return
     */
    @Cacheable(cacheNames = {CacheConstant.c8h}, key = "'wf:d:' + #processNo+':'+#version", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    ProcessDefinitionDto findByProcessNo(String processNo, Integer version);
    /**
     * 查询最新的流程定义，不能加缓存
     * @param processNo
     * @return
     */
    ProcessDefinitionDto findLatestByProcessNo(String processNo);

    /**
     * 获取指定类型可用的流程
     * @param  tenantId 指定租户
     * @param processType 流程类型
     * @return
     */
    @Cacheable(cacheNames = {CacheConstant.c8h}, key = "'wf:d:' + #tenantId+':'+#processType",
            cacheManager = CacheConstant.cacheManager, unless = "#result == null or #result.isEmpty()")
    List<ProcessDefinitionDto> findByType(Integer tenantId,String processType);


}
