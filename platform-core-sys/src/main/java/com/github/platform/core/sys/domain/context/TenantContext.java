package com.github.platform.core.sys.domain.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author yxkong
 * @create 2023/2/10 下午2:34
 * @desc BaseContext
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TenantContext {
    /**
     * 租户号
     */
    protected Integer tenantId;
}
