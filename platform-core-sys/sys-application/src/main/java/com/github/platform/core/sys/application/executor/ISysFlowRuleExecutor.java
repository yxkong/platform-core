package com.github.platform.core.sys.application.executor;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sys.domain.context.SysFlowRuleContext;
import com.github.platform.core.sys.domain.context.SysFlowRuleQueryContext;
import com.github.platform.core.sys.domain.dto.SysFlowRuleDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 状态机配置规则执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
public interface ISysFlowRuleExecutor {
    /**
    * 查询状态机配置规则列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysFlowRuleDto> query(SysFlowRuleQueryContext context);

    /**
     * g根据业务类型查询
     * @param bizType 业务类型
     * @return
     */
    List<SysFlowRuleDto> findByBizType(String bizType);
    /**
    * 新增状态机配置规则
    * @param context 新增上下文
    */
    String insert(SysFlowRuleContext context);
    /**
    * 根据id查询状态机配置规则明细
    * @param id 主键
    * @return 单条记录
    */
    SysFlowRuleDto findById(Long id);
    /**
    * 修改状态机配置规则
    * @param context 更新上下文
    */
    void update(SysFlowRuleContext context);
    /**
    * 根据id删除状态机配置规则记录
    * @param id 主键
    */
    void delete(Long id);

}