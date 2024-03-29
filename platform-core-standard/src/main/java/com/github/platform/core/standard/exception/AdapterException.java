package com.github.platform.core.standard.exception;


import com.github.platform.core.standard.constant.ResultStatusEnum;
/**
 * 适配层异常
 *
 * @author: yxkong
 * @date: 2021/11/15 2:55 PM
 * @version: 1.0
 */
public class AdapterException extends CommonException {
    public AdapterException(BaseResult baseResult) {
        super(baseResult);
    }
    public AdapterException(BaseResult baseResult,Throwable throwable) {
        super(baseResult,throwable);
    }
    public AdapterException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public AdapterException(ResultStatusEnum resultStatusEnum,Throwable throwable) {
        super(resultStatusEnum,throwable);
    }

    public AdapterException(ResultStatusEnum resultStatusEnum,String msg) {
        super(resultStatusEnum.getStatus(), msg);
    }
    public AdapterException(String status,String message) {
        super(status, message);
    }
}
