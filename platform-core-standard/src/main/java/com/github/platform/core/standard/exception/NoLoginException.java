package com.github.platform.core.standard.exception;

import com.github.platform.core.standard.constant.ResultStatusEnum;

import java.util.Objects;

/**
 * 未登录拦截
 * @author: yxkong
 * @date: 2022/11/30 9:50 上午
 * @version: 1.0
 */
public class NoLoginException extends CommonException{
    public NoLoginException(BaseResult baseResult) {
        super(baseResult);
    }

    public NoLoginException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public NoLoginException(String status, String message) {
        super(status, message);
    }

    public NoLoginException(String message){
        super(ResultStatusEnum.TOKEN_INVALID.getStatus(), Objects.isNull(message) ? ResultStatusEnum.TOKEN_INVALID.getMessage() : message);
    }
    public NoLoginException(){
        super(ResultStatusEnum.TOKEN_INVALID);
    }
}
