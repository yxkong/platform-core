package com.github.platform.core.sms.application.executor;

import com.github.platform.core.sms.domain.context.SysSmsServiceProviderContext;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsServiceProviderDto;
import com.github.platform.core.standard.entity.dto.PageBean;

import java.util.List;

/**
 * 服务商执行器
 * @author: yxkong
 * @date: 2023/12/27 11:58
 * @version: 1.0
 */
public interface ISysSmsServiceProviderExecutor {
    /**
     * 查询服务商列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<SysSmsServiceProviderDto> query(SysSmsServiceProviderQueryContext context);

    /**
     * 新增服务商
     *
     * @param context 新增上下文
     * @return 操作结果
     */
    void insert(SysSmsServiceProviderContext context);

    /**
     * 根据id查询服务商明细
     *
     * @param id 主键
     * @return 单条记录
     */
    SysSmsServiceProviderDto findById(Long id);

    /**
     * 修改服务商
     *
     * @param context 更新上下文
     * @return 更新结果
     */
    void update(SysSmsServiceProviderContext context);

    /**
     * 根据id删除服务商记录
     *
     * @param id 主键
     * @return 删除结果
     */
    void delete(Long id);

    List<SysSmsServiceProviderDto> findAll();
}
