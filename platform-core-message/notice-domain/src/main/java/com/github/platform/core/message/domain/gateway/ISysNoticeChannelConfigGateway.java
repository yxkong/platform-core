package com.github.platform.core.message.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigContext;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.Cacheable;

/**
 * 通知通道配置网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
public interface ISysNoticeChannelConfigGateway {
    /**缓存前缀加冒号*/
    String PREFIX_COLON = "p:m:nc:";
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c12h;
    /**
    * 查询通知通道配置列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysNoticeChannelConfigDto> query(SysNoticeChannelConfigQueryContext context);
    /**
    * 新增通知通道配置
    * @param context 新增上下文
    * @return 返回结果
    */
    SysNoticeChannelConfigDto insert(SysNoticeChannelConfigContext context);
    /**
    * 根据id查询通知通道配置明细
    * @param id 主键
    * @return 结果
    */

    SysNoticeChannelConfigDto findById(Long id);
    /**
     * 根据渠道和租户id查询通知通道配置明细
     * @param channelType 通知通道类型
     * @param tenantId 租户id
     * @return 结果
     */
    SysNoticeChannelConfigDto findChannel(String channelType,Integer tenantId);
    /**
    * 修改通知通道配置
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, SysNoticeChannelConfigDto> update(SysNoticeChannelConfigContext context);
    /**
    * 根据id删除通知通道配置记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int delete(SysNoticeChannelConfigContext context);

}
