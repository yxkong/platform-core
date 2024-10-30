package com.github.platform.core.web.aspect;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.platform.core.common.aspect.ExceptionHandlerBase;
import com.github.platform.core.common.configuration.property.PlatformProperties;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.exception.*;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.web.util.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 * fixed:
 * 1, 解决api调用api时，底层api将异常信息设置到data中，前面api如果使用feign并且添加了类型，无法转换
 * @author yxkong
 * @date 2019/5/24-17:08
 */
@RestControllerAdvice
//@ControllerAdvice
@Order(SpringBeanOrderConstant.GLOBAL_EXCEPTION)
@Slf4j
public class GlobalExceptionHandler extends ExceptionHandlerBase{

    @Resource
    private PlatformProperties properties;
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBean<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                                HttpServletRequest request) {
        log("HttpRequestMethodNotSupportedException", Arrays.toString(e.getSupportedMethods()),e);
        return ResultBeanUtil.result(ResultStatusEnum.REQ_TYPE_EXCEPTION.getStatus(), "请求方式错误，请更换为" + Arrays.toString(e.getSupportedMethods()));
    }

    /**
     * 必传参数没有传递
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultBean<?> missingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log("missingServletRequestParameterException",null,e);
        String parameterName = e.getParameterName();
        return ResultBeanUtil.paramEmpty(String.format("参数[%s]必填", parameterName));
    }

    /**
     * 响应内容解析json失败
     */
    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResultBean<?> httpMessageNotWritableException(HttpMessageNotWritableException e, HttpServletRequest request) {
        log("httpMessageNotWritableException",null,e);
        return ResultBeanUtil.result(ResultStatusEnum.COMMON_NOT_WRITABLE_EXCEPTION);
    }
    /**
     * 数据校验失败捕获
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean<?> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        log("methodArgumentNotValidHandler",e);
        StringBuilder sb = new StringBuilder();
        // 解析原错误信息，封装后返回，此处返回非法的字段名称，错误信息
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            sb.append(String.format("[%s: %s]", fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return ResultBeanUtil.result(ResultStatusEnum.VALID_EXCEPTION.getStatus(), sb.toString());
    }

    /**
     * spring 绑定参数异常捕获
     */
    @ExceptionHandler(BindException.class)
    public ResultBean<?> bindException(BindException e) {
        log("bindException",e);
        StringBuilder sb = new StringBuilder();
        // 解析原错误信息，封装后返回，此处返回非法的字段名称，错误信息
        if (e.hasErrors()) {
            e.getFieldErrors().forEach(fieldError -> {
                sb.append(String.format("[%s] %s;", fieldError.getField(), fieldError.getDefaultMessage()));
            });
        }
        return ResultBeanUtil.result(ResultStatusEnum.COMMON_BIND_EXCEPTION.getStatus(), sb.toString());
    }

    /**
     * 请求内容类型如果不是json，或者为json单请求内容为空，会捕获该异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultBean<?> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log("httpMessageNotReadableException",e);
        if (e.getCause() instanceof InvalidFormatException) {
            return ResultBeanUtil.result(ResultStatusEnum.PARAM_ERROR, e.getCause().getMessage());
        }
        return ResultBeanUtil.result(ResultStatusEnum.COMMON_NOT_READABLE_EXCEPTION);
    }


    /**
     * 拦截数据库操作异常
     */
    @ExceptionHandler(DbException.class)
    public ResultBean<?> dbException(DbException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("dbException",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e);
    }
    /**
     * 拦截Feign调用异常
     */
    @ExceptionHandler(FeignCallException.class)
    public ResultBean<?> feignException(FeignCallException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("feignException",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e,null);
    }


    @ExceptionHandler(NoAuthForDataOptException.class)
    public ResultBean<?> noAuth(NoAuthForDataOptException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("noAuth",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e,null);
    }

    @ExceptionHandler(NoLoginException.class)
    public ResultBean<?> noLogin(NoLoginException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("noLogin",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e,null);
    }

    @ExceptionHandler(ParamsRuntimeException.class)
    public ResultBean<?> notFount(ParamsRuntimeException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("notFount",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e,null);
    }

    @ExceptionHandler(AdapterException.class)
    public ResultBean<?> adapterException(AdapterException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("adapterException",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e,null);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResultBean<?> applicationException(ApplicationException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("applicationException",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e,null);
    }

    @ExceptionHandler(DomainException.class)
    public ResultBean<?> domainException(DomainException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("domainException",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e,null);
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResultBean<?> infrastructureException(InfrastructureException e) {
        if (Objects.nonNull(e.getThrowable())){
            log("infrastructureException",e.getMessage(),e.getThrowable());
        }
        return ResultBeanUtil.result(e,null);
    }
    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultBean<?> notFount(RuntimeException e) {
        log("RuntimeException",e);
        return ResultBeanUtil.fail(e.getMessage());
    }



    private void log(String exceptionName,String exceptionStr,Throwable e){
        ContentCachingRequestWrapper request = RequestHolder.getContentCachingRequestWrapper();
        log.error(log(exceptionName,request.getHeader(HeaderConstant.TOKEN), request.getRequestURI(),request.getMethod(),RequestHolder.getIncludeHeaders(request,properties.getIncludeHeaders()),RequestHolder.getRequestBody(request),exceptionStr),e);
    }
    public void log(String exceptionName,Throwable e){
        HttpServletRequest request = RequestHolder.getRequest();
        log.error(log(exceptionName,request.getHeader(HeaderConstant.TOKEN), request.getRequestURI(),null,RequestHolder.getIncludeHeaders(request,properties.getIncludeHeaders()),RequestHolder.getRequestBody(request),null),e);
    }
    /**
     * 终极处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultBean<?> exception(Exception e) {
        log("exception",e);
        return ResultBeanUtil.fail(e.getMessage() );
    }


}
