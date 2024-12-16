package com.github.platform.core.monitor.domain.gateway;

import com.github.platform.core.monitor.domain.context.SysOptLogContext;
import com.github.platform.core.monitor.domain.context.SysOptLogQueryContext;
import com.github.platform.core.monitor.domain.dto.SysOptLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

/**
* 操作日志网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
public interface ISysOptLogGateway {
    /**
    * 查询操作日志列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysOptLogDto> query(SysOptLogQueryContext context);
    /**
    * 新增操作日志
    * @param context 新增上下文
    * @return 返回结果
    */
    Pair<Boolean, String> insert(SysOptLogContext context);
    /**
    * 根据id查询操作日志明细
    * @param id 主键
    * @return 结果
    */
    SysOptLogDto findById(Long id);
    /**
    * 修改操作日志
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, String> update(SysOptLogContext context);
    /**
    * 根据id删除操作日志记录
    * @param id 主键id
    * @return 删除结果
    */
    Pair<Boolean, String> delete(Long id);
}
