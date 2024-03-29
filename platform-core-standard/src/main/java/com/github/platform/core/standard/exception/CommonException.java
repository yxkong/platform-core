package com.github.platform.core.standard.exception;

import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import lombok.Getter;

/**
 * 通用异常
 *
 * @author yxkong
 * @date 2019/5/27-19:41
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -2706753300347526671L;
    /**自定义状态码*/
    private String status;
    /**自定义提示信息*/
    private String message;
    /**原始异常*/
    @Getter
    private Throwable throwable;

    protected CommonException() {

    }

    public CommonException(String status, String message) {
        this.status = status;
        this.message = message;
    }
    public CommonException(String status, String message,Throwable throwable) {
        this.status = status;
        this.message = message;
        this.throwable = throwable;
    }
    public CommonException(BaseResult baseResult){
        this(baseResult.getStatus(), baseResult.getMessage());
    }
    public CommonException(BaseResult baseResult,Throwable throwable){
        this(baseResult.getStatus(), baseResult.getMessage(),throwable);
    }
    public CommonException(ResultStatusEnum resultStatusEnum) {
        this(resultStatusEnum.getStatus(), resultStatusEnum.getMessage());
    }
    public CommonException(ResultBean resultBean) {
        this(resultBean.getStatus(), resultBean.getMessage());
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public CommonException format(Object... args){
        this.message = String.format(message, args);
        return this;
    }
}
