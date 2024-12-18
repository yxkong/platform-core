package com.github.platform.core.message.domain.gateway;

import com.github.platform.core.message.domain.context.SysNoticeTemplateContext;
import com.github.platform.core.message.domain.context.SysNoticeTemplateQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 消息通知模板网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:24.593
 * @version 1.0
 */
public interface ISysNoticeTemplateGateway {
    /**
    * 查询消息通知模板列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysNoticeTemplateDto> query(SysNoticeTemplateQueryContext context);
    /**
    * 新增消息通知模板
    * @param context 新增上下文
    * @return 返回结果
    */
    SysNoticeTemplateDto insert(SysNoticeTemplateContext context);
    /**
    * 根据id查询消息通知模板明细
    * @param id 主键
    * @return 结果
    */
    SysNoticeTemplateDto findById(Long id);
    /**
     * 根据tempNo查询消息通知模板明细
     * @param tempNo 模板编号
     * @return 结果
     */
    SysNoticeTemplateDto findByTempNo(String tempNo);
    /**
     * 根据eventType 和 租户查询消息通知模板明细
     * @param eventType 事件类型
     * @param tenantId 租户
     * @return 结果
     */
    SysNoticeTemplateDto findEventType(String eventType,Integer tenantId);

    /**
    * 修改消息通知模板
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, SysNoticeTemplateDto> update(SysNoticeTemplateContext context);
    /**
    * 根据id删除消息通知模板记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int delete(SysNoticeTemplateContext context);
}
