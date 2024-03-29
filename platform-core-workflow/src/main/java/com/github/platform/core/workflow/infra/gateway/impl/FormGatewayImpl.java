package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.workflow.domain.common.entity.FormBase;
import com.github.platform.core.workflow.domain.context.FormContext;
import com.github.platform.core.workflow.domain.context.FormQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDetailDto;
import com.github.platform.core.workflow.domain.dto.FormDto;
import com.github.platform.core.workflow.domain.gateway.IFormGateway;
import com.github.platform.core.persistence.mapper.workflow.FormMapper;
import com.github.platform.core.workflow.infra.convert.FormInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* 表单配置网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
@Service
public class FormGatewayImpl extends BaseGatewayImpl implements IFormGateway {

    @Resource
    private FormMapper formMapper;
    @Resource
    private FormInfraConvert convert;
    @Override
    public PageBean<FormDto> query(FormQueryContext context) {
        FormBase formsBase = convert.toFormBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<FormBase> list = formMapper.findListBy(formsBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public FormDto insert(FormContext context) {
        FormBase formsBase = convert.toFormBase(context);
        formMapper.insert(formsBase);
        return convert.toDto(formsBase);
    }

    @Override
    public FormDto findById(Long id) {
        FormBase formsBase = formMapper.findById(id);
        return convert.toDto(formsBase);
    }

    @Override
    public Pair<Boolean, FormDto> update(FormContext context) {
        FormBase formsBase = convert.toFormBase(context);
        int flag = formMapper.updateById(formsBase);
        return Pair.of( flag>0 , convert.toDto(formsBase)) ;
    }

    @Override
    public int delete(Long id) {
        return formMapper.deleteById(id);
    }

    @Override
    public List<FormDto> allForms(StatusEnum status) {
        FormBase formBase = new FormBase();
        if (Objects.isNull(status)){
            formBase.setStatus(status.getStatus());
        }
        List<FormBase> list = formMapper.findListBy(formBase);
        return convert.toDtos(list);
    }
}
