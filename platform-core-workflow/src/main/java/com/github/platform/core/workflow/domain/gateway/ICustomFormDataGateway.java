package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.workflow.domain.dto.FormInfoDto;

import java.util.List;

/**
 * 自定义表单数据网关
 *
 * @author: yxkong
 * @date: 2024/3/12 14:24
 * @version: 1.0
 */
public interface ICustomFormDataGateway {
    /**
     * 查找表单数据
     * @param bizNo
     * @param instanceNo
     * @param formNo
     * @return
     */
    List<FormInfoDto> getFormViewWithData(String bizNo, String instanceNo, String formNo);
}
