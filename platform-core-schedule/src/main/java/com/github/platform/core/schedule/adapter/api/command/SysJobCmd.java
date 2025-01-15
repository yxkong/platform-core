package com.github.platform.core.schedule.adapter.api.command;
import com.github.platform.core.schedule.domain.common.entity.SysJobBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 任务管理增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "任务管理增加或修改")
public class SysJobCmd extends SysJobBase{
    /**
     * 是否多实例
     */
    private Integer multiInstance;
}
