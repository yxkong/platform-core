package com.github.platform.core.sys.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.sys.domain.common.entity.SysDictBase;
import com.github.platform.core.sys.domain.constant.DictConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
* 字典数据传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.400
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "字典数据传输实体")
public class SysDictDto extends SysDictBase{
    /**
     * 字典类型名称
     */
    @Schema(description ="字典类型名称")
    private String dictTypeName;
    private String charType;
    @JsonIgnore
    public boolean isStr(){
        return Objects.equals(DictConstant.CHAR_TYPE_STR,this.charType);
    }

    @JsonIgnore
    public boolean isInt(){
        return Objects.equals(DictConstant.CHAR_TYPE_INT,this.charType);
    }

}