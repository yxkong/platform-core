package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.workflow.FormInfoMapper;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.gateway.ISysCommonGateway;
import com.github.platform.core.workflow.domain.common.entity.FormInfoBase;
import com.github.platform.core.workflow.domain.context.FormInfoContext;
import com.github.platform.core.workflow.domain.context.FormInfoQueryContext;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import com.github.platform.core.workflow.domain.gateway.IFormInfoGateway;
import com.github.platform.core.workflow.infra.convert.FormInfoInfraConvert;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
* 表单信息网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:21.269
* @version 1.0
*/
@Service
public class FormInfoGatewayImpl extends BaseGatewayImpl implements IFormInfoGateway {

    @Resource
    private FormInfoMapper formInfoMapper;
    @Resource
    private FormInfoInfraConvert convert;
    @Resource
    private ISysCommonGateway sysCommonGateway;
    @Override
    public PageBean<FormInfoDto> query(FormInfoQueryContext context) {
        FormInfoBase formInfoBase = convert.toFormInfoBase(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<FormInfoBase> list = formInfoMapper.findListBy(formInfoBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public FormInfoDto insert(FormInfoContext context) {
        FormInfoBase formInfoBase = convert.toFormInfoBase(context);
        formInfoMapper.insert(formInfoBase);
        return convert.toDto(formInfoBase);
    }

    @Override
    public FormInfoDto findById(Long id) {
        FormInfoBase formInfoBase = formInfoMapper.findById(id);
        return convert.toDto(formInfoBase);
    }

    @Override
    public Pair<Boolean, FormInfoDto> update(FormInfoContext context) {
        FormInfoBase formInfoBase = convert.toFormInfoBase(context);
        int flag = formInfoMapper.updateById(formInfoBase);
        return Pair.of( flag>0 , convert.toDto(formInfoBase)) ;
    }

    @Override
    public int delete(Long id) {
        return formInfoMapper.deleteById(id);
    }

    @Override
    public void insertList(List<FormInfoContext> infos, String formNo) {
        formInfoMapper.insertList(convert.toFormInfos(infos,formNo));
    }

    @Override
    public List<FormInfoDto> findByFromNo(String formNo) {
        if (StringUtils.isEmpty(formNo)){
            return null;
        }
        List<FormInfoBase> list = formInfoMapper.findListBy(FormInfoBase.builder().formNo(formNo).build());
        return convert.toDtos(list);
    }
    @Override
    public List<FormInfoDto> findByFromNoWithDict(String formNo) {
        if (StringUtils.isEmpty(formNo)){
            return null;
        }
        List<FormInfoBase> list = formInfoMapper.findListBy(FormInfoBase.builder().formNo(formNo).build());
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        List<FormInfoDto> rst = new ArrayList<>();
        list.forEach(s->{
            FormInfoDto dto = convert.toFormView(s,null);
            if (s.isOption()){
                dto.setOptions(sysCommonGateway.getOptionsByType(s.getOptionName()));
            }
            rst.add(dto);
        });
        return rst;
    }

    @Override
    public void updateList(List<FormInfoContext> updates) {
        formInfoMapper.updateList(convert.toFormInfos(updates,null));
    }
}
