package com.github.platform.core.loadbalancer.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 灰度规则表查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "灰度规则表查询")
public class GrayRuleQueryBase extends PageQueryBaseEntity {
    /**规则名称*/
    private String name;

    /**规则对应的服务标签*/
    private String label;
}