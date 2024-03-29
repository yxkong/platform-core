package com.github.platform.core.schedule.domain.gateway;

import com.github.platform.core.schedule.domain.common.entity.SysJobBase;
import com.github.platform.core.schedule.domain.context.SysJobContext;
import com.github.platform.core.schedule.domain.context.SysJobQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

/**
* 任务管理网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
public interface ISysJobGateway {
    /**
    * 查询任务管理列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysJobDto> query(SysJobQueryContext context);
    /**
    * 新增任务管理
    * @param context 新增上下文
    * @return 返回结果
    */
    SysJobDto insert(SysJobContext context);

    /**
    * 根据id查询任务管理明细
    * @param id 主键
    * @return 结果
    */
    SysJobDto findById(Long id);
    /**
    * 修改任务管理
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean,SysJobDto> update(SysJobContext context);
    boolean update(SysJobDto sysJobDto);
    /**
    * 根据id删除任务管理记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);
    SysJobDto jobUnique(SysJobContext context);
}
