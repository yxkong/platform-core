package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sys.application.executor.ISysFlowRuleExecutor;
import com.github.platform.core.sys.domain.context.SysFlowRuleContext;
import com.github.platform.core.sys.domain.context.SysFlowRuleQueryContext;
import com.github.platform.core.sys.domain.dto.SysFlowRuleDto;
import com.github.platform.core.sys.domain.gateway.ISysFlowRuleGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/**
 * 状态机配置规则执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
@Service
@Slf4j
public class SysFlowRuleExecutorImpl extends BaseExecutor implements ISysFlowRuleExecutor{
    @Resource
    private ISysFlowRuleGateway sysFlowRuleGateway;
    /**
    * 查询状态机配置规则列表
    * @param context 查询上下文
    * @return 分页结果
    */
    @Override
    public PageBean<SysFlowRuleDto> query(SysFlowRuleQueryContext context){
        return sysFlowRuleGateway.query(context);
    }

    @Override
    public List<SysFlowRuleDto> findByBizType(String bizType) {
        return sysFlowRuleGateway.findByBizType(bizType);
    }

    /**
    * 新增状态机配置规则
    * @param context 新增上下文
    */
    @Override
    public String insert(SysFlowRuleContext context){
        SysFlowRuleDto record = sysFlowRuleGateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        return record.getStrId();
    }
    /**
    * 根据id查询状态机配置规则明细
    * @param id 主键
    * @return 单条记录
    */
    @Override
    public SysFlowRuleDto findById(Long id) {
        return sysFlowRuleGateway.findById(id);
    }
    /**
    * 修改状态机配置规则
    * @param context 更新上下文
    */
    @Override
    public void update(SysFlowRuleContext context) {
        Pair<Boolean, SysFlowRuleDto> update = sysFlowRuleGateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    /**
    * 根据id删除状态机配置规则记录
    * @param id 主键
    */
    @Override
    public void delete(Long id) {
        /**此处是为了再gateway上做多条件缓存，如果有必要，先查，后设置值*/
        SysFlowRuleContext context = SysFlowRuleContext.builder().id(id).build();
        int d = sysFlowRuleGateway.delete(context);
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
