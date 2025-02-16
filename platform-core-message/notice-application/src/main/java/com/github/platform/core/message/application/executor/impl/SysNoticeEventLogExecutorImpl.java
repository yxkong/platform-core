package com.github.platform.core.message.application.executor.impl;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.message.application.executor.IMessageNoticeExecutor;
import com.github.platform.core.message.application.executor.ISysNoticeEventLogExecutor;
import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.context.SysNoticeEventLogQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeEventLogDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeEventLogGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.standard.entity.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;
/**
 * 通知事件日志执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
@Service
@Slf4j
public class SysNoticeEventLogExecutorImpl extends SysExecutor implements ISysNoticeEventLogExecutor{
    @Resource
    private ISysNoticeEventLogGateway sysNoticeEventLogGateway;
    @Resource
    private IMessageNoticeExecutor messageNoticeExecutor;
    /**
    * 查询通知事件日志列表
    * @param context 查询上下文
    * @return 分页结果
    */
    @Override
    public PageBean<SysNoticeEventLogDto> query(SysNoticeEventLogQueryContext context){
        context.setTenantId(getTenantId(context));
        return sysNoticeEventLogGateway.query(context);
    }
    /**
    * 新增通知事件日志
    * @param context 新增上下文
    */
    @Override
    public String insert(SysNoticeEventLogContext context){
        context.setTenantId(getTenantId(context));
        SysNoticeEventLogDto record = sysNoticeEventLogGateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        return record.getStrId();
    }
    /**
    * 根据id查询通知事件日志明细
    * @param id 主键
    * @return 单条记录
    */
    @Override
    public SysNoticeEventLogDto findById(Long id) {
        return sysNoticeEventLogGateway.findById(id);
    }
    /**
    * 修改通知事件日志
    * @param context 更新上下文
    */
    @Override
    public void update(SysNoticeEventLogContext context) {
        context.setTenantId(getTenantId(context));
        Pair<Boolean, SysNoticeEventLogDto> update = sysNoticeEventLogGateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    /**
    * 根据id删除通知事件日志记录
    * @param id 主键
    */
    @Override
    public void delete(Long id) {
        /**此处是为了再gateway上做多条件缓存，如果有必要，先查，后设置值*/
        SysNoticeEventLogContext context = SysNoticeEventLogContext.builder().id(id).build();
        int d = sysNoticeEventLogGateway.delete(context);
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }

    @Override
    public void rePush(Long id) {
        SysNoticeEventLogDto dto = sysNoticeEventLogGateway.findById(id);
        if (Objects.isNull(dto)){
            throw exception(ResultStatusEnum.NO_DATA);
        }
        DomainEvent domainEvent = JsonUtils.fromJson(dto.getPayload(), DomainEvent.class);
        messageNoticeExecutor.execute(domainEvent,false);
    }
}
