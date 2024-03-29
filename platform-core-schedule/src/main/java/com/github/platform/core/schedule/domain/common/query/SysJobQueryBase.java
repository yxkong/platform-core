package com.github.platform.core.schedule.domain.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 任务管理查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "任务管理查询")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysJobQueryBase extends PageQueryBaseEntity {
    /**任务名称*/
    private String name;
    /**bean名称*/
    private String beanName;
}