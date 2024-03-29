package com.github.platform.core.sms.domain.gateway;

import com.github.platform.core.sms.domain.common.entity.SysSmsLogBase;
import com.github.platform.core.sms.domain.context.SysSmsLogContext;
import com.github.platform.core.sms.domain.context.SmsLogQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

/**
* 短信日志网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-07-04 10:23:45.615
* @version 1.0
*/
public interface ISysSmsLogGateway {
    /**
     * 查询短信日志列表
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<SysSmsLogDto> query(SmsLogQueryContext context);
    /**
     * 新增短信日志
     * @param context 新增上下文
     * @return 返回结果
     */
    SysSmsLogDto insert(SysSmsLogContext context);
    /**
     * 根据id查询短信日志明细
     * @param id 主键
     * @return 结果
     */
    SysSmsLogDto findById(Long id);
    /**
     * 修改短信日志
     * @param context 修改上下文
     * @return 更新结果
     */
    SysSmsLogDto update(SysSmsLogContext context);
    /**
     * 根据id删除短信日志记录
     * @param id 主键id
     * @return 删除结果
     */
    int delete(Long id);

    /**
     * 根据三方id获取
     * @param msgId
     * @return
     */
    SysSmsLogDto findByMsgId(String msgId);
}
