package com.github.platform.core.web.aspect;

import com.github.platform.core.common.aspect.ExceptionHandlerBase;
import com.github.platform.core.common.configuration.property.PlatformProperties;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.standard.util.ExceptionUtil;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.web.util.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 数据库异常处理
 * @author: yxkong
 * @date: 2024/7/8 18:43
 * @version: 1.0
 */
@RestControllerAdvice
@Order(SpringBeanOrderConstant.GLOBAL_EXCEPTION)
@Slf4j
@ConditionalOnClass(name = {"org.springframework.dao.QueryTimeoutException;", "org.springframework.jdbc.BadSqlGrammarException"})
public class DBGlobalExceptionHandler  extends ExceptionHandlerBase {
    @Resource
    private PlatformProperties properties;
    /******* 数据库异常处理  *******/
    @ConditionalOnClass(BadSqlGrammarException.class)
    @ExceptionHandler(BadSqlGrammarException.class)
    public void badSqlGrammarException(BadSqlGrammarException e) {
        throw new InfrastructureException(ResultStatusEnum.DB_BAD_SQL,e);
    }
    @ConditionalOnClass(CannotGetJdbcConnectionException.class)
    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public void cannotGetJdbcConnectionException(CannotGetJdbcConnectionException e) {
        throw new InfrastructureException(ResultStatusEnum.DB_BAD_SQL,e);
    }
    @ConditionalOnClass(DuplicateKeyException.class)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResultBean<?> duplicateKeyException(DuplicateKeyException e) {
        log("duplicateKeyException", e);
        return ResultBeanUtil.result(ResultStatusEnum.DB_DUPLICATE_KEY, ExceptionUtil.getMessage(e));
    }
    @ConditionalOnClass(DeadlockLoserDataAccessException.class)
    @ExceptionHandler(DeadlockLoserDataAccessException.class)
    public ResultBean<?> deadlockLoserDataAccessException(DeadlockLoserDataAccessException e) {
        log("deadlockLoserDataAccessException", e);
        return ResultBeanUtil.result(ResultStatusEnum.DB_DEAD_LOCK, ExceptionUtil.getMessage(e));
    }
    @ConditionalOnClass(QueryTimeoutException.class)
    @ExceptionHandler(QueryTimeoutException.class)
    public ResultBean<?> queryTimeoutException(QueryTimeoutException e) {
        log("queryTimeoutException", e);
        return ResultBeanUtil.result(ResultStatusEnum.DB_QUERY_TIMEOUT, ExceptionUtil.getMessage(e));
    }
    @ConditionalOnClass(CannotAcquireLockException.class)
    @ExceptionHandler(CannotAcquireLockException.class)
    public ResultBean<?> cannotAcquireLockException(CannotAcquireLockException e) {
        log("cannotAcquireLockException", e);
        return ResultBeanUtil.result(ResultStatusEnum.DB_CANNOT_ACQUIRE_LOCK, ExceptionUtil.getMessage(e));
    }
    public void log(String exceptionName,Throwable e){
        HttpServletRequest request = RequestHolder.getRequest();
        log.error(log(exceptionName,request.getHeader(HeaderConstant.TOKEN), request.getRequestURI(),null,RequestHolder.getIncludeHeaders(request,properties.getIncludeHeaders()),RequestHolder.getRequestBody(request),null),e);
    }
}
