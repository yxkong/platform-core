package com.github.platform.core.message.application.executor.impl;

import com.github.platform.core.message.application.executor.ISysNoticeChannelConfigExecutor;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigContext;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeChannelConfigGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.auth.application.executor.SysExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;
/**
 * 通知通道配置执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
@Service
@Slf4j
public class SysNoticeChannelConfigExecutorImpl extends SysExecutor implements ISysNoticeChannelConfigExecutor{
    @Resource
    private ISysNoticeChannelConfigGateway sysNoticeChannelConfigGateway;
    /**
    * 查询通知通道配置列表
    * @param context 查询上下文
    * @return 分页结果
    */
    @Override
    public PageBean<SysNoticeChannelConfigDto> query(SysNoticeChannelConfigQueryContext context){
        context.setTenantId(getTenantId(context));
        return sysNoticeChannelConfigGateway.query(context);
    }
    /**
    * 新增通知通道配置
    * @param context 新增上下文
    */
    @Override
    public String insert(SysNoticeChannelConfigContext context){
        context.setTenantId(getTenantId(context));
        SysNoticeChannelConfigDto record = sysNoticeChannelConfigGateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        return record.getStrId();
    }
    /**
    * 根据id查询通知通道配置明细
    * @param id 主键
    * @return 单条记录
    */
    @Override
    public SysNoticeChannelConfigDto findById(Long id) {
        return sysNoticeChannelConfigGateway.findById(id);
    }
    /**
    * 修改通知通道配置
    * @param context 更新上下文
    */
    @Override
    public void update(SysNoticeChannelConfigContext context) {
        context.setTenantId(getTenantId(context));
        Pair<Boolean, SysNoticeChannelConfigDto> update = sysNoticeChannelConfigGateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    /**
    * 根据id删除通知通道配置记录
    * @param id 主键
    */
    @Override
    public void delete(Long id) {
        SysNoticeChannelConfigDto dto = sysNoticeChannelConfigGateway.findById(id);
        SysNoticeChannelConfigContext context = SysNoticeChannelConfigContext.builder().id(id).tenantId(dto.getTenantId()).channelType(dto.getChannelType()).build();
        int d = sysNoticeChannelConfigGateway.delete(context);
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
