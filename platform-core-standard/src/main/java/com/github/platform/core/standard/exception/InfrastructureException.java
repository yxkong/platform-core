package com.github.platform.core.standard.exception;


import com.github.platform.core.standard.constant.ResultStatusEnum;

/**
 * 基础设施层异常
 *
 * @author: yxkong
 * @date: 2021/11/15 2:56 PM
 * @version: 1.0
 */
public class InfrastructureException extends CommonException {
    public InfrastructureException(BaseResult baseResult) {
        super(baseResult);
    }
    public InfrastructureException(BaseResult baseResult,Throwable throwable) {
        super(baseResult,throwable);
    }

    public InfrastructureException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public InfrastructureException(ResultStatusEnum resultStatusEnum,Throwable throwable) {
        super(resultStatusEnum,throwable);
    }
    public InfrastructureException(String status,String message) {
        super(status, message);
    }
}
