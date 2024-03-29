package com.github.platform.core.workflow.domain.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.workflow.domain.common.entity.ProcessDefinitionBase;
import com.github.platform.core.workflow.domain.constant.ProcessStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
* 流程定义传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "流程管理传输实体")
public class ProcessDefinitionDto extends ProcessDefinitionBase {
    @JsonIgnore
    public boolean isDraft(){
        return ProcessStatusEnum.DRAFT.getStatus().equals(this.status);
    }

    @JsonIgnore
    @Override
    public boolean isOff(){
        return ProcessStatusEnum.OFF.getStatus().equals(this.status);
    }
}