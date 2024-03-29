package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.context.ProcessDetailQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessQueryContext;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import com.github.platform.core.workflow.domain.dto.ProcessDetailDto;
import com.github.platform.core.workflow.domain.dto.ProcessDto;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 流程执行器
 * @author: yxkong
 * @date: 2023/12/27 11:49
 * @version: 1.0
 */
public interface IProcessExecutor {
    /**
     * 查询待办
     *
     * @param context
     * @return
     */
    PageBean<ProcessDto> queryTodo(ProcessQueryContext context);

    /**
     * 外部查询流程详情，项目使用
     *
     * @param bizNo
     * @return
     */
    ProcessDetailDto queryDetail(String bizNo);

    /**
     * 获取当前任务流程定义key
     * @param actives
     * @param instanceId
     * @param loginName
     * @return
     */

    String getCurrentTaskDefinitionKey(Set<String> actives, String instanceId, String loginName);

    /**
     * 根据指定类型获取候选
     * @param bizNo
     * @param taskKey
     * @param assigneeType
     * @return
     */
    @Cacheable(cacheNames = {CacheConstant.c12h}, key = "'pm:p:' + #bizNo + ':' + #taskKey+':'+#assigneeType", cacheManager = CacheConstant.cacheManager, unless = "#result == null or #result == ''")
    String getCandidate(String bizNo, String taskKey, String assigneeType);

    /**
     * 查询详情
     *
     * @param context
     * @return
     */
    ProcessDetailDto queryDetail(ProcessDetailQueryContext context);

    /**
     * 获取节点扩展属性
     * @param bizNo  业务编号
     * @param taskKey  节点key
     * @return
     */
    @Cacheable(cacheNames = {CacheConstant.c12h}, key = "'pm:p:' + #bizNo + ':' + #taskKey", cacheManager = CacheConstant.cacheManager, unless = "#result == null or #result.size() == 0")
    Map<String,String> getTaskExtendProperty(String bizNo,String taskKey);

    /**
     * 查询流程表单
     * @param processNo
     * @return
     */
    List<FormInfoDto> createQuery(String processNo);
}
