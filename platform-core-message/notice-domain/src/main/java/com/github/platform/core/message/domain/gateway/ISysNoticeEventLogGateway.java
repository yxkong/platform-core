package com.github.platform.core.message.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.context.SysNoticeEventLogQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeEventLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 通知事件日志网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
public interface ISysNoticeEventLogGateway {
    /**缓存前缀加冒号*/
    String PREFIX_COLON = "p:m:nel:";
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c1h;
    /**
    * 查询通知事件日志列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysNoticeEventLogDto> query(SysNoticeEventLogQueryContext context);
    /**
    * 新增通知事件日志
    * @param context 新增上下文
    * @return 返回结果
    */
    SysNoticeEventLogDto insert(SysNoticeEventLogContext context);
    /**
    * 根据id查询通知事件日志明细
    * @param id 主键
    * @return 结果
    */
    SysNoticeEventLogDto findById(Long id);
    /**
    * 修改通知事件日志
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, SysNoticeEventLogDto> update(SysNoticeEventLogContext context);
    /**
    * 根据id删除通知事件日志记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int delete(SysNoticeEventLogContext context);

    /**
     * 根据消息id查询记录，去重
     * @param msgId
     * @return
     */
    SysNoticeEventLogDto findByMsgId(String msgId);
}
