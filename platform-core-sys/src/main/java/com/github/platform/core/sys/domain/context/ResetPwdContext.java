package com.github.platform.core.sys.domain.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author yxkong
 * @create 2023/2/10 下午3:04
 * @desc ResetPwdContext
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPwdContext extends TenantContext {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 重置后的密码
     */
    private String pwd;
    /**
     * 重置账户
     */
    private String loginName;
}
