package com.github.platform.core.loadbalancer.domain.context;

import com.github.platform.core.loadbalancer.domain.common.query.GrayRuleQueryBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 灰度规则表查询上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GrayRuleQueryContext extends GrayRuleQueryBase {
}
