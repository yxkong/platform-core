package com.github.platform.core.standard.exception;

import com.github.platform.core.standard.constant.ResultStatusEnum;
import java.util.Objects;

/**
 * 数据库操作异常
 * @author yxkong
 * @date 2019-05-27 20:07
 */
public class DbException extends CommonException {

    private static final long serialVersionUID = 8016028387771340304L;

    public DbException(BaseResult baseResult){
        super(baseResult);
    }
    public DbException(BaseResult baseResult,Throwable throwable){
        super(baseResult,throwable);
    }

    public DbException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum);
    }
    public DbException(ResultStatusEnum resultStatusEnum,Throwable throwable) {
        super(resultStatusEnum,throwable);
    }
    public DbException(String status,String message) {
        super(status, message);
    }
    public DbException(String message){
        super(ResultStatusEnum.DB_EXCEPTION.getStatus(), Objects.isNull(message) ? ResultStatusEnum.DB_EXCEPTION.getMessage() : message);
    }
}
