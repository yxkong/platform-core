package com.github.platform.core.standard.exception;

import com.github.platform.core.standard.constant.ResultStatusEnum;
import java.util.Objects;

/**
 * 没有数据操作权限异常
 * @author: yxkong
 * @date: 2022/6/18 12:19 PM
 * @version: 1.0
 */
public class NoAuthForDataOptException extends CommonException {
    public NoAuthForDataOptException(BaseResult baseResult) {
        super(baseResult);
    }

    public NoAuthForDataOptException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public NoAuthForDataOptException(String status, String message) {
        super(status, message);
    }

    public NoAuthForDataOptException(String message){
        super(ResultStatusEnum.NO_DATA_AUTH.getStatus(), Objects.isNull(message) ? ResultStatusEnum.NO_DATA_AUTH.getMessage() : message);
    }
}
