package com.github.platform.core.message.application.executor;

import com.github.platform.core.message.domain.context.SysNoticeChannelConfigContext;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
import com.github.platform.core.standard.entity.dto.PageBean;

/**
 * 通知通道配置执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
public interface ISysNoticeChannelConfigExecutor {
    /**
    * 查询通知通道配置列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysNoticeChannelConfigDto> query(SysNoticeChannelConfigQueryContext context);
    /**
    * 新增通知通道配置
    * @param context 新增上下文
    */
    String insert(SysNoticeChannelConfigContext context);
    /**
    * 根据id查询通知通道配置明细
    * @param id 主键
    * @return 单条记录
    */
    SysNoticeChannelConfigDto findById(Long id);
    /**
    * 修改通知通道配置
    * @param context 更新上下文
    */
    void update(SysNoticeChannelConfigContext context);
    /**
    * 根据id删除通知通道配置记录
    * @param id 主键
    */
    void delete(Long id);
}