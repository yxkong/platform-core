package com.github.platform.core.gateway.admin.domain.dto;

import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.gateway.admin.domain.common.entity.GatewayRouteBase;
import com.github.platform.core.standard.constant.SymbolConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 网关路由传输实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "网关路由传输实体")
public class GatewayRouteDto extends GatewayRouteBase {
    public List<String> getTagList() {
        if (StringUtils.isNotEmpty(this.getTags())){
            return Arrays.asList(this.getTags().split(SymbolConstant.comma));
        }
        return new ArrayList<>();
    }
}