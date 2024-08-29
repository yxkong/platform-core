package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.sys.domain.context.SysFlowRuleContext;
import com.github.platform.core.sys.domain.context.SysFlowRuleQueryContext;
import com.github.platform.core.sys.domain.dto.SysFlowRuleDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 状态机配置规则网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
public interface ISysFlowRuleGateway {
    /**
    * 查询状态机配置规则列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysFlowRuleDto> query(SysFlowRuleQueryContext context);

    /**
     * 根据业务l类型查询
     * @param bizType
     * @return
     */
    List<SysFlowRuleDto> findByBizType(String bizType);
    /**
    * 新增状态机配置规则
    * @param context 新增上下文
    * @return 返回结果
    */
    SysFlowRuleDto insert(SysFlowRuleContext context);
    /**
    * 根据id查询状态机配置规则明细
    * @param id 主键
    * @return 结果
    */
    SysFlowRuleDto findById(Long id);
    /**
    * 修改状态机配置规则
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, SysFlowRuleDto> update(SysFlowRuleContext context);
    /**
    * 根据id删除状态机配置规则记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int delete(SysFlowRuleContext context);


}
