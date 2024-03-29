package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.workflow.domain.context.ProcessConditionContext;
import com.github.platform.core.workflow.domain.context.ProcessConditionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessConditionDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
* 流程条件网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-30 18:17:25.794
* @version 1.0
*/
public interface IProcessConditionGateway {
    /**
    * 查询流程条件列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<ProcessConditionDto> query(ProcessConditionQueryContext context);
    /**
    * 新增流程条件
    * @param context 新增上下文
    * @return 返回结果
    */
    ProcessConditionDto insert(ProcessConditionContext context);
    /**
    * 根据id查询流程条件明细
    * @param id 主键
    * @return 结果
    */
    ProcessConditionDto findById(Long id);
    /**
    * 修改流程条件
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, ProcessConditionDto> update(ProcessConditionContext context);
    /**
    * 根据id删除流程条件记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    List<ProcessConditionDto> getByType(String processType);
}
