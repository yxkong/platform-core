package com.github.platform.core.standard.exception;


import com.github.platform.core.standard.constant.ResultStatusEnum;
/**
 * 配置异常异常
 * @author: yxkong
 * @date: 2021/4/5 7:49 下午
 * @version: 1.0
 */
public class ConfigRuntimeException extends CommonException {
    public ConfigRuntimeException(BaseResult baseResult) {
        super(baseResult);
    }
    public ConfigRuntimeException(BaseResult baseResult,Throwable throwable) {
        super(baseResult,throwable);
    }
    public ConfigRuntimeException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public ConfigRuntimeException(ResultStatusEnum resultStatusEnum,Throwable throwable) {
        super(resultStatusEnum,throwable);
    }
    public ConfigRuntimeException(String status, String message) {
        super(status, message);
    }
}