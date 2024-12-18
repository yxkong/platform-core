package com.github.platform.core.sys.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.sys.domain.common.entity.SysThirdUserBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 三方用户传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.505
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "三方用户传输实体")
public class SysThirdUserDto extends SysThirdUserBase{
    /** 加密用户id */
    protected String strUserId;
    /**登录名称*/
    protected String loginName;
    @JsonIgnore
    public boolean isDisabled(){
        if(null == this.status || StatusEnum.OFF.getStatus().equals(this.status)){
            return true;
        }
        return false;
    }
}