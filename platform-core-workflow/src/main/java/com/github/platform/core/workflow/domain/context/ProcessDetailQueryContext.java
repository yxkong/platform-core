package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.query.ProcessDetailQueryBase;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 * 流程明细查询上下文
 * @author: yxkong
 * @date: 2023/11/13 15:00
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = " 流程明细查询上下文")
public class ProcessDetailQueryContext extends ProcessDetailQueryBase {
    /**
     * 实例信息
     */
    private ProcessInstanceDto instanceDto;
    /**
     * 场景
     *  1， 表示 项目
     */
    private Integer scene;

    /**
     * 非项目流程
     * @return
     */
    public boolean isNotPm(){
        if (Objects.isNull(this.scene)){
            return true;
        }
        return 1 != this.scene;
    }
}
