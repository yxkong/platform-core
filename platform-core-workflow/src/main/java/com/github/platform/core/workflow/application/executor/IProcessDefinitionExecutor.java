package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionContext;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessDefinitionDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 流程管理执行器
 * @author: yxkong
 * @date: 2023/12/27 11:48
 * @version: 1.0
 */
public interface IProcessDefinitionExecutor {
    /**
     * 查询流程管理列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<ProcessDefinitionDto> query(ProcessDefinitionQueryContext context);

    PageBean<ProcessDefinitionDto> queryHistory(ProcessDefinitionQueryContext context);

    /**
     * 新增流程管理(不包含流程内容)
     *
     * @param context 新增上下文
     */
    void insert(ProcessDefinitionContext context);

    /**
     * 根据id查询流程管理明细
     *
     * @param id 主键
     * @return 单条记录
     */
    ProcessDefinitionDto findById(Long id);

    /**
     * 设计流程并更新
     *
     * @param context
     */
    void designAndUpdate(ProcessDefinitionContext context);

    /**
     * 修改流程管理,不更新流程文件
     *
     * @param context 更新上下文
     */
    void update(ProcessDefinitionContext context);

    /**
     * 根据id删除流程管理记录
     *
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 启动工作流
     * 1，处理工作流的xml ，processXmlFile
     * 2，更新工作流的状态（在停用工作流的时候，生成新的版本，id变动）
     *
     * @param id
     */
    void startProcessById(Long id);

    /**
     * 停用工作流
     * 1，当前流程禁用
     * 2，创建一个新的流程，流程状态为草稿
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    void stopProcessById(Long id);

    /**
     * 将指定版本合并为最新
     *
     * @param id
     */
    void rest(Long id);

    /**
     * 获取流程
     * @return
     */
    List<OptionsDto> getProcessOptions(ProcessTypeEnum processTypeEnum);
}
