package com.github.platform.core.standard.util;


import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.exception.BaseResult;
import com.github.platform.core.standard.exception.CommonException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * ResultBean工具类
 * @author: yxkong
 * @date: 2021/9/10 2:50 下午
 * @version: 1.0
 */
public class ResultBeanUtil {

    /**
     * 返回成功
     * @return
     */
    public static ResultBean success(){
        return success("成功！");
    }

    public static ResultBean success(String msg){
        return success(msg,null);
    }

    /**
     * 返回成功，携带数据体
     * @param data 数据体
     * @return
     */
    public static <T> ResultBean<T> success(T data){
        return statusEnum(ResultStatusEnum.SUCCESS,data);
    }

    /**
     * 返回泛型成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T>ResultBean<T> succ(T data){
        return statusEnum(ResultStatusEnum.SUCCESS,data);
    }
    /**
     * 返回成功，自定义message和数据体
     * @param msg  自定义message
     * @param data  数据体
     * @return
     */
    public static <T> ResultBean<T> success(String msg,T data){
        return result(ResultStatusEnum.SUCCESS.getStatus(),msg,data);
    }

    /**
     * 返回泛型成功,自定义message和数据体
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultBean<T> succ(String msg,T data){
        return result(ResultStatusEnum.SUCCESS.getStatus(),msg,data);
    }
    private static <T> ResultBean<T> statusEnum(ResultStatusEnum statusEnum,T data){
        return result(statusEnum.getStatus(), statusEnum.getMessage(), data);
    }


    /**
     * 返回失败
     * @return
     */
    public static ResultBean fail(){
        return fail(null);
    }

    public static ResultBean fail(String msg){
        return fail(msg,null);
    }

    /**
     * 返回失败，自定义数据体
     * @param data 自定义数据体
     * @return
     */
    public static <T> ResultBean<T> fail(T data){
        return statusEnum(ResultStatusEnum.ERROR,data);
    }

    /**
     * 返回失败，自定义message和数据体
     * @param msg  自定义message
     * @param data 自定义数据体
     * @return
     */
    public static <T> ResultBean<T> fail(String msg,T data){
        return result(ResultStatusEnum.ERROR.getStatus(),msg,data);
    }

    /**
     * 返回无权限
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultBean<T> noAuth(String msg){
        return result(ResultStatusEnum.NO_AUTH.getStatus(),msg,null);
    }

    /**
     * 根据ResultStatusEnum返回结果
     * @param resultStatusEnum 枚举
     * @return
     */
    public static ResultBean result(ResultStatusEnum resultStatusEnum){
        return statusEnum(resultStatusEnum,null);
    }

    public static ResultBean result(ResultStatusEnum statusEnum, String data){
        return result(statusEnum.getStatus(),statusEnum.getMessage(),data);
    }

    /**
     *  根据BaseResult返回结果
     * @param baseResult 根据BaseResult中的值获取
     * @return
     */
    public static ResultBean result(BaseResult baseResult){
        return result(baseResult.getStatus(), baseResult.getMessage(), null);
    }

    /**
     * 根据CommonException 返回结果
     * @param e
     * @return
     */
    public static ResultBean result(CommonException e){
        return result(e.getStatus(),e.getMessage(),ExceptionUtil.getMessage(e.getThrowable()));
    }
    /**
     *  根据BaseResult返回结果
     * @param baseResult 根据BaseResult中的值获取
     * @return
     */
    public static  <T>ResultBean<T>  result(BaseResult baseResult,T data){
        return result(baseResult.getStatus(), baseResult.getMessage(), data);
    }
    /**
     * 自定义状态和消息
     * @param status
     * @param msg
     * @return
     */
    public static ResultBean result(String status,String msg){
        return result(status,msg,null);
    }


    /**
     * 返回结果
     * @param status 状态
     * @param msg  消息message
     * @param data  消息体
     * @return
     */
    public static <T>ResultBean<T> result(String status,String msg,T data){
        if (Objects.nonNull(data)){
            return ResultBean.<T>builder().status(status).message(msg).data(data).build();
        }
        return (ResultBean<T>) ResultBean.builder().status(status).message(msg).build();

    }

    /**
     * 必填参数为空
     * @param msg
     * @return
     */
    public static ResultBean paramEmpty(String msg){
        return result(ResultStatusEnum.PARAM_EMPTY.getStatus(),msg,null);
    }
    /**
     * token失效
     * @param msg
     * @return
     */
    public static ResultBean tokenInvalid(String msg){
        if (StringUtils.isEmpty(msg)){
            return result(ResultStatusEnum.TOKEN_INVALID);
        }
        return result(ResultStatusEnum.TOKEN_INVALID.getStatus(),msg);

    }

    public static Boolean isSuccess(ResultBean data) {
        if (data == null) {
            return Boolean.FALSE;
        }
        return ResultStatusEnum.SUCCESS.getStatus().equals(data.getStatus());
    }
}