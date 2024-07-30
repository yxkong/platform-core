package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.persistence.mapper.workflow.FormDataMapper;
import com.github.platform.core.workflow.domain.common.entity.FormDataBase;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import com.github.platform.core.workflow.domain.gateway.ICustomFormDataGateway;
import com.github.platform.core.workflow.domain.gateway.IFormInfoGateway;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表单数据获取
 * @author: yxkong
 * @date: 2024/3/12 14:27
 * @version: 1.0
 */
@Service("sysFormDataGateway")
public class SysFormDataGatewayImpl implements ICustomFormDataGateway {
    @Resource
    private FormDataMapper formDataMapper;

    @Resource
    private IFormInfoGateway formInfoGateway;
    @Override
    public List<FormInfoDto> getFormViewWithData(String bizNo, String instanceNo, String formNo) {
        List<FormInfoDto> rst = formInfoGateway.findByFromNoWithDict(formNo);
        if (CollectionUtil.isEmpty(rst)){
            return rst;
        }
        List<FormDataBase> dataList = formDataMapper.findListBy(FormDataBase.builder().instanceNo(instanceNo).formNo(formNo).build());
        if (CollectionUtil.isEmpty(dataList)) {
            return rst;
        }
        return fillFormDataValues(rst, dataList);
    }
    private List<FormInfoDto> fillFormDataValues(List<FormInfoDto> rst, List<FormDataBase> datas) {
        // 使用Map优化查找效率
        Map<String, FormDataBase> dataMap = datas.stream()
                .collect(Collectors.toMap(FormDataBase::getName, d -> d));

        return rst.stream()
                .peek(s -> {
                    FormDataBase data = dataMap.get(s.getName());
                    if (data != null) {
                        s.setId(data.getId());
                        s.setValue(data.getValue());
                    }
                })
                .collect(Collectors.toList());
    }
}
