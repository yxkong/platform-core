package com.github.platform.core.common.aspect;

import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.standard.util.ExceptionUtil;
import com.github.platform.core.standard.util.ResultBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局通用异常，和用户无关，主要是捕获以后，将异常包装成对应层面的异常
 * @author: yxkong
 * @date: 2023/8/3 2:34 PM
 * @version: 1.0
 */
@Slf4j
public class DBExceptionHandler extends ExceptionHandlerBase{
    @ExceptionHandler(BadSqlGrammarException.class)
    public void badSqlGrammarException(BadSqlGrammarException e) {
        throw new InfrastructureException(ResultStatusEnum.DB_BAD_SQL,e);
    }
    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public void cannotGetJdbcConnectionException(CannotGetJdbcConnectionException e) {
        throw new InfrastructureException(ResultStatusEnum.DB_BAD_SQL,e);
    }
    @ExceptionHandler(DuplicateKeyException.class)
    public ResultBean<?> duplicateKeyException(DuplicateKeyException e) {
        log("duplicateKeyException", e);
        return ResultBeanUtil.result(ResultStatusEnum.DB_DUPLICATE_KEY, ExceptionUtil.getMessage(e));
    }
    @ExceptionHandler(DeadlockLoserDataAccessException.class)
    public ResultBean<?> deadlockLoserDataAccessException(DeadlockLoserDataAccessException e) {
        log("deadlockLoserDataAccessException", e);
        return ResultBeanUtil.result(ResultStatusEnum.DB_DEAD_LOCK, ExceptionUtil.getMessage(e));
    }
    @ExceptionHandler(QueryTimeoutException.class)
    public ResultBean<?> queryTimeoutException(QueryTimeoutException e) {
        log("queryTimeoutException", e);
        return ResultBeanUtil.result(ResultStatusEnum.DB_QUERY_TIMEOUT, ExceptionUtil.getMessage(e));
    }
    @ExceptionHandler(CannotAcquireLockException.class)
    public ResultBean<?> cannotAcquireLockException(CannotAcquireLockException e) {
        log("cannotAcquireLockException", e);
        return ResultBeanUtil.result(ResultStatusEnum.DB_CANNOT_ACQUIRE_LOCK, ExceptionUtil.getMessage(e));
    }
    private void log(String exceptionName,Throwable e){
        log.error(log(exceptionName,null, null,null,null,null,null),e);
    }
}
