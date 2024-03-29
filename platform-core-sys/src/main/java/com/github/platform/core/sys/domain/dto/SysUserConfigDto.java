package com.github.platform.core.sys.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.sys.domain.common.entity.SysUserConfigBase;
import com.github.platform.core.sys.domain.constant.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
 * 用户配置传输实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "用户配置传输实体")
public class SysUserConfigDto extends SysUserConfigBase{

    @JsonIgnore
    public Long getLongVal(){
        if (Constants.CHAR_TYPE_LONG.equals(this.valType)){
            return Long.parseLong(this.getValue());
        }
        return null;
    }
    @JsonIgnore
    public Integer getIntegerVal(){
        if (Constants.CHAR_TYPE_INT.equals(this.valType)){
            return Integer.parseInt(this.getValue());
        }
        return null;
    }

}