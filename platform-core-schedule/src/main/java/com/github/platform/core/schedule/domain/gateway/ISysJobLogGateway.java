package com.github.platform.core.schedule.domain.gateway;

import com.github.platform.core.schedule.domain.context.SysJobLogContext;
import com.github.platform.core.schedule.domain.context.SysJobLogQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
* 任务执行日志网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
public interface ISysJobLogGateway {
    /**
    * 查询任务执行日志列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysJobLogDto> query(SysJobLogQueryContext context);
    /**
    * 新增任务执行日志
    * @param context 新增上下文
    * @return 返回结果
    */
    SysJobLogDto insert(SysJobLogContext context);
    /**
    * 根据id查询任务执行日志明细
    * @param id 主键
    * @return 结果
    */
    SysJobLogDto findById(Long id);

    /**
     * 异步更新执行日志
     * @param context 上下文
     * @return
     */
    int updateAsync(SysJobLogContext context);
    /**
    * 根据id删除任务执行日志记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);
}
