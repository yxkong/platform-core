package com.github.platform.core.common.service;
import com.github.platform.core.common.configuration.property.PlatformProperties;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.common.LoginInfo;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.entity.event.MsgContent;
import com.github.platform.core.standard.exception.ApplicationException;
import com.github.platform.core.standard.exception.BaseResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 通用封装
 * @author: yxkong
 * @date: 2023/1/3 4:29 PM
 * @version: 1.0
 */
@Slf4j
public class BaseExecutor extends BaseBizCommon {
    /**
     * 抛出Application层异常，
     * @param status 状态码
     * @param msg 信息
     * @return
     */
    protected ApplicationException exception(String status,String msg)throws ApplicationException{
        throw new ApplicationException(status,msg);
    }

    /**
     * 抛出Application层业务异常
     * @param result 实现BaseResult 接口的枚举,可参考：{@link com.github.platform.core.sys.domain.constant.SysResultEnum}
     * @return
     */
    protected ApplicationException exception(BaseResult result) throws ApplicationException{
        throw new ApplicationException(result);
    }

    /**
     * 抛出Application层业务异常
     * @param result
     * @param throwable
     * @return
     * @throws ApplicationException
     */
    protected ApplicationException exception(BaseResult result,Throwable throwable) throws ApplicationException{
        throw new ApplicationException(result,throwable);
    }

    /**
     * 抛出Application层业务异常
     * @param resultStatusEnum 通用状态枚举
     * @return
     */
    protected ApplicationException exception(ResultStatusEnum resultStatusEnum)throws ApplicationException{
        throw new ApplicationException(resultStatusEnum);
    }

    /**
     * 抛出Application层业务异常
     * @param resultStatusEnum 通用状态枚举
     */
    protected ApplicationException exception(ResultStatusEnum resultStatusEnum,Throwable throwable )throws ApplicationException{
        throw new ApplicationException(resultStatusEnum,throwable);
    }


    /**
     *
     * @param object 事件
     */
    protected void sendEvent(Object object){
        ApplicationContextHolder.getBean(IDomainEventService.class).publish(object);
    }
    /**
     * 发送Mq事件
     * @param loginInfo
     * @param msgModule
     * @param msgNode
     * @param topic
     * @param resultBean
     */
    protected void sendEventMq(LoginInfo loginInfo,String msgModule, String msgNode,String topic, ResultBean resultBean) {
        PlatformProperties properties = ApplicationContextHolder.getBean(PlatformProperties.class);
        try {
            int status = 0;
            String errorMessage = null;
            if (Objects.isNull(resultBean)) {
                if (resultBean.isSucc()) {
                    status = 1;
                } else {
                    errorMessage = resultBean.getMessage();
                }
            }
            MsgContent<Object> msgContent = MsgContent.builder()
                    .serviceName(properties.getSystem().getServiceName())
                    .msgModule(msgModule)
                    .msgNode(msgNode)
                    .userId(loginInfo.getId())
                    .tenantId(loginInfo.getTenantId())
                    .mobile(loginInfo.getMobile())
                    .data(resultBean.getData())
                    .status(status)
                    .msg(errorMessage)
                    .build();
            ApplicationContextHolder.getBean(IDomainEventService.class).publishMq(msgContent,topic);
        } catch (Exception e) {
            log.error("发送事件广播异常", e);
        }
    }

}
