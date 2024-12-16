package com.github.platform.core.monitor.infra.listener;

import com.github.platform.core.common.configuration.property.PlatformProperties;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.log.domain.constants.LogOutTypeEnum;
import com.github.platform.core.log.domain.constants.LogScopeEnum;
import com.github.platform.core.log.domain.entity.OptLogEntity;
import com.github.platform.core.log.infra.configuration.properties.OptLogProperties;
import com.github.platform.core.log.infra.event.OptLogEvent;
import com.github.platform.core.monitor.domain.common.entity.SysOptLogBase;
import com.github.platform.core.monitor.infra.convert.SysOptLogInfraConvert;
import com.github.platform.core.monitor.infra.service.ISysOptLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事件处理
 * 可以直接使用
 *     @EventListener
 *
 * @author: yxkong
 * @date: 2023/5/4 2:57 PM
 * @version: 1.0
 */
@Component
@Slf4j
public class OptLogEventListener implements ApplicationListener<OptLogEvent> {
    private final static Integer MAX_LENGTH = 2000;
    @Resource
    private OptLogProperties logProperties;
    @Resource
    private ISysOptLogService optLogService;
    @Resource
    private SysOptLogInfraConvert convert;
    @Resource
    private PlatformProperties platformProperties;
    @Override
    public void onApplicationEvent(OptLogEvent event) {
        try {
            OptLogEntity entity = event.getOptLog();
            if (!isRequest()) {
                entity.setRequestBody(null);
            }
            if (!isResponse()) {
                entity.setResponseBody(null);
            }
            if (LogOutTypeEnum.local.getType().equals(logProperties.getType())){
                log(entity);
            } else if (LogOutTypeEnum.db.getType().equals(logProperties.getType())){
                // 入库
                if (entity.getPersistent()) {
                    optLogService.insert(getOptLogBase(entity));
                }
            } else if (LogOutTypeEnum.kafka.getType().equals(logProperties.getType())){
                // 发送kafka
            } else if (LogOutTypeEnum.mixDb.getType().equals(logProperties.getType())){
                log(entity);
                //入库
                if (entity.getPersistent()){
                    optLogService.insert(getOptLogBase(entity));
                }
            } else if (LogOutTypeEnum.mixKafka.getType().equals(logProperties.getType())){
                log(entity);
                // 发送kafka
            }
        } catch (Exception e) {
            log.error("监听opt log",e);
        }
    }

    private void log(OptLogEntity entity) {
        if (logProperties.isDebug() && log.isDebugEnabled()){
            log.debug("opt log:{}", JsonUtils.toJson(entity));
        } else if(logProperties.isInfo() && log.isInfoEnabled()){
            log.info("opt log:{}", JsonUtils.toJson(entity));
        } else if(logProperties.isWarn() && log.isWarnEnabled()){
            log.warn("opt log:{}", JsonUtils.toJson(entity));
        }
    }

    private boolean isRequest(){
        if (LogScopeEnum.all.getScope().equals(logProperties.getScope()) ||
                LogScopeEnum.request.getScope().equals(logProperties.getScope())){
            return true;
        }
        return false;
    }
    private boolean isResponse(){
        if (LogScopeEnum.all.getScope().equals(logProperties.getScope()) ||
                LogScopeEnum.request.getScope().equals(logProperties.getScope())){
            return true;
        }
        return false;
    }
    private SysOptLogBase getOptLogBase(OptLogEntity entity ){
        SysOptLogBase optLogBase = convert.toSysOptLogBase(entity);
        if (StringUtils.isNotEmpty(entity.getRequestBody()) && entity.getRequestBody().length()> MAX_LENGTH){
            optLogBase.setRequestBody(entity.getRequestBody().substring(0,MAX_LENGTH));
        }
        if (StringUtils.isNotEmpty(entity.getResponseBody()) && entity.getResponseBody().length() > MAX_LENGTH){
            optLogBase.setResponseBody(entity.getResponseBody().substring(0,MAX_LENGTH));
        }
        optLogBase.setHeaders(JsonUtils.toJson(entity.getHeadersMap()));
        return optLogBase;
    }
}
