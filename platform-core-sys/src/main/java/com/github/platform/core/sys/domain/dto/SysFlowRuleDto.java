package com.github.platform.core.sys.domain.dto;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.sys.domain.common.entity.SysFlowRuleBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;

/**
 * 状态机配置规则传输实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "状态机配置规则传输实体")
public class SysFlowRuleDto extends SysFlowRuleBase{
    //状态列表
    private List<String> targetList;

    public List<String> getTargetList() {
        if (StringUtils.isNotEmpty(this.getTargetStatus())){
            targetList = Arrays.asList(this.targetStatus.split(SymbolConstant.comma));
        }
        return targetList;
    }
}