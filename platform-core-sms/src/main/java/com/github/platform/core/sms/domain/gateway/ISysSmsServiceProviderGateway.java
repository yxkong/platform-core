package com.github.platform.core.sms.domain.gateway;

import com.github.platform.core.sms.domain.context.SysSmsServiceProviderContext;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsServiceProviderDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
* 服务商网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
public interface ISysSmsServiceProviderGateway {
    /**
    * 查询服务商列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysSmsServiceProviderDto> query(SysSmsServiceProviderQueryContext context);
    /**
    * 新增服务商
    * @param context 新增上下文
    * @return 返回结果
    */
    SysSmsServiceProviderDto insert(SysSmsServiceProviderContext context);
    /**
    * 根据id查询服务商明细
    * @param id 主键
    * @return 结果
    */
    SysSmsServiceProviderDto findById(Long id);
    /**
    * 修改服务商
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, SysSmsServiceProviderDto> update(SysSmsServiceProviderContext context);
    /**
    * 根据id删除服务商记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    List<SysSmsServiceProviderDto> findAll();

    /**
     * 根据厂商编号查询
     * @param proNo
     * @return
     */
    SysSmsServiceProviderDto findByProNo(String proNo);
}
