package com.github.platform.core.standard.exception;


import com.github.platform.core.standard.constant.ResultStatusEnum;
/**
 * 领域层异常
 *
 * @author: yxkong
 * @date: 2021/11/15 2:55 PM
 * @version: 1.0
 */
public class DomainException extends CommonException{
    public DomainException(BaseResult baseResult) {
        super(baseResult);
    }
    public DomainException(BaseResult baseResult,Throwable throwable) {
        super(baseResult,throwable);
    }

    public DomainException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public DomainException(ResultStatusEnum resultStatusEnum,Throwable throwable) {
        super(resultStatusEnum,throwable);
    }
    public DomainException(String status,String message) {
        super(status, message);
    }
}
