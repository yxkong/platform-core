package com.github.platform.core.log.infra.configuration.properties;

import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.log.domain.constants.LogOutTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * 操作日志配置属性
 * @author: yxkong
 * @date: 2023/5/2 11:57 上午
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.DATA_OPT_LOG)
public class OptLogProperties {
    /**
     * 是否启用
     */
    private Boolean enable = false;

    /**
     * 日志类型,local,db,kafka,mixDb,mixKafka
     */
    private String  type = LogOutTypeEnum.db.getType();
    /**
     * 日志范围 all -入参和出参, request-入参, response-出参
     */
    private String scope ;

    /**日志级别*/
    private String logLevel;
    public boolean isDebug(){
        if (StringUtils.isEmpty(logLevel) || Objects.equals(this.logLevel,"debug")){
            return true;
        }
        return false;
    }
    public boolean isInfo(){
        return Objects.equals(this.logLevel,"info");
    }
    public boolean isWarn(){
        return Objects.equals(this.logLevel,"warn");
    }
}
