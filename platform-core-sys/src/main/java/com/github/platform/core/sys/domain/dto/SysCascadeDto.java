package com.github.platform.core.sys.domain.dto;

import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.sys.domain.common.entity.SysCascadeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
 * 级联表传输实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-01 20:57:44.667
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "级联表传输实体")
public class SysCascadeDto extends SysCascadeBase{
    private Boolean hasChildren;

    public boolean isParent() {
        return !StatusEnum.OFF.getStatus().equals(this.getLeaf());
    }
}