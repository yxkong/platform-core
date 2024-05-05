package com.github.platform.core.standard.exception;


import com.github.platform.core.standard.constant.ResultStatusEnum;

import java.util.Objects;

/**
 * 参数异常
 * @author: yxkong
 * @date: 2021/4/5 7:49 下午
 * @version: 1.0
 */
public class ParamsRuntimeException extends CommonException {

    public ParamsRuntimeException(){
        super(ResultStatusEnum.PARAM_ERROR);
    }
    public ParamsRuntimeException(String message){
        super(ResultStatusEnum.PARAM_ERROR.getStatus(), Objects.isNull(message) ? ResultStatusEnum.PARAM_ERROR.getMessage() : message);
    }
}