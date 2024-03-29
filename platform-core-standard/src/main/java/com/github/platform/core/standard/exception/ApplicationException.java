package com.github.platform.core.standard.exception;

import com.github.platform.core.standard.constant.ResultStatusEnum;

/**
 * 应用层异常
 *
 * @author: yxkong
 * @date: 2021/11/15 2:54 PM
 * @version: 1.0
 */
public class ApplicationException extends CommonException{
    public ApplicationException(BaseResult exceptionResult) {
        super(exceptionResult.getStatus(), exceptionResult.getMessage());
    }
    public ApplicationException(BaseResult exceptionResult,Throwable throwable) {
        super(exceptionResult.getStatus(), exceptionResult.getMessage(),throwable);
    }

    public ApplicationException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum.getStatus(), resultStatusEnum.getMessage());
    }
    public ApplicationException(ResultStatusEnum resultStatusEnum,Throwable throwable) {
        super(resultStatusEnum.getStatus(), resultStatusEnum.getMessage(),throwable);
    }
    public ApplicationException(String status,String message) {
        super(status, message);
    }
}
