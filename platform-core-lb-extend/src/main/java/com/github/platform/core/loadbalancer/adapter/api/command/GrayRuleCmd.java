package com.github.platform.core.loadbalancer.adapter.api.command;

import com.github.platform.core.loadbalancer.domain.common.entity.GrayRuleBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 灰度规则表增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "灰度规则表增加或修改")
public class GrayRuleCmd  extends GrayRuleBase{
}
