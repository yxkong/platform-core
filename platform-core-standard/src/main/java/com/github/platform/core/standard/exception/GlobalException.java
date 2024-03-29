package com.github.platform.core.standard.exception;


import com.github.platform.core.standard.constant.ResultStatusEnum;
import java.util.Objects;

/**
 * 全局拦截异常
 * @author: yxkong
 * @date: 2022/6/15
 * @version: 1.0
 */
public class GlobalException extends CommonException {
    public GlobalException(BaseResult baseResult) {
        super(baseResult);
    }

    public GlobalException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public GlobalException(String status, String message) {
        super(status, message);
    }

    public GlobalException(String message){
        super(ResultStatusEnum.ERROR.getStatus(), Objects.isNull(message) ? ResultStatusEnum.ERROR.getMessage() : message);
    }
}
