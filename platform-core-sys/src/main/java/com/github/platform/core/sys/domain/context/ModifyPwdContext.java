package com.github.platform.core.sys.domain.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author yxkong
 * @create 2023/2/10 上午11:08
 * @desc ModifyPwdContext
 */
@ToString(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPwdContext extends TenantContext {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 旧密码
     */
    private String oldPwd;

    /**
     * 新密码
     */
    private String newPwd;
}
