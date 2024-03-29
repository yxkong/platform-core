package com.github.platform.core.sms.domain.gateway;

import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
* 模板厂商网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.326
* @version 1.0
*/
public interface ISysSmsTemplateStatusGateway {
    /**
    * 查询模板厂商列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysSmsTemplateStatusDto> query(SysSmsTemplateStatusQueryContext context);
    /**
    * 新增模板厂商
    * @param context 新增上下文
    * @return 返回结果
    */
    SysSmsTemplateStatusDto insert(SysSmsTemplateStatusContext context);
    /**
    * 根据id查询模板厂商明细
    * @param id 主键
    * @return 结果
    */
    SysSmsTemplateStatusDto findById(Long id);
    /**
    * 修改模板厂商
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, SysSmsTemplateStatusDto> update(SysSmsTemplateStatusContext context);
    /**
    * 根据id删除模板厂商记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    /**
     * 根据模板编号查询启用的厂商状态
     * @param tempNo
     * @return
     */
    List<SysSmsTemplateStatusDto> findListByTempNo(String tempNo);
}
