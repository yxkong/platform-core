package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.workflow.domain.constant.InstanceStatusEnum;
import com.github.platform.core.workflow.domain.context.ProcessInstanceContext;
import com.github.platform.core.workflow.domain.context.ProcessInstanceQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

/**
* 流程实例网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
public interface IProcessInstanceGateway {
    /**
    * 查询流程实例列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<ProcessInstanceDto> query(ProcessInstanceQueryContext context);
    /**
    * 新增流程实例
    * @param context 新增上下文
    * @return 返回结果
    */
    ProcessInstanceDto insert(ProcessInstanceContext context);
    /**
    * 根据id查询流程实例明细
    * @param id 主键
    * @return 结果
    */
    @Cacheable(cacheNames = {CacheConstant.c12h}, key = "'wf:i:' + #id", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    ProcessInstanceDto findById(Long id);
    /**
    * 修改流程实例
    * @param context 修改上下文
    * @return 更新结果
    */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = {CacheConstant.c12h}, key = "'wf:i:' + #context.instanceId", cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = {CacheConstant.c12h}, key = "'wf:i:' + #context.bizNo", cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = {CacheConstant.c12h}, key = "'wf:i:' + #context.id", cacheManager = CacheConstant.cacheManager)
            }
    )
    Pair<Boolean, ProcessInstanceDto> update(ProcessInstanceContext context);

    /**
     * 根据实例id更新流程状态
     * @param instanceId
     * @param status
     */
    @CacheEvict(cacheNames = {CacheConstant.c12h},key = "'wf:i:' + #instanceId",allEntries = true, cacheManager = CacheConstant.cacheManager)
    void updateByInstanceId(String instanceId, InstanceStatusEnum status);
    /**
    * 根据id删除流程实例记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    /**
     * 根据业务编号和流程类型查询实例信息
     * @param bizNo  业务编号，不能为空
     * @param processNo 流程编号，可以为空
     * @return
     */
    @Cacheable(cacheNames = {CacheConstant.c12h}, key = "'wf:i:' + #bizNo", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    ProcessInstanceDto findByBizNoAndProcessNo(String bizNo, String processNo);


    /**
     * 根据实例编号查询流程实例
     * @param instanceNo
     * @return
     */
    ProcessInstanceDto findByInstanceNo(String instanceNo);

    /**
     * 根据实例id获取实例信息
     * @param instanceId
     * @return
     */
    @Cacheable(cacheNames = {CacheConstant.c12h}, key = "'wf:i:' + #instanceId", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    ProcessInstanceDto findByInstanceId(String instanceId);

}
