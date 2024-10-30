package com.github.platform.core.message.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.message.application.executor.ISysNoticeTemplateExecutor;
import com.github.platform.core.message.domain.context.SysNoticeTemplateContext;
import com.github.platform.core.message.domain.context.SysNoticeTemplateQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeTemplateGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
/**
 * 消息通知模板执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-10-10 10:51:10.862
 * @version 1.0
 */
@Service
@Slf4j
public class SysNoticeTemplateExecutorImpl extends BaseExecutor implements ISysNoticeTemplateExecutor{
    @Resource
    private ISysNoticeTemplateGateway sysNoticeTemplateGateway;
    /**
    * 查询消息通知模板列表
    * @param context 查询上下文
    * @return 分页结果
    */
    @Override
    public PageBean<SysNoticeTemplateDto> query(SysNoticeTemplateQueryContext context){
        return sysNoticeTemplateGateway.query(context);
    };
    /**
    * 新增消息通知模板
    * @param context 新增上下文
    */
    @Override
    public String insert(SysNoticeTemplateContext context){
        SysNoticeTemplateDto record = sysNoticeTemplateGateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        return record.getStrId();
    }
    /**
    * 根据id查询消息通知模板明细
    * @param id 主键
    * @return 单条记录
    */
    @Override
    public SysNoticeTemplateDto findById(Long id) {
        return sysNoticeTemplateGateway.findById(id);
    }
    /**
    * 修改消息通知模板
    * @param context 更新上下文
    */
    @Override
    public void update(SysNoticeTemplateContext context) {
        Pair<Boolean, SysNoticeTemplateDto> update = sysNoticeTemplateGateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    /**
    * 根据id删除消息通知模板记录
    * @param id 主键
    */
    @Override
    public void delete(Long id) {
        /**此处是为了再gateway上做多条件缓存，如果有必要，先查，后设置值*/
        SysNoticeTemplateContext context = SysNoticeTemplateContext.builder().id(id).build();
        int d = sysNoticeTemplateGateway.delete(context);
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
