package com.github.platform.core.common.gateway;

import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.exception.BaseResult;
import com.github.platform.core.standard.exception.InfrastructureException;

/**
 * gateway基础
 * @author: yxkong
 * @date: 2023/9/5 4:38 PM
 * @version: 1.0
 */
public class BaseGatewayImpl {
    /**
     * 抛出InfrastructureException层业务自定义异常
     * @param status 状态码
     * @param msg 信息
     * @return
     */
    protected InfrastructureException exception(String status, String msg){
        throw new InfrastructureException(status,msg);
    }

    /**
     * 抛出InfrastructureException层业务自定义异常
     * @param result 实现BaseResult 接口的枚举，可参考：{@link com.github.platform.core.sys.domain.constant.SysResultEnum}
     * @return
     */
    protected InfrastructureException exception(BaseResult result){
        throw new InfrastructureException(result);
    }
    protected InfrastructureException exception(BaseResult result,Throwable throwable){
        throw new InfrastructureException(result,throwable);
    }

    /**
     * 抛出InfrastructureException层业务自定义异常
     * @param resultStatusEnum 通用异常枚举
     * @return
     */
    protected InfrastructureException exception(ResultStatusEnum resultStatusEnum){
        throw new InfrastructureException(resultStatusEnum);
    }
    protected InfrastructureException exception(ResultStatusEnum resultStatusEnum,Throwable throwable){
        throw new InfrastructureException(resultStatusEnum,throwable);
    }
}
