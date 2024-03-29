package com.github.platform.core.sms.infra.constant;

import com.github.platform.core.standard.exception.BaseResult;

/**
 * 短信状态
 * @author: yxkong
 * @date: 2023/11/21 10:43
 * @version: 1.0
 */
public enum SmsInfraEnum implements BaseResult {
    CONFIG_TEMP_SP_ERROR("1017", "未找到模板%s对应的启用的厂商,请先配置"),
    CONFIG_ROUTE_TEMP_SP_ERROR("1017", "未路由到模板%s指定的厂商编号:%s,请核查配置"),
    CONFIG_SP_DISABLE("1017", "模板编号%s对应的厂商%s已禁用，请核查"),
   aa("","")
    ;

    SmsInfraEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }
    private String status;
    private String message;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
