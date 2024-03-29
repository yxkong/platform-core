package com.github.platform.core.sms.domain.gateway;

import com.github.platform.core.sms.domain.context.SysSmsTemplateContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.entity.SmsTemplateEntity;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

/**
* 短信模板网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.124
* @version 1.0
*/
public interface ISysSmsTemplateGateway {
    /**
    * 查询短信模板列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysSmsTemplateDto> query(SysSmsTemplateQueryContext context);
    /**
    * 新增短信模板
    * @param context 新增上下文
    * @return 返回结果
    */
    SysSmsTemplateDto insert(SysSmsTemplateContext context);
    /**
    * 根据id查询短信模板明细
    * @param id 主键
    * @return 结果
    */
    SysSmsTemplateDto findById(Long id);
    /**
    * 修改短信模板
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, SysSmsTemplateDto> update(SysSmsTemplateContext context);
    /**
    * 根据id删除短信模板记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    /**
     * 根据模板编号获取模板信息
     * @param tempNo
     * @return
     */
    SysSmsTemplateDto findByTempNo(String tempNo);
}
