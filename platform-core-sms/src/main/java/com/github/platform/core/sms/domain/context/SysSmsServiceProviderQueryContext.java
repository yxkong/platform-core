package com.github.platform.core.sms.domain.context;

import com.github.platform.core.sms.domain.common.query.SysSmsServiceProviderQueryBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 服务商查询上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SysSmsServiceProviderQueryContext extends SysSmsServiceProviderQueryBase {
}
