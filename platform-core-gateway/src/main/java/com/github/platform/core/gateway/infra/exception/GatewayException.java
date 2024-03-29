package com.github.platform.core.gateway.infra.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.platform.core.standard.exception.BaseResult;
import com.github.platform.core.standard.util.ResultBeanUtil;


/**
 *
 * @author: yxkong
 * @date: 2021/12/18 4:40 PM
 * @version: 1.0
 */
public enum GatewayException implements BaseResult {
    //限流
    FLOW_LIMIT("1090", "请求被限流"),
    //熔断降级
    REFUSE("1091", "请求被降级");

    GatewayException(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private String status;

    private String message;
    @Override
    public String getStatus() {
        return this.status;
    }
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String getMessage() {
        return this.message;
    }
    public byte[] getBytes() {
        try {
            return objectMapper.writeValueAsBytes(ResultBeanUtil.result(this));
        } catch (JsonProcessingException e) {
        }
        return "".getBytes();
    }
}
