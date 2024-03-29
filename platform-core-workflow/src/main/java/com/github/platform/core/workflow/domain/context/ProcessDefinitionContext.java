package com.github.platform.core.workflow.domain.context;
import com.github.platform.core.workflow.domain.common.entity.ProcessDefinitionBase;
import lombok.experimental.SuperBuilder;
import lombok.*;

import java.util.Objects;

/**
* 流程定义增加或修改上下文
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
public class ProcessDefinitionContext extends ProcessDefinitionBase {

    public boolean validateUpdateProcessFile(){
        if ((Objects.isNull(this.id) && Objects.isNull(this.processNo))|| Objects.isNull(this.processFile)){
            return false;
        }
        return true;
    }
}
