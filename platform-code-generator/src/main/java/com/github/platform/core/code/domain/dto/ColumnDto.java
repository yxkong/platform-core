package com.github.platform.core.code.domain.dto;

import com.github.platform.core.code.domain.common.entity.CodeColumnConfigBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
* 代码生成字段信息存储传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 14:20:40.462
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "代码生成字段信息存储传输实体")
public class ColumnDto extends CodeColumnConfigBase{
    private Integer formShow;
    private Integer listShow;
    private Integer queryShow;

    public boolean isListShow(){
        return isTrue(this.listShow);
    }
    public boolean isFormShow(){
        return isTrue(this.formShow);
    }
    public boolean isQueryShow(){
        return isTrue(this.queryShow);
    }
    private  Boolean isTrue(Integer item){
        if (Objects.isNull(item) || item == 0){
            return false;
        }
        return true;
    }
}