package com.github.platform.core.gateway.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关统一异常处理
 * @author yxk
 */
@Slf4j
public class GatewayGlobalException extends DefaultErrorWebExceptionHandler {


    public GatewayGlobalException(ErrorAttributes errorAttributes, WebProperties.Resources resources, ErrorProperties errorProperties, ApplicationContext applicationContext){
        super(errorAttributes,resources, errorProperties, applicationContext);
    }
    /**
     * 获取异常属性
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Throwable  e= super.getError(request);
        int status = determineHttpStatus(e);
        log.error("请求地址：{},http响应码：{}异常信息：",request.path(),status,e);
        //屏蔽对客户端的输出
        return builderResponse(0,"系统异常，请稍后再试！",e.getMessage());
    }
    private int determineHttpStatus(Throwable  e){
        if (e instanceof NotFoundException){
            return HttpStatus.SC_NOT_FOUND;
        }
        if(e instanceof  ResponseStatusException) {
            ((ResponseStatusException) e).getStatus();
        }
        return HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }
    /**
     * 根据code获取对应的HttpStatus
     *
     * @param errorAttributes
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return (int) errorAttributes.get("status");
    }
    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 构建响应结果，和ResultBean保持一致
     * @param status 状态码
     * @param message 返回提示信息
     * @param data 真实的异常数据
     * @return
     */
    public static Map<String, Object> builderResponse(int status,String message, String data) {
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);
        map.put("message", message);
        map.put("data", data);
        map.put("timestamp",System.currentTimeMillis());
        return map;
    }
}
