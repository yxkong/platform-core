package com.github.platform.core.sms.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.sms.domain.common.entity.SysSmsTemplateBase;
import com.github.platform.core.sms.domain.constant.SmsConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 短信模板传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.124
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "短信模板传输实体")
public class SysSmsTemplateDto extends SysSmsTemplateBase {

    /**
     * 是否可用
     * @return
     */
    @JsonIgnore
    public boolean isValid(){
        if (status == 1 ){
            return true;
        }
        return false;
    }

    @JsonIgnore
    public boolean isRoute() {
        if (SmsConstant.route_type_random.equalsIgnoreCase(this.routeType) || SmsConstant.route_type_roundRobin.equalsIgnoreCase(this.routeType)){
            return true;
        }
        return false;
    }
}