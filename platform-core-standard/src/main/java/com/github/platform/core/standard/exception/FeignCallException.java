package com.github.platform.core.standard.exception;

import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
/**
 * Feign 调用异常
 *
 * @author yxkong
 * @date 2019-05-27 20:07
 */
public class FeignCallException extends CommonException {

    private static final long serialVersionUID = -1887241576634384486L;

    public FeignCallException(ResultBean<?> resultBean) {
        super(resultBean);
    }
    public FeignCallException(BaseResult baseResult) {
        super(baseResult);
    }
    public FeignCallException(BaseResult baseResult,Throwable throwable) {
        super(baseResult,throwable);
    }

    public FeignCallException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public FeignCallException(ResultStatusEnum resultStatusEnum,Throwable throwable) {
        super(resultStatusEnum,throwable);
    }
    public FeignCallException(String status,String message) {
        super(status, message);
    }
}
