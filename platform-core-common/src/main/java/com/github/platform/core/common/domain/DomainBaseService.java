package com.github.platform.core.common.domain;

import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.exception.BaseResult;
import com.github.platform.core.standard.exception.DomainException;

/**
 * 领域服务基类
 * @author: yxkong
 * @date: 2023/9/12 2:46 PM
 * @version: 1.0
 */
public class DomainBaseService {
    /**
     * 抛出domain层业务自定义异常
     * @param status 状态码
     * @param msg 信息
     * @return
     */
    protected DomainException exception(String status, String msg){
        throw new DomainException(status,msg);
    }

    /**
     * 抛出domain层业务自定义异常
     * @param result 实现BaseResult 接口的枚举，可参考：{@link com.github.platform.core.sys.domain.constant.SysResultEnum}
     * @return
     */
    protected DomainException exception(BaseResult result){
        throw new DomainException(result);
    }

    /**
     * 抛出domain层业务自定义异常
     * @param result
     * @param throwable
     * @return
     */
    protected DomainException exception(BaseResult result,Throwable throwable){
        throw new DomainException(result,throwable);
    }

    /**
     * 抛出domain层业务自定义异常
     * @param resultStatusEnum 通用异常枚举
     * @return
     */
    protected DomainException exception(ResultStatusEnum resultStatusEnum){
        throw new DomainException(resultStatusEnum);
    }

    /**
     * 抛出domain层业务自定义异常
     * @param resultStatusEnum
     * @param throwable
     * @return
     */
    protected DomainException exception(ResultStatusEnum resultStatusEnum,Throwable throwable){
        throw new DomainException(resultStatusEnum,throwable);
    }
}
