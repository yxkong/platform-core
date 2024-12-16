package com.github.platform.core.sys.adapter.api.command;

import com.github.platform.core.common.utils.SignUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.sys.domain.common.query.SysCascadeQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
 * 级联表查询
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
@Schema(description = "级联表查询")
public class SysCascadeQuery extends SysCascadeQueryBase {
    /** 父级id */
    @Schema(description = "父级id")
    private String strParentId;

    @Override
    public Long getParentId() {
        if (StringUtils.isNotEmpty(this.strParentId)){
            return SignUtil.getLongId(this.strParentId);
        }
        return super.getParentId();
    }
}