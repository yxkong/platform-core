package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.persistence.mapper.workflow.FormDataMapper;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.common.entity.FormDataBase;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.FormDataQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import com.github.platform.core.workflow.domain.gateway.IFormDataGateway;
import com.github.platform.core.workflow.infra.convert.FormDataInfraConvert;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
* 表单数据网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:23.341
* @version 1.0
*/
@Service
public class FormDataGatewayImpl extends BaseGatewayImpl implements IFormDataGateway {

    @Resource
    private FormDataMapper formDataMapper;
    @Resource
    private FormDataInfraConvert convert;
    @Override
    public PageBean<FormDataDto> query(FormDataQueryContext context) {
        FormDataBase formDataBase = convert.toFormDataBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<FormDataBase> list = formDataMapper.findListBy(formDataBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public FormDataDto insert(FormDataContext context) {
        FormDataBase formDataBase = convert.toFormDataBase(context);
        formDataMapper.insert(formDataBase);
        return convert.toDto(formDataBase);
    }

    @Override
    public void insertList(List<FormDataContext> infos, String instanceNo) {
        formDataMapper.insertList(convert.toFormDatas(infos,instanceNo));
    }

    @Override
    public FormDataDto findById(Long id) {
        FormDataBase formDataBase = formDataMapper.findById(id);
        return convert.toDto(formDataBase);
    }

    @Override
    public Pair<Boolean, FormDataDto> update(FormDataContext context) {
        FormDataBase formDataBase = convert.toFormDataBase(context);
        int flag = formDataMapper.updateById(formDataBase);
        return Pair.of( flag>0 , convert.toDto(formDataBase)) ;
    }

    @Override
    public int delete(Long id) {
        return formDataMapper.deleteById(id);
    }

    @Override
    public List<FormDataDto> findByInstanceNoAndFormNo(String instanceNo, String formNo) {
        FormDataBase formDataBase = FormDataBase.builder().instanceNo(instanceNo).formNo(formNo).build();
        List<FormDataBase> list = formDataMapper.findListBy(formDataBase);
        return convert.toDtos(list);
    }
}
